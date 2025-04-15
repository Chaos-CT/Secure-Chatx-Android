package org.gchat.securesms.components.settings.app.chats

import android.content.Context
import org.signal.core.util.concurrent.SignalExecutors
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.dependencies.AppDependencies
import org.gchat.securesms.jobs.MultiDeviceConfigurationUpdateJob
import org.gchat.securesms.jobs.MultiDeviceContactUpdateJob
import org.gchat.securesms.keyvalue.SignalStore
import org.gchat.securesms.recipients.Recipient
import org.gchat.securesms.storage.StorageSyncHelper
import org.gchat.securesms.util.TextSecurePreferences

class ChatsSettingsRepository {

  private val context: Context = AppDependencies.application

  fun syncLinkPreviewsState() {
    SignalExecutors.BOUNDED.execute {
      val isLinkPreviewsEnabled = SignalStore.settings.isLinkPreviewsEnabled

      SignalDatabase.recipients.markNeedsSync(Recipient.self().id)
      StorageSyncHelper.scheduleSyncForDataChange()
      AppDependencies.jobManager.add(
        MultiDeviceConfigurationUpdateJob(
          TextSecurePreferences.isReadReceiptsEnabled(context),
          TextSecurePreferences.isTypingIndicatorsEnabled(context),
          TextSecurePreferences.isShowUnidentifiedDeliveryIndicatorsEnabled(context),
          isLinkPreviewsEnabled
        )
      )
    }
  }

  fun syncPreferSystemContactPhotos() {
    SignalExecutors.BOUNDED.execute {
      SignalDatabase.recipients.markNeedsSync(Recipient.self().id)
      AppDependencies.jobManager.add(MultiDeviceContactUpdateJob(true))
      StorageSyncHelper.scheduleSyncForDataChange()
    }
  }

  fun syncKeepMutedChatsArchivedState() {
    SignalExecutors.BOUNDED.execute {
      SignalDatabase.recipients.markNeedsSync(Recipient.self().id)
      StorageSyncHelper.scheduleSyncForDataChange()
    }
  }
}
