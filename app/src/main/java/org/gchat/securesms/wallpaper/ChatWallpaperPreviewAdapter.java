package org.gchat.securesms.wallpaper;

import org.gchat.securesms.R;
import org.gchat.securesms.util.adapter.mapping.MappingAdapter;

class ChatWallpaperPreviewAdapter extends MappingAdapter {
  ChatWallpaperPreviewAdapter() {
    registerFactory(ChatWallpaperSelectionMappingModel.class, ChatWallpaperViewHolder.createFactory(R.layout.chat_wallpaper_preview_fragment_adapter_item, null, null));
  }
}
