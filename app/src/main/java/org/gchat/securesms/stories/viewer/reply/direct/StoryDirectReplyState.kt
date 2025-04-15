package org.gchat.securesms.stories.viewer.reply.direct

import org.gchat.securesms.database.model.MessageRecord
import org.gchat.securesms.recipients.Recipient

data class StoryDirectReplyState(
  val groupDirectReplyRecipient: Recipient? = null,
  val storyRecord: MessageRecord? = null
)
