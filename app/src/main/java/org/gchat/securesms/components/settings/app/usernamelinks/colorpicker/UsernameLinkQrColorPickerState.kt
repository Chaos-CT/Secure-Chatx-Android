package org.gchat.securesms.components.settings.app.usernamelinks.colorpicker

import kotlinx.collections.immutable.ImmutableList
import org.gchat.securesms.components.settings.app.usernamelinks.QrCodeState
import org.gchat.securesms.components.settings.app.usernamelinks.UsernameQrCodeColorScheme

data class UsernameLinkQrColorPickerState(
  val username: String,
  val qrCodeData: QrCodeState,
  val colorSchemes: ImmutableList<UsernameQrCodeColorScheme>,
  val selectedColorScheme: UsernameQrCodeColorScheme
)
