/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.service.webrtc

import org.signal.ringrtc.CallId
import org.signal.ringrtc.PeekInfo
import org.gchat.securesms.recipients.Recipient

/**
 * App-level peek info object for call links.
 */
data class CallLinkPeekInfo(
  val callId: CallId?,
  val isActive: Boolean,
  val isJoined: Boolean
) {

  val isCompletelyInactive
    get() = callId == null && !isActive && !isJoined

  companion object {
    @JvmStatic
    fun fromPeekInfo(peekInfo: PeekInfo): CallLinkPeekInfo {
      return CallLinkPeekInfo(
        callId = peekInfo.eraId?.let { CallId.fromEra(it) },
        isActive = peekInfo.joinedMembers.isNotEmpty(),
        isJoined = peekInfo.joinedMembers.contains(Recipient.self().requireServiceId().rawUuid)
      )
    }
  }
}
