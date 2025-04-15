package org.gchat.securesms.database.loaders;

import android.content.Context;

import org.gchat.securesms.util.AbstractCursorLoader;

public abstract class MediaLoader extends AbstractCursorLoader {

  MediaLoader(Context context) {
    super(context);
  }

  public enum MediaType {
    GALLERY,
    DOCUMENT,
    AUDIO,
    ALL
  }
}
