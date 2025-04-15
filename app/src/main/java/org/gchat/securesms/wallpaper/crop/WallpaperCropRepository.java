package org.gchat.securesms.wallpaper.crop;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import org.signal.core.util.logging.Log;
import org.gchat.securesms.database.SignalDatabase;
import org.gchat.securesms.dependencies.AppDependencies;
import org.gchat.securesms.keyvalue.SignalStore;
import org.gchat.securesms.recipients.RecipientId;
import org.gchat.securesms.wallpaper.ChatWallpaper;
import org.gchat.securesms.wallpaper.WallpaperStorage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

final class WallpaperCropRepository {

  private static final String TAG = Log.tag(WallpaperCropRepository.class);

  @Nullable private final RecipientId recipientId;
  private final           Context     context;

  public WallpaperCropRepository(@Nullable RecipientId recipientId) {
    this.context     = AppDependencies.getApplication();
    this.recipientId = recipientId;
  }

  @WorkerThread
  @NonNull ChatWallpaper setWallPaper(byte[] bytes) throws IOException {
    try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
      ChatWallpaper wallpaper = WallpaperStorage.save(inputStream);

      if (recipientId != null) {
        Log.i(TAG, "Setting image wallpaper for " + recipientId);
        SignalDatabase.recipients().setWallpaper(recipientId, wallpaper, true);
      } else {
        Log.i(TAG, "Setting image wallpaper for default");
        SignalStore.wallpaper().setWallpaper(wallpaper);
      }

      return wallpaper;
    }
  }
}
