package org.gchat.securesms.components.settings.app.wrapped

import androidx.fragment.app.Fragment
import org.gchat.securesms.R
import org.gchat.securesms.preferences.BackupsPreferenceFragment

class WrappedBackupsPreferenceFragment : SettingsWrapperFragment() {
  override fun getFragment(): Fragment {
    toolbar.setTitle(R.string.BackupsPreferenceFragment__chat_backups)
    return BackupsPreferenceFragment()
  }
}
