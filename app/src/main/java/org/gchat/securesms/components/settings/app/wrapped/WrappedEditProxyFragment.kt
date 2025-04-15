package org.gchat.securesms.components.settings.app.wrapped

import androidx.fragment.app.Fragment
import org.gchat.securesms.R
import org.gchat.securesms.preferences.EditProxyFragment

class WrappedEditProxyFragment : SettingsWrapperFragment() {
  override fun getFragment(): Fragment {
    toolbar.setTitle(R.string.preferences_use_proxy)
    return EditProxyFragment()
  }
}
