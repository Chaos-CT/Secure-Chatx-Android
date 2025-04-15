package org.gchat.securesms.wallpaper;

import androidx.annotation.Nullable;

import org.gchat.securesms.R;
import org.gchat.securesms.util.adapter.mapping.MappingAdapter;

class ChatWallpaperSelectionAdapter extends MappingAdapter {
  ChatWallpaperSelectionAdapter(@Nullable ChatWallpaperViewHolder.EventListener eventListener) {
    registerFactory(ChatWallpaperSelectionMappingModel.class, ChatWallpaperViewHolder.createFactory(R.layout.chat_wallpaper_selection_fragment_adapter_item, eventListener, null));
  }
}
