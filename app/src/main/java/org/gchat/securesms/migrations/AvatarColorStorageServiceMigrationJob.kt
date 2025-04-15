package org.gchat.securesms.migrations

import org.signal.core.util.logging.Log
import org.signal.core.util.requireLong
import org.signal.core.util.select
import org.signal.core.util.withinTransaction
import org.gchat.securesms.database.RecipientTable
import org.gchat.securesms.database.RecipientTable.Companion.ID
import org.gchat.securesms.database.RecipientTable.Companion.STORAGE_SERVICE_ID
import org.gchat.securesms.database.RecipientTable.Companion.TABLE_NAME
import org.gchat.securesms.database.RecipientTable.Companion.TYPE
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.jobmanager.Job
import org.gchat.securesms.keyvalue.SignalStore
import org.gchat.securesms.recipients.Recipient
import org.gchat.securesms.recipients.RecipientId
import org.gchat.securesms.storage.StorageSyncHelper

/**
 * A job that marks all contacts and groups as needing to be synced, so that we'll update the
 * storage records with the new avatar color field.
 */
internal class AvatarColorStorageServiceMigrationJob(
  parameters: Parameters = Parameters.Builder().build()
) : MigrationJob(parameters) {

  companion object {
    val TAG = Log.tag(AvatarColorStorageServiceMigrationJob::class.java)
    const val KEY = "AvatarColorStorageServiceMigrationJob"
  }

  override fun getFactoryKey(): String = KEY

  override fun isUiBlocking(): Boolean = false

  override fun performMigration() {
    if (!Recipient.isSelfSet) {
      return
    }

    if (!SignalStore.account.isRegistered) {
      return
    }

    SignalDatabase.recipients.markNeedsSync(Recipient.self().id)
    SignalDatabase.recipients.markAllContactsAndGroupsAsNeedsSync()
    StorageSyncHelper.scheduleSyncForDataChange()
  }

  override fun shouldRetry(e: Exception): Boolean = false

  private fun RecipientTable.markAllContactsAndGroupsAsNeedsSync() {
    writableDatabase.withinTransaction { db ->
      db.select(ID)
        .from(TABLE_NAME)
        .where("$STORAGE_SERVICE_ID NOT NULL AND $TYPE IN (${RecipientTable.RecipientType.INDIVIDUAL.id}, ${RecipientTable.RecipientType.GV2.id})")
        .run()
        .use { cursor ->
          while (cursor.moveToNext()) {
            rotateStorageId(RecipientId.from(cursor.requireLong(ID)))
          }
        }
    }
  }

  class Factory : Job.Factory<AvatarColorStorageServiceMigrationJob> {
    override fun create(parameters: Parameters, serializedData: ByteArray?): AvatarColorStorageServiceMigrationJob {
      return AvatarColorStorageServiceMigrationJob(parameters)
    }
  }
}
