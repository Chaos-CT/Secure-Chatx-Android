package org.gchat.securesms.keyboard.emoji

import org.gchat.securesms.components.emoji.EmojiEventListener
import org.gchat.securesms.keyboard.emoji.search.EmojiSearchFragment

interface EmojiKeyboardCallback :
  EmojiEventListener,
  EmojiKeyboardPageFragment.Callback,
  EmojiSearchFragment.Callback
