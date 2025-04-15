package org.gchat.securesms.stories.tabs

import io.reactivex.rxjava3.core.Flowable
import org.gchat.securesms.database.RxDatabaseObserver
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.recipients.Recipient

class ConversationListTabRepository {

  fun getNumberOfUnreadMessages(): Flowable<Long> {
    return RxDatabaseObserver.conversationList.map { SignalDatabase.threads.getUnreadMessageCount() }
  }

  fun getNumberOfUnseenStories(): Flowable<Long> {
    return RxDatabaseObserver.conversationList.map {
      SignalDatabase
        .messages
        .getUnreadStoryThreadRecipientIds()
        .map { Recipient.resolved(it) }
        .filterNot { it.shouldHideStory }
        .size
        .toLong()
    }
  }

  fun getHasFailedOutgoingStories(): Flowable<Boolean> {
    return RxDatabaseObserver.conversationList.map { SignalDatabase.messages.hasFailedOutgoingStory() }
  }

  fun getNumberOfUnseenCalls(): Flowable<Long> {
    return RxDatabaseObserver.conversationList.map { SignalDatabase.calls.getUnreadMissedCallCount() }
  }
}
