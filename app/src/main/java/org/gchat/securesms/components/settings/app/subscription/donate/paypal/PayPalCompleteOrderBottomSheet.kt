package org.gchat.securesms.components.settings.app.subscription.donate.paypal

import android.content.DialogInterface
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.signal.core.util.dp
import org.gchat.securesms.R
import org.gchat.securesms.badges.Badges
import org.gchat.securesms.badges.models.BadgeDisplay112
import org.gchat.securesms.components.settings.DSLConfiguration
import org.gchat.securesms.components.settings.DSLSettingsAdapter
import org.gchat.securesms.components.settings.DSLSettingsBottomSheetFragment
import org.gchat.securesms.components.settings.DSLSettingsText
import org.gchat.securesms.components.settings.app.subscription.donate.gateway.GatewaySelectorBottomSheet.Companion.presentTitleAndSubtitle
import org.gchat.securesms.components.settings.configure

/**
 * Bottom sheet for final order confirmation from PayPal
 */
class PayPalCompleteOrderBottomSheet : DSLSettingsBottomSheetFragment() {

  companion object {
    const val REQUEST_KEY = "complete_order"
  }

  private var didConfirmOrder = false
  private val args: PayPalCompleteOrderBottomSheetArgs by navArgs()

  override fun bindAdapter(adapter: DSLSettingsAdapter) {
    BadgeDisplay112.register(adapter)
    PayPalCompleteOrderPaymentItem.register(adapter)

    adapter.submitList(getConfiguration().toMappingModelList())
  }

  override fun onDismiss(dialog: DialogInterface) {
    super.onDismiss(dialog)
    setFragmentResult(REQUEST_KEY, bundleOf(REQUEST_KEY to didConfirmOrder))
  }

  private fun getConfiguration(): DSLConfiguration {
    return configure {
      customPref(
        BadgeDisplay112.Model(
          badge = Badges.fromDatabaseBadge(args.inAppPayment.data.badge!!),
          withDisplayText = false
        )
      )

      space(12.dp)

      presentTitleAndSubtitle(requireContext(), args.inAppPayment)

      space(24.dp)

      customPref(PayPalCompleteOrderPaymentItem.Model())

      space(82.dp)

      primaryButton(
        text = DSLSettingsText.from(R.string.PaypalCompleteOrderBottomSheet__donate),
        onClick = {
          didConfirmOrder = true
          findNavController().popBackStack()
        }
      )

      secondaryButtonNoOutline(
        text = DSLSettingsText.from(android.R.string.cancel),
        onClick = {
          findNavController().popBackStack()
        }
      )

      space(16.dp)
    }
  }
}
