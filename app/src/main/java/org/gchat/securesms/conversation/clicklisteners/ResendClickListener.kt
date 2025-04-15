/*
 * Copyright 2024 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.conversation.clicklisteners

import android.view.View
import org.signal.core.util.logging.Log
import org.gchat.securesms.database.model.MessageRecord
import org.gchat.securesms.mms.Slide
import org.gchat.securesms.mms.SlidesClickedListener
import org.gchat.securesms.sms.MessageSender

class ResendClickListener(private val messageRecord: MessageRecord) : SlidesClickedListener {
  override fun onClick(v: View?, slides: MutableList<Slide>?) {
    if (v == null) {
      Log.w(TAG, "Could not resend message, view was null!")
      return
    }

    MessageSender.resend(v.context, messageRecord)
  }

  companion object {
    private val TAG = Log.tag(ResendClickListener::class.java)
  }
}
