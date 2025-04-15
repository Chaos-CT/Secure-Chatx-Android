/*
 * Copyright 2025 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.components.webrtc.v2

import org.gchat.securesms.service.webrtc.PendingParticipantCollection

/**
 * Represents the current state of the pending participants card.
 */
data class PendingParticipantsState(
  val pendingParticipantCollection: PendingParticipantCollection,
  val isInPipMode: Boolean
)
