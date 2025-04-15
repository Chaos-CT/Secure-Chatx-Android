package org.gchat.securesms.conversation.ui.mentions;

import androidx.annotation.NonNull;

import org.gchat.securesms.recipients.Recipient;
import org.gchat.securesms.util.viewholders.RecipientMappingModel;

public final class MentionViewState extends RecipientMappingModel<MentionViewState> {

  private final Recipient recipient;

  public MentionViewState(@NonNull Recipient recipient) {
    this.recipient = recipient;
  }

  @Override
  public @NonNull Recipient getRecipient() {
    return recipient;
  }
}
