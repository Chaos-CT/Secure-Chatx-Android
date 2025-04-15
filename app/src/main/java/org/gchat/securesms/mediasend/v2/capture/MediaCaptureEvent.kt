package org.gchat.securesms.mediasend.v2.capture

import org.gchat.securesms.mediasend.Media
import org.gchat.securesms.recipients.Recipient

sealed interface MediaCaptureEvent {
  data class MediaCaptureRendered(val media: Media) : MediaCaptureEvent
  data class UsernameScannedFromQrCode(val recipient: Recipient, val username: String) : MediaCaptureEvent
  data object DeviceLinkScannedFromQrCode : MediaCaptureEvent
  data object MediaCaptureRenderFailed : MediaCaptureEvent
  data class ReregistrationScannedFromQrCode(val data: String) : MediaCaptureEvent
}
