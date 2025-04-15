package org.gchat.securesms.components.settings.app.wrapped

import androidx.fragment.app.Fragment
import org.gchat.securesms.R
import org.gchat.securesms.preferences.AdvancedPinPreferenceFragment

class WrappedAdvancedPinPreferenceFragment : SettingsWrapperFragment() {
  override fun getFragment(): Fragment {
    toolbar.setTitle(R.string.preferences__advanced_pin_settings_title)
    return AdvancedPinPreferenceFragment()
  }
}
