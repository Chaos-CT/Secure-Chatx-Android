package org.gchat.securesms.stories.my

import androidx.fragment.app.Fragment
import org.gchat.securesms.components.FragmentWrapperActivity

class MyStoriesActivity : FragmentWrapperActivity() {
  override fun getFragment(): Fragment {
    return MyStoriesFragment()
  }
}
