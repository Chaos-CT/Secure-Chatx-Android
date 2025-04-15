package org.gchat.securesms.util;

import androidx.annotation.StyleRes;

import org.gchat.securesms.R;

public class DynamicNoActionBarInviteTheme extends DynamicTheme {

  protected @StyleRes int getTheme() {
    return R.style.Signal_DayNight_Invite;
  }
}
