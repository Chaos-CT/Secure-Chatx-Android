package org.gchat.securesms.migrations

import org.gchat.securesms.database.SignalDatabase.Companion.recipients
import org.gchat.securesms.jobmanager.Job
import org.gchat.securesms.keyvalue.SignalStore
import org.gchat.securesms.recipients.Recipient
import org.gchat.securesms.storage.StorageSyncHelper
import org.gchat.securesms.util.TextSecurePreferences

/**
 * Added as a way to initialize the story viewed receipts setting.
 */
internal class StoryViewedReceiptsStateMigrationJob(
  parameters: Parameters = Parameters.Builder().build()
) : MigrationJob(parameters) {
  companion object {
    const val KEY = "StoryViewedReceiptsStateMigrationJob"
  }

  override fun getFactoryKey(): String = KEY

  override fun isUiBlocking(): Boolean = false

  override fun performMigration() {
    if (!SignalStore.story.isViewedReceiptsStateSet()) {
      SignalStore.story.viewedReceiptsEnabled = TextSecurePreferences.isReadReceiptsEnabled(context)
      if (SignalStore.account.isRegistered) {
        recipients.markNeedsSync(Recipient.self().id)
        StorageSyncHelper.scheduleSyncForDataChange()
      }
    }
  }

  override fun shouldRetry(e: Exception): Boolean = false

  class Factory : Job.Factory<StoryViewedReceiptsStateMigrationJob> {
    override fun create(parameters: Parameters, serializedData: ByteArray?): StoryViewedReceiptsStateMigrationJob {
      return StoryViewedReceiptsStateMigrationJob(parameters)
    }
  }
}
