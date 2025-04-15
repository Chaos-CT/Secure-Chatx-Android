package org.gchat.securesms.avatar.text

import org.gchat.securesms.avatar.Avatar
import org.gchat.securesms.avatar.AvatarColorItem
import org.gchat.securesms.avatar.Avatars

data class TextAvatarCreationState(
  val currentAvatar: Avatar.Text
) {
  fun colors(): List<AvatarColorItem> = Avatars.colors.map { AvatarColorItem(it, currentAvatar.color == it) }
}
