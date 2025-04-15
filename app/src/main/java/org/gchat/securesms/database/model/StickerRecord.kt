package org.gchat.securesms.database.model

import android.net.Uri
import org.gchat.securesms.mms.PartAuthority

/**
 * Represents a record for a sticker pack in the [StickerTable].
 */
data class StickerRecord(
  @JvmField val rowId: Long,
  @JvmField val packId: String,
  @JvmField val packKey: String,
  @JvmField val stickerId: Int,
  @JvmField val emoji: String,
  @JvmField val contentType: String,
  @JvmField val size: Long,
  @JvmField val isCover: Boolean
) {
  @JvmField
  val uri: Uri = PartAuthority.getStickerUri(rowId)
}
