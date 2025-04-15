package org.gchat.securesms.webrtc

import org.gchat.securesms.components.webrtc.CallParticipantsState
import org.gchat.securesms.service.webrtc.state.WebRtcEphemeralState

class CallParticipantsViewState(
  callParticipantsState: CallParticipantsState,
  ephemeralState: WebRtcEphemeralState,
  val isPortrait: Boolean,
  val isLandscapeEnabled: Boolean
) {

  val callParticipantsState = CallParticipantsState.update(callParticipantsState, ephemeralState)
}
