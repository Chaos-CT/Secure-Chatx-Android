package org.gchat.securesms.stories.settings.custom

import org.gchat.securesms.database.model.DistributionListRecord

data class PrivateStorySettingsState(
  val privateStory: DistributionListRecord? = null,
  val areRepliesAndReactionsEnabled: Boolean = false,
  val isActionInProgress: Boolean = false
)
