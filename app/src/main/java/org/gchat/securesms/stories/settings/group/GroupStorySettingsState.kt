package org.gchat.securesms.stories.settings.group

import org.gchat.securesms.recipients.Recipient

data class GroupStorySettingsState(
  val name: String = "",
  val members: List<Recipient> = emptyList(),
  val removed: Boolean = false
)
