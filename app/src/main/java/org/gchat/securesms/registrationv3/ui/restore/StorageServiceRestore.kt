/*
 * Copyright 2025 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.registrationv3.ui.restore

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.signal.core.util.Stopwatch
import org.signal.core.util.logging.Log
import org.gchat.securesms.dependencies.AppDependencies
import org.gchat.securesms.jobmanager.enqueueBlocking
import org.gchat.securesms.jobmanager.runJobBlocking
import org.gchat.securesms.jobs.ProfileUploadJob
import org.gchat.securesms.jobs.ReclaimUsernameAndLinkJob
import org.gchat.securesms.jobs.StorageAccountRestoreJob
import org.gchat.securesms.jobs.StorageSyncJob
import org.gchat.securesms.keyvalue.SignalStore
import org.gchat.securesms.registration.util.RegistrationUtil
import org.gchat.securesms.registrationv3.data.RegistrationRepository
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

object StorageServiceRestore {
  private val TAG = Log.tag(StorageServiceRestore::class)

  /**
   * Restore account data from Storage Service in a quasi-blocking manner. Uses existing jobs
   * to perform the restore but will not wait indefinitely for them to finish so may return prior
   * to completing the restore.
   */
  suspend fun restore() {
    withContext(Dispatchers.IO) {
      val stopwatch = Stopwatch("storage-service-restore")

      SignalStore.storageService.needsAccountRestore = false

      AppDependencies.jobManager.runJobBlocking(StorageAccountRestoreJob(), StorageAccountRestoreJob.LIFESPAN.milliseconds)
      stopwatch.split("account-restore")

      AppDependencies
        .jobManager
        .startChain(StorageSyncJob())
        .then(ReclaimUsernameAndLinkJob())
        .enqueueBlocking(10.seconds)
      stopwatch.split("storage-sync-restore")

      stopwatch.stop(TAG)

      val isMissingProfileData = RegistrationRepository.isMissingProfileData()

      if (!isMissingProfileData) {
        RegistrationUtil.maybeMarkRegistrationComplete()
        AppDependencies.jobManager.add(ProfileUploadJob())
      }
    }
  }
}
