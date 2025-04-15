package org.gchat.securesms.conversationlist.chatfilter

import org.gchat.securesms.conversationlist.model.ConversationFilter

data class ConversationFilterRequest(
  val filter: ConversationFilter,
  val source: ConversationFilterSource
)
