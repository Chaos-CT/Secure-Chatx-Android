package org.gchat.securesms.jobs

import org.signal.core.util.logging.Log
import org.gchat.securesms.dependencies.AppDependencies
import org.gchat.securesms.jobmanager.Job
import org.gchat.securesms.jobmanager.impl.NetworkConstraint
import org.gchat.securesms.keyvalue.SignalStore
import org.gchat.securesms.net.NotPushRegisteredException
import org.gchat.securesms.recipients.Recipient
import org.whispersystems.signalservice.api.crypto.UntrustedIdentityException
import org.whispersystems.signalservice.api.messages.multidevice.KeysMessage
import org.whispersystems.signalservice.api.messages.multidevice.SignalServiceSyncMessage
import org.whispersystems.signalservice.api.push.exceptions.PushNetworkException
import org.whispersystems.signalservice.api.push.exceptions.ServerRejectedException
import java.io.IOException

class MultiDeviceKeysUpdateJob private constructor(parameters: Parameters) : BaseJob(parameters) {

  companion object {
    const val KEY: String = "MultiDeviceKeysUpdateJob"

    private val TAG = Log.tag(MultiDeviceKeysUpdateJob::class.java)
  }

  constructor() : this(
    Parameters.Builder()
      .setQueue("MultiDeviceKeysUpdateJob")
      .setMaxInstancesForFactory(2)
      .addConstraint(NetworkConstraint.KEY)
      .setMaxAttempts(10)
      .build()
  )

  override fun serialize(): ByteArray? = null

  override fun getFactoryKey(): String = KEY

  @Throws(IOException::class, UntrustedIdentityException::class)
  public override fun onRun() {
    if (!Recipient.self().isRegistered) {
      throw NotPushRegisteredException()
    }

    if (!SignalStore.account.hasLinkedDevices) {
      Log.i(TAG, "Not multi device, aborting...")
      return
    }

    if (SignalStore.account.isLinkedDevice) {
      Log.i(TAG, "Not primary device, aborting...")
      return
    }

    val syncMessage = SignalServiceSyncMessage.forKeys(
      KeysMessage(
        storageService = SignalStore.storageService.storageKey,
        master = SignalStore.svr.masterKey,
        accountEntropyPool = SignalStore.account.accountEntropyPool,
        mediaRootBackupKey = SignalStore.backup.mediaRootBackupKey
      )
    )

    AppDependencies.signalServiceMessageSender.sendSyncMessage(syncMessage)
  }

  public override fun onShouldRetry(e: Exception): Boolean {
    if (e is ServerRejectedException) return false
    return e is PushNetworkException
  }

  override fun onFailure() {
  }

  class Factory : Job.Factory<MultiDeviceKeysUpdateJob?> {
    override fun create(parameters: Parameters, serializedData: ByteArray?): MultiDeviceKeysUpdateJob {
      return MultiDeviceKeysUpdateJob(parameters)
    }
  }
}
