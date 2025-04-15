/**
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.calls.links.details

import androidx.compose.runtime.Immutable
import org.gchat.securesms.database.CallLinkTable
import org.gchat.securesms.service.webrtc.CallLinkPeekInfo

@Immutable
data class CallLinkDetailsState(
  val displayRevocationDialog: Boolean = false,
  val isLoadingAdminApprovalChange: Boolean = false,
  val callLink: CallLinkTable.CallLink? = null,
  val peekInfo: CallLinkPeekInfo? = null
)
