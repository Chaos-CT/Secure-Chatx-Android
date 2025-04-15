package org.gchat.securesms.components.settings.app.subscription.currency

import androidx.fragment.app.viewModels
import org.gchat.securesms.components.settings.DSLConfiguration
import org.gchat.securesms.components.settings.DSLSettingsAdapter
import org.gchat.securesms.components.settings.DSLSettingsBottomSheetFragment
import org.gchat.securesms.components.settings.DSLSettingsText
import org.gchat.securesms.components.settings.app.subscription.InAppPaymentsRepository
import org.gchat.securesms.components.settings.configure
import java.util.Locale

/**
 * Simple fragment for selecting a currency for Donations
 */
class SetCurrencyFragment : DSLSettingsBottomSheetFragment() {

  private val viewModel: SetCurrencyViewModel by viewModels(
    factoryProducer = {
      val args = SetCurrencyFragmentArgs.fromBundle(requireArguments())
      SetCurrencyViewModel.Factory(args.inAppPaymentType, args.supportedCurrencyCodes.toList())
    }
  )

  override fun bindAdapter(adapter: DSLSettingsAdapter) {
    viewModel.state.observe(viewLifecycleOwner) { state ->
      adapter.submitList(getConfiguration(state).toMappingModelList())
    }
  }

  private fun getConfiguration(state: SetCurrencyState): DSLConfiguration {
    return configure {
      state.currencies.forEach { currency ->
        clickPref(
          title = DSLSettingsText.from(currency.getDisplayName(Locale.getDefault())),
          summary = DSLSettingsText.from(currency.currencyCode),
          onClick = {
            viewModel.setSelectedCurrency(currency.currencyCode)
            InAppPaymentsRepository.scheduleSyncForAccountRecordChange()
            dismissAllowingStateLoss()
          }
        )
      }
    }
  }
}
