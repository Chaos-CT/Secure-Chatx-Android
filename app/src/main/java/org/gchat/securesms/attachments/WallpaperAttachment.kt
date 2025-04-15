/*
 * Copyright 2024 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.attachments

import org.gchat.securesms.database.AttachmentTable
import org.gchat.securesms.database.AttachmentTable.TransformProperties
import org.gchat.securesms.util.MediaUtil

/**
 * A basically-empty [Attachment] that is solely used for inserting an attachment into the [AttachmentTable].
 */
class WallpaperAttachment() : Attachment(
  contentType = MediaUtil.IMAGE_WEBP,
  transferState = AttachmentTable.TRANSFER_PROGRESS_DONE,
  size = 0,
  fileName = null,
  cdn = Cdn.CDN_0,
  remoteLocation = null,
  remoteKey = null,
  remoteIv = null,
  remoteDigest = null,
  incrementalDigest = null,
  fastPreflightId = null,
  voiceNote = false,
  borderless = false,
  videoGif = false,
  width = 0,
  height = 0,
  incrementalMacChunkSize = 0,
  quote = false,
  uploadTimestamp = 0,
  caption = null,
  stickerLocator = null,
  blurHash = null,
  audioHash = null,
  transformProperties = TransformProperties.empty(),
  uuid = null
) {
  override val uri = null
  override val publicUri = null
  override val thumbnailUri = null
}
