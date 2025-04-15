/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.backup.v2.database

import org.gchat.securesms.attachments.Attachment
import org.gchat.securesms.attachments.AttachmentId
import org.gchat.securesms.database.AttachmentTable

fun AttachmentTable.restoreWallpaperAttachment(attachment: Attachment): AttachmentId? {
  return insertAttachmentsForMessage(AttachmentTable.WALLPAPER_MESSAGE_ID, listOf(attachment), emptyList()).values.firstOrNull()
}
