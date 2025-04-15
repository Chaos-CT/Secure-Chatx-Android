package org.gchat.securesms.conversation.ui.mentions

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.gchat.securesms.database.RecipientTable
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.recipients.Recipient
import org.gchat.securesms.recipients.RecipientId

/**
 * Search for members that match the query for rendering in the mentions picker during message compose.
 */
class MentionsPickerRepositoryV2(
  private val recipients: RecipientTable = SignalDatabase.recipients
) {
  fun search(query: String, members: List<RecipientId>): Single<List<Recipient>> {
    return if (members.isEmpty()) {
      Single.just(emptyList())
    } else {
      Single
        .fromCallable { recipients.queryRecipientsForMentions(query, members) }
        .subscribeOn(Schedulers.io())
    }
  }
}
