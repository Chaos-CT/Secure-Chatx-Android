package org.gchat.securesms.components.settings.app

import androidx.compose.runtime.Immutable
import org.gchat.securesms.keyvalue.SignalStore
import org.gchat.securesms.util.Environment
import org.gchat.securesms.util.RemoteConfig

@Immutable
data class AppSettingsState(
  val unreadPaymentsCount: Int,
  val hasExpiredGiftBadge: Boolean,
  val allowUserToGoToDonationManagementScreen: Boolean,
  val userUnregistered: Boolean,
  val clientDeprecated: Boolean,
  val showInternalPreferences: Boolean = RemoteConfig.internalUser,
  val showPayments: Boolean = SignalStore.payments.paymentsAvailability.showPaymentsMenu(),
  val showAppUpdates: Boolean = Environment.IS_NIGHTLY,
  val showBackups: Boolean = RemoteConfig.messageBackups,
  val backupFailureState: BackupFailureState = BackupFailureState.NONE
) {
  fun isRegisteredAndUpToDate(): Boolean {
    return !userUnregistered && !clientDeprecated
  }
}
