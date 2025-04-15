package org.gchat.securesms.components.settings.app.privacy

import android.content.Context
import org.signal.core.util.concurrent.SignalExecutors
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.dependencies.AppDependencies
import org.gchat.securesms.jobs.MultiDeviceConfigurationUpdateJob
import org.gchat.securesms.keyvalue.SignalStore
import org.gchat.securesms.recipients.Recipient
import org.gchat.securesms.storage.StorageSyncHelper
import org.gchat.securesms.util.TextSecurePreferences

class PrivacySettingsRepository {

  private val context: Context = AppDependencies.application

  fun getBlockedCount(consumer: (Int) -> Unit) {
    SignalExecutors.BOUNDED.execute {
      val recipientDatabase = SignalDatabase.recipients

      consumer(recipientDatabase.getBlocked().size)
    }
  }

  fun syncReadReceiptState() {
    SignalExecutors.BOUNDED.execute {
      SignalDatabase.recipients.markNeedsSync(Recipient.self().id)
      StorageSyncHelper.scheduleSyncForDataChange()
      AppDependencies.jobManager.add(
        MultiDeviceConfigurationUpdateJob(
          TextSecurePreferences.isReadReceiptsEnabled(context),
          TextSecurePreferences.isTypingIndicatorsEnabled(context),
          TextSecurePreferences.isShowUnidentifiedDeliveryIndicatorsEnabled(context),
          SignalStore.settings.isLinkPreviewsEnabled
        )
      )
    }
  }

  fun syncTypingIndicatorsState() {
    val enabled = TextSecurePreferences.isTypingIndicatorsEnabled(context)

    SignalDatabase.recipients.markNeedsSync(Recipient.self().id)
    StorageSyncHelper.scheduleSyncForDataChange()
    AppDependencies.jobManager.add(
      MultiDeviceConfigurationUpdateJob(
        TextSecurePreferences.isReadReceiptsEnabled(context),
        enabled,
        TextSecurePreferences.isShowUnidentifiedDeliveryIndicatorsEnabled(context),
        SignalStore.settings.isLinkPreviewsEnabled
      )
    )

    if (!enabled) {
      AppDependencies.typingStatusRepository.clear()
    }
  }
}
