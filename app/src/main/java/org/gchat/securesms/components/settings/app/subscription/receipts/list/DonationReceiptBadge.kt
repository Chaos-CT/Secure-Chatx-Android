package org.gchat.securesms.components.settings.app.subscription.receipts.list

import org.gchat.securesms.badges.models.Badge
import org.gchat.securesms.database.model.InAppPaymentReceiptRecord

data class DonationReceiptBadge(
  val type: InAppPaymentReceiptRecord.Type,
  val level: Int,
  val badge: Badge
)
