package org.gchat.securesms.payments.preferences;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import org.signal.core.util.concurrent.SimpleTask;
import org.gchat.securesms.ContactSelectionListFragment;
import org.gchat.securesms.LoggingFragment;
import org.gchat.securesms.R;
import org.gchat.securesms.components.ContactFilterView;
import org.gchat.securesms.contacts.ContactSelectionDisplayMode;
import org.gchat.securesms.contacts.paged.ChatType;
import org.gchat.securesms.conversation.ConversationIntents;
import org.gchat.securesms.database.SignalDatabase;
import org.gchat.securesms.payments.CanNotSendPaymentDialog;
import org.gchat.securesms.payments.preferences.model.PayeeParcelable;
import org.gchat.securesms.recipients.Recipient;
import org.gchat.securesms.recipients.RecipientId;
import org.gchat.securesms.util.ViewUtil;
import org.gchat.securesms.util.navigation.SafeNavigation;
import org.whispersystems.signalservice.api.util.ExpiringProfileCredentialUtil;

import java.util.Optional;
import java.util.function.Consumer;


public class PaymentRecipientSelectionFragment extends LoggingFragment implements ContactSelectionListFragment.OnContactSelectedListener, ContactSelectionListFragment.ScrollCallback {

  private Toolbar                      toolbar;
  private ContactFilterView            contactFilterView;
  private ContactSelectionListFragment contactsFragment;

  public PaymentRecipientSelectionFragment() {
    super(R.layout.payment_recipient_selection_fragment);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    toolbar = view.findViewById(R.id.payment_recipient_selection_fragment_toolbar);
    toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).popBackStack());

    contactFilterView = view.findViewById(R.id.contact_filter_edit_text);

    Bundle arguments = new Bundle();
    arguments.putBoolean(ContactSelectionListFragment.REFRESHABLE, false);
    arguments.putInt(ContactSelectionListFragment.DISPLAY_MODE, ContactSelectionDisplayMode.FLAG_PUSH | ContactSelectionDisplayMode.FLAG_HIDE_NEW);
    arguments.putBoolean(ContactSelectionListFragment.CAN_SELECT_SELF, false);

    Fragment child = getChildFragmentManager().findFragmentById(R.id.contact_selection_list_fragment_holder);
    if (child == null) {
      FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
      contactsFragment = new ContactSelectionListFragment();
      contactsFragment.setArguments(arguments);
      transaction.add(R.id.contact_selection_list_fragment_holder, contactsFragment);
      transaction.commit();
    } else {
      contactsFragment = (ContactSelectionListFragment) child;
    }

    initializeSearch();
  }

  private void initializeSearch() {
    contactFilterView.setOnFilterChangedListener(filter -> contactsFragment.setQueryFilter(filter));
  }

  @Override
  public void onBeforeContactSelected(boolean isFromUnknownSearchKey, @NonNull Optional<RecipientId> recipientId, @Nullable String number, @NonNull Optional<ChatType> chatType, @NonNull Consumer<Boolean> callback) {
    if (recipientId.isPresent()) {
      SimpleTask.run(getViewLifecycleOwner().getLifecycle(),
                     () -> Recipient.resolved(recipientId.get()),
                     this::createPaymentOrShowWarningDialog);
    }

    callback.accept(false);
  }

  @Override
  public void onContactDeselected(@NonNull Optional<RecipientId> recipientId, @Nullable String number, @NonNull Optional<ChatType> chatType) {}

  @Override
  public void onSelectionChanged() {
  }

  @Override
  public void onBeginScroll() {
    hideKeyboard();
  }

  private void hideKeyboard() {
    ViewUtil.hideKeyboard(requireContext(), toolbar);
    toolbar.clearFocus();
  }

  private void createPaymentOrShowWarningDialog(@NonNull Recipient recipient) {
    if (ExpiringProfileCredentialUtil.isValid(recipient.getExpiringProfileKeyCredential())) {
      createPayment(recipient.getId());
    } else {
      showWarningDialog(recipient.getId());
    }
  }

  private void createPayment(@NonNull RecipientId recipientId) {
    hideKeyboard();
    SafeNavigation.safeNavigate(Navigation.findNavController(requireView()), PaymentRecipientSelectionFragmentDirections.actionPaymentRecipientSelectionToCreatePayment(new PayeeParcelable(recipientId)));
  }

  private void showWarningDialog(@NonNull RecipientId recipientId) {
    CanNotSendPaymentDialog.show(requireContext(),
                                 () -> openConversation(recipientId));
  }

  private void openConversation(@NonNull RecipientId recipientId) {
    SimpleTask.run(getViewLifecycleOwner().getLifecycle(),
                   () -> SignalDatabase.threads().getOrCreateThreadIdFor(Recipient.resolved(recipientId)),
                   threadId -> startActivity(ConversationIntents.createBuilderSync(requireContext(), recipientId, threadId).build()));
  }
}
