package org.gchat.securesms.video.interfaces

fun interface TranscoderCancelationSignal {
  fun isCanceled(): Boolean
}
