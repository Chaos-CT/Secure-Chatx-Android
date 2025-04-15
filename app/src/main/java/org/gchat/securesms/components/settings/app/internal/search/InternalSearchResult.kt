/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.components.settings.app.internal.search

import org.gchat.securesms.groups.GroupId
import org.gchat.securesms.recipients.RecipientId

data class InternalSearchResult(
  val name: String,
  val id: RecipientId,
  val aci: String? = null,
  val pni: String? = null,
  val groupId: GroupId? = null
)
