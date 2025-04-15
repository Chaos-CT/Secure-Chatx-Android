/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package org.gchat.securesms.messages

import org.gchat.securesms.groups.GroupId

/**
 * Message processing exception metadata.
 */
class ExceptionMetadata @JvmOverloads constructor(val sender: String, val senderDevice: Int, val groupId: GroupId? = null)
