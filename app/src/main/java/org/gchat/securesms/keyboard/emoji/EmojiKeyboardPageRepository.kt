package org.gchat.securesms.keyboard.emoji

import android.content.Context
import org.signal.core.util.concurrent.SignalExecutors
import org.gchat.securesms.components.emoji.EmojiPageModel
import org.gchat.securesms.components.emoji.RecentEmojiPageModel
import org.gchat.securesms.emoji.EmojiSource.Companion.latest
import org.gchat.securesms.util.TextSecurePreferences
import java.util.function.Consumer

class EmojiKeyboardPageRepository(private val context: Context) {
  fun getEmoji(consumer: Consumer<List<EmojiPageModel>>) {
    SignalExecutors.BOUNDED.execute {
      val list = mutableListOf<EmojiPageModel>()
      list += RecentEmojiPageModel(context, TextSecurePreferences.RECENT_STORAGE_KEY)
      list += latest.displayPages
      consumer.accept(list)
    }
  }
}
