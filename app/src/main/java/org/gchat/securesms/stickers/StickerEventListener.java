package org.gchat.securesms.stickers;

import androidx.annotation.NonNull;

import org.gchat.securesms.database.model.StickerRecord;

public interface StickerEventListener {
  void onStickerSelected(@NonNull StickerRecord sticker);

  void onStickerManagementClicked();
}
