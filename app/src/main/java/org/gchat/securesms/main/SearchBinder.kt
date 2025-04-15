package org.gchat.securesms.main

import android.widget.ImageView
import org.gchat.securesms.components.Material3SearchToolbar
import org.gchat.securesms.util.views.Stub

interface SearchBinder {
  fun getSearchAction(): ImageView

  fun getSearchToolbar(): Stub<Material3SearchToolbar>

  fun onSearchOpened()

  fun onSearchClosed()
}
