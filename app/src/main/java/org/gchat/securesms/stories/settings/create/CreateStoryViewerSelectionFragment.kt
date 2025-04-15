package org.gchat.securesms.stories.settings.create

import androidx.navigation.fragment.findNavController
import org.gchat.securesms.R
import org.gchat.securesms.database.model.DistributionListId
import org.gchat.securesms.recipients.RecipientId
import org.gchat.securesms.stories.settings.select.BaseStoryRecipientSelectionFragment
import org.gchat.securesms.util.navigation.safeNavigate

/**
 * Allows user to select who will see the story they are creating
 */
class CreateStoryViewerSelectionFragment : BaseStoryRecipientSelectionFragment() {
  override val actionButtonLabel: Int = R.string.CreateStoryViewerSelectionFragment__next
  override val distributionListId: DistributionListId? = null

  override fun goToNextScreen(recipients: Set<RecipientId>) {
    findNavController().safeNavigate(CreateStoryViewerSelectionFragmentDirections.actionCreateStoryViewerSelectionToCreateStoryWithViewers(recipients.toTypedArray()))
  }
}
