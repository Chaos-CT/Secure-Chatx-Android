package org.gchat.securesms.stories.settings.story

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.signal.core.util.concurrent.SignalExecutors
import org.gchat.securesms.database.GroupTable
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.dependencies.AppDependencies
import org.gchat.securesms.keyvalue.SignalStore
import org.gchat.securesms.recipients.Recipient
import org.gchat.securesms.recipients.RecipientId
import org.gchat.securesms.sms.MessageSender
import org.gchat.securesms.storage.StorageSyncHelper
import org.gchat.securesms.stories.Stories

class StoriesPrivacySettingsRepository {
  fun markGroupsAsStories(groups: List<RecipientId>): Completable {
    return Completable.fromCallable {
      SignalDatabase.groups.setShowAsStoryState(groups, GroupTable.ShowAsStoryState.ALWAYS)
      SignalDatabase.recipients.markNeedsSync(groups)
      StorageSyncHelper.scheduleSyncForDataChange()
    }
  }

  fun setStoriesEnabled(isEnabled: Boolean): Completable {
    return Completable.fromAction {
      SignalStore.story.isFeatureDisabled = !isEnabled
      Stories.onStorySettingsChanged(Recipient.self().id)
      AppDependencies.resetNetwork()

      SignalDatabase.messages.getAllOutgoingStories(false, -1).use { reader ->
        reader.map { record -> record.id }
      }.forEach { messageId ->
        MessageSender.sendRemoteDelete(messageId)
      }
    }.subscribeOn(Schedulers.io())
  }

  fun onSettingsChanged() {
    SignalExecutors.BOUNDED_IO.execute {
      Stories.onStorySettingsChanged(Recipient.self().id)
    }
  }

  fun userHasOutgoingStories(): Single<Boolean> {
    return Single.fromCallable {
      SignalDatabase.messages.getAllOutgoingStories(false, -1).use {
        it.iterator().hasNext()
      }
    }.subscribeOn(Schedulers.io())
  }
}
