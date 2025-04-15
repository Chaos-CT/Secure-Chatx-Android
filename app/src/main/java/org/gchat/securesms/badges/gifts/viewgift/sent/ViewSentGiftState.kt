package org.gchat.securesms.badges.gifts.viewgift.sent

import org.gchat.securesms.badges.models.Badge
import org.gchat.securesms.recipients.Recipient

data class ViewSentGiftState(
  val recipient: Recipient? = null,
  val badge: Badge? = null
)
