package org.gchat.securesms.keyboard.emoji

import org.gchat.securesms.components.emoji.EmojiPageModel
import org.gchat.securesms.components.emoji.EmojiPageViewGridAdapter
import org.gchat.securesms.components.emoji.RecentEmojiPageModel
import org.gchat.securesms.components.emoji.parsing.EmojiTree
import org.gchat.securesms.emoji.EmojiCategory
import org.gchat.securesms.emoji.EmojiSource
import org.gchat.securesms.util.adapter.mapping.MappingModel

fun EmojiPageModel.toMappingModels(): List<MappingModel<*>> {
  val emojiTree: EmojiTree = EmojiSource.latest.emojiTree

  return displayEmoji.map {
    val isTextEmoji = EmojiCategory.EMOTICONS.key == key || (RecentEmojiPageModel.KEY == key && emojiTree.getEmoji(it.value, 0, it.value.length) == null)

    if (isTextEmoji) {
      EmojiPageViewGridAdapter.EmojiTextModel(key, it)
    } else {
      EmojiPageViewGridAdapter.EmojiModel(key, it)
    }
  }
}
