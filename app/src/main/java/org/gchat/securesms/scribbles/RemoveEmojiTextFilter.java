package org.gchat.securesms.scribbles;

import androidx.annotation.NonNull;

import org.signal.imageeditor.core.HiddenEditText;
import org.gchat.securesms.components.emoji.EmojiUtil;

class RemoveEmojiTextFilter implements HiddenEditText.TextFilter {
  @Override
  public String filter(@NonNull String text) {
    return EmojiUtil.stripEmoji(text);
  }
}
