package org.gchat.securesms.components.settings.app.subscription.receipts.detail

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.database.model.InAppPaymentReceiptRecord

class DonationReceiptDetailRepository {
  fun getDonationReceiptRecord(id: Long): Single<InAppPaymentReceiptRecord> {
    return Single.fromCallable<InAppPaymentReceiptRecord> {
      SignalDatabase.donationReceipts.getReceipt(id)!!
    }.subscribeOn(Schedulers.io())
  }
}
