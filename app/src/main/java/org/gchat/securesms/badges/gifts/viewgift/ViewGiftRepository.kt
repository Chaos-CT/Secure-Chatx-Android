package org.gchat.securesms.badges.gifts.viewgift

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.signal.libsignal.zkgroup.receipts.ReceiptCredentialPresentation
import org.gchat.securesms.badges.models.Badge
import org.gchat.securesms.components.settings.app.subscription.getBadge
import org.gchat.securesms.database.DatabaseObserver
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.database.model.MmsMessageRecord
import org.gchat.securesms.database.model.databaseprotos.GiftBadge
import org.gchat.securesms.dependencies.AppDependencies
import java.util.Locale

/**
 * Shared repository for getting information about a particular gift.
 */
class ViewGiftRepository {
  fun getBadge(giftBadge: GiftBadge): Single<Badge> {
    val presentation = ReceiptCredentialPresentation(giftBadge.redemptionToken.toByteArray())
    return Single
      .fromCallable {
        AppDependencies
          .donationsService
          .getDonationsConfiguration(Locale.getDefault())
      }
      .flatMap { it.flattenResult() }
      .map { it.getBadge(presentation.receiptLevel.toInt()) }
      .subscribeOn(Schedulers.io())
  }

  fun getGiftBadge(messageId: Long): Observable<GiftBadge> {
    return Observable.create { emitter ->
      fun refresh() {
        val record = SignalDatabase.messages.getMessageRecord(messageId)
        val giftBadge: GiftBadge = (record as MmsMessageRecord).giftBadge!!

        emitter.onNext(giftBadge)
      }

      val messageObserver = DatabaseObserver.MessageObserver {
        if (messageId == it.id) {
          refresh()
        }
      }

      AppDependencies.databaseObserver.registerMessageUpdateObserver(messageObserver)
      emitter.setCancellable {
        AppDependencies.databaseObserver.unregisterObserver(messageObserver)
      }

      refresh()
    }
  }
}
