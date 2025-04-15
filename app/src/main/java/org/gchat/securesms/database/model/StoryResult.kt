package org.gchat.securesms.database.model

import org.gchat.securesms.recipients.RecipientId

class StoryResult(
  val recipientId: RecipientId,
  val messageId: Long,
  val messageSentTimestamp: Long,
  val isOutgoing: Boolean
)
