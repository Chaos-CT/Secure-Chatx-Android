package org.gchat.securesms.database.model

import org.gchat.securesms.recipients.RecipientId

/** A model for [org.gchat.securesms.database.PendingRetryReceiptTable] */
data class PendingRetryReceiptModel(
  val id: Long,
  val author: RecipientId,
  val authorDevice: Int,
  val sentTimestamp: Long,
  val receivedTimestamp: Long,
  val threadId: Long
)
