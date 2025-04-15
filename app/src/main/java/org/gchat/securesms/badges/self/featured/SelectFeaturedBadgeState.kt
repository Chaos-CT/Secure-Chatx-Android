package org.gchat.securesms.badges.self.featured

import org.gchat.securesms.badges.models.Badge

data class SelectFeaturedBadgeState(
  val stage: Stage = Stage.INIT,
  val selectedBadge: Badge? = null,
  val allUnlockedBadges: List<Badge> = listOf()
) {
  enum class Stage {
    INIT,
    READY,
    SAVING
  }
}
