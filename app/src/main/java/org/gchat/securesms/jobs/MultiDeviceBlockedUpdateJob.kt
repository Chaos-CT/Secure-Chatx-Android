package org.gchat.securesms.jobs

import org.signal.core.util.logging.Log
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.database.model.RecipientRecord
import org.gchat.securesms.dependencies.AppDependencies
import org.gchat.securesms.jobmanager.Job
import org.gchat.securesms.jobmanager.impl.NetworkConstraint
import org.gchat.securesms.keyvalue.SignalStore
import org.gchat.securesms.net.NotPushRegisteredException
import org.gchat.securesms.recipients.Recipient
import org.whispersystems.signalservice.api.crypto.UntrustedIdentityException
import org.whispersystems.signalservice.api.messages.multidevice.BlockedListMessage
import org.whispersystems.signalservice.api.messages.multidevice.SignalServiceSyncMessage
import org.whispersystems.signalservice.api.push.exceptions.PushNetworkException
import org.whispersystems.signalservice.api.push.exceptions.ServerRejectedException
import java.io.IOException
import java.util.concurrent.TimeUnit

class MultiDeviceBlockedUpdateJob private constructor(parameters: Parameters) : BaseJob(parameters) {
  companion object {
    const val KEY: String = "MultiDeviceBlockedUpdateJob"

    private val TAG = Log.tag(MultiDeviceBlockedUpdateJob::class.java)
  }

  constructor() : this(
    Parameters.Builder()
      .addConstraint(NetworkConstraint.KEY)
      .setQueue("MultiDeviceBlockedUpdateJob")
      .setLifespan(TimeUnit.DAYS.toMillis(1))
      .setMaxAttempts(Parameters.UNLIMITED)
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

    val blocked: List<RecipientRecord> = SignalDatabase.recipients.getBlocked()
    val blockedGroups = blocked.mapNotNull { it.groupId?.decodedId }
    val blockedIndividuals = blocked
      .filter { it.aci != null || it.e164 != null }
      .map { BlockedListMessage.Individual(it.aci, it.e164) }

    AppDependencies.signalServiceMessageSender.sendSyncMessage(
      SignalServiceSyncMessage.forBlocked(BlockedListMessage(blockedIndividuals, blockedGroups))
    )
  }

  public override fun onShouldRetry(exception: Exception): Boolean {
    if (exception is ServerRejectedException) return false
    if (exception is PushNetworkException) return true
    return false
  }

  override fun onFailure() = Unit

  class Factory : Job.Factory<MultiDeviceBlockedUpdateJob?> {
    override fun create(parameters: Parameters, serializedData: ByteArray?): MultiDeviceBlockedUpdateJob {
      return MultiDeviceBlockedUpdateJob(parameters)
    }
  }
}
