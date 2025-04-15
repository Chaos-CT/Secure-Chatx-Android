package org.gchat.securesms.migrations

import org.gchat.securesms.dependencies.AppDependencies
import org.gchat.securesms.jobmanager.Job
import org.gchat.securesms.jobs.MultiDeviceKeysUpdateJob
import org.gchat.securesms.keyvalue.SignalStore

/**
 * Migration to sync keys with linked devices.
 */
internal class SyncKeysMigrationJob(
  parameters: Parameters = Parameters.Builder().build()
) : MigrationJob(parameters) {

  companion object {
    const val KEY = "SyncKeysMigrationJob"
  }

  override fun getFactoryKey(): String = KEY

  override fun isUiBlocking(): Boolean = false

  override fun performMigration() {
    if (SignalStore.account.hasLinkedDevices) {
      AppDependencies.jobManager.add(MultiDeviceKeysUpdateJob())
    }
  }

  override fun shouldRetry(e: Exception): Boolean = false

  class Factory : Job.Factory<SyncKeysMigrationJob> {
    override fun create(parameters: Parameters, serializedData: ByteArray?): SyncKeysMigrationJob {
      return SyncKeysMigrationJob(parameters)
    }
  }
}
