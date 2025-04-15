/**
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.calls.links.create

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.gchat.securesms.database.CallLinkTable
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.dependencies.AppDependencies
import org.gchat.securesms.jobs.CallLinkUpdateSendJob
import org.gchat.securesms.recipients.Recipient
import org.gchat.securesms.recipients.RecipientId
import org.gchat.securesms.service.webrtc.links.CallLinkCredentials
import org.gchat.securesms.service.webrtc.links.CreateCallLinkResult
import org.gchat.securesms.service.webrtc.links.SignalCallLinkManager
import org.gchat.securesms.storage.StorageSyncHelper
import org.whispersystems.signalservice.internal.push.SyncMessage

/**
 * Repository for creating new call links. This will delegate to the [SignalCallLinkManager]
 * but will also ensure the database is updated.
 */
class CreateCallLinkRepository(
  private val callLinkManager: SignalCallLinkManager = AppDependencies.signalCallManager.callLinkManager
) {
  fun ensureCallLinkCreated(credentials: CallLinkCredentials): Single<EnsureCallLinkCreatedResult> {
    val callLinkRecipientId = Single.fromCallable {
      SignalDatabase.recipients.getByCallLinkRoomId(credentials.roomId)
    }

    return callLinkRecipientId.flatMap { recipientId ->
      if (recipientId.isPresent) {
        Single.just(EnsureCallLinkCreatedResult.Success(Recipient.resolved(recipientId.get())))
      } else {
        callLinkManager.createCallLink(credentials).map {
          when (it) {
            is CreateCallLinkResult.Success -> {
              SignalDatabase.callLinks.insertCallLink(
                CallLinkTable.CallLink(
                  recipientId = RecipientId.UNKNOWN,
                  roomId = it.credentials.roomId,
                  credentials = it.credentials,
                  state = it.state,
                  deletionTimestamp = 0L
                )
              )

              AppDependencies.jobManager.add(
                CallLinkUpdateSendJob(
                  it.credentials.roomId,
                  SyncMessage.CallLinkUpdate.Type.UPDATE
                )
              )

              StorageSyncHelper.scheduleSyncForDataChange()

              EnsureCallLinkCreatedResult.Success(
                Recipient.resolved(
                  SignalDatabase.recipients.getByCallLinkRoomId(it.credentials.roomId).get()
                )
              )
            }

            is CreateCallLinkResult.Failure -> EnsureCallLinkCreatedResult.Failure(it)
          }
        }
      }
    }.subscribeOn(Schedulers.io())
  }
}
