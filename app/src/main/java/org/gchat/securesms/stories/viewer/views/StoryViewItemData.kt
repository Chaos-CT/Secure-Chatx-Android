package org.gchat.securesms.stories.viewer.views

import org.gchat.securesms.recipients.Recipient

data class StoryViewItemData(
  val recipient: Recipient,
  val timeViewedInMillis: Long
)
