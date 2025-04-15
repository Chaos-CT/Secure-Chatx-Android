package org.gchat.securesms.messagedetails;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import org.signal.core.util.concurrent.SignalExecutors;
import org.gchat.securesms.database.DatabaseObserver;
import org.gchat.securesms.database.NoSuchMessageException;
import org.gchat.securesms.database.SignalDatabase;
import org.gchat.securesms.database.model.MessageId;
import org.gchat.securesms.database.model.MessageRecord;
import org.gchat.securesms.dependencies.AppDependencies;

final class MessageRecordLiveData extends LiveData<MessageRecord> {

  private final DatabaseObserver.Observer observer;
  private final MessageId                 messageId;

  MessageRecordLiveData(MessageId messageId) {
    this.messageId = messageId;
    this.observer  = this::retrieveMessageRecordActual;
  }

  @Override
  protected void onActive() {
    SignalExecutors.BOUNDED_IO.execute(this::retrieveMessageRecordActual);
  }

  @Override
  protected void onInactive() {
    AppDependencies.getDatabaseObserver().unregisterObserver(observer);
  }

  @WorkerThread
  private synchronized void retrieveMessageRecordActual() {
    try {
      MessageRecord record = SignalDatabase.messages().getMessageRecord(messageId.getId());

      if (record.isPaymentNotification()) {
        record = SignalDatabase.payments().updateMessageWithPayment(record);
      }

      postValue(record);
      AppDependencies.getDatabaseObserver().registerVerboseConversationObserver(record.getThreadId(), observer);
    } catch (NoSuchMessageException ignored) {
      postValue(null);
    }
  }
}
