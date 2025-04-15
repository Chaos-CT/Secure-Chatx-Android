package org.gchat.securesms.components.settings.app.appearance

import org.gchat.securesms.keyvalue.SettingsValues

data class AppearanceSettingsState(
  val theme: SettingsValues.Theme,
  val messageFontSize: Int,
  val language: String,
  val isCompactNavigationBar: Boolean
)
