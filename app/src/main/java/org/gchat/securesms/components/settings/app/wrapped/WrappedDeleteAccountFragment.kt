package org.gchat.securesms.components.settings.app.wrapped

import androidx.fragment.app.Fragment
import org.gchat.securesms.R
import org.gchat.securesms.delete.DeleteAccountFragment

class WrappedDeleteAccountFragment : SettingsWrapperFragment() {
  override fun getFragment(): Fragment {
    toolbar.setTitle(R.string.preferences__delete_account)
    return DeleteAccountFragment()
  }
}
