package org.gchat.securesms.stories.settings.select

import org.gchat.securesms.database.model.DistributionListId
import org.gchat.securesms.database.model.DistributionListRecord
import org.gchat.securesms.recipients.RecipientId

data class BaseStoryRecipientSelectionState(
  val distributionListId: DistributionListId?,
  val privateStory: DistributionListRecord? = null,
  val selection: Set<RecipientId> = emptySet(),
  val isStartingSelection: Boolean = false
)
