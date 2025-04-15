package org.gchat.securesms.search

import org.gchat.securesms.recipients.Recipient

/**
 * Represents a search result for a message.
 */
data class MessageResult(
  val conversationRecipient: Recipient,
  val messageRecipient: Recipient,
  val body: CharSequence,
  val bodySnippet: CharSequence,
  val threadId: Long,
  val messageId: Long,
  val receivedTimestampMs: Long,
  val isMms: Boolean
)
