package org.gchat.securesms.components.emoji.parsing

import org.gchat.securesms.emoji.EmojiPage

data class EmojiDrawInfo(val page: EmojiPage, val index: Int, val emoji: String, val rawEmoji: String?, val jumboSheet: String?)
