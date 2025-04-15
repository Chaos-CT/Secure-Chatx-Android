package org.gchat.securesms.avatar.vector

import org.gchat.securesms.avatar.Avatar
import org.gchat.securesms.avatar.AvatarColorItem
import org.gchat.securesms.avatar.Avatars

data class VectorAvatarCreationState(
  val currentAvatar: Avatar.Vector
) {
  fun colors(): List<AvatarColorItem> = Avatars.colors.map { AvatarColorItem(it, currentAvatar.color == it) }
}
