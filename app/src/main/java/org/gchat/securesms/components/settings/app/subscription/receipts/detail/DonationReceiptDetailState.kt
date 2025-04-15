package org.gchat.securesms.components.settings.app.subscription.receipts.detail

import org.gchat.securesms.database.model.InAppPaymentReceiptRecord

data class DonationReceiptDetailState(
  val inAppPaymentReceiptRecord: InAppPaymentReceiptRecord? = null
)
