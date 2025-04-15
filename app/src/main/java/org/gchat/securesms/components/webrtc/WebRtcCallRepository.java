package org.gchat.securesms.components.webrtc;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.core.util.Consumer;

import org.signal.core.util.concurrent.SignalExecutors;
import org.gchat.securesms.database.GroupTable;
import org.gchat.securesms.database.SignalDatabase;
import org.gchat.securesms.database.identity.IdentityRecordList;
import org.gchat.securesms.dependencies.AppDependencies;
import org.gchat.securesms.recipients.Recipient;

import java.util.Collections;
import java.util.List;

public final class WebRtcCallRepository {

  private WebRtcCallRepository() {}

  @WorkerThread
  public static void getIdentityRecords(@NonNull Recipient recipient, @NonNull Consumer<IdentityRecordList> consumer) {
    SignalExecutors.BOUNDED.execute(() -> {
      List<Recipient> recipients;

      if (recipient.isGroup()) {
        recipients = SignalDatabase.groups().getGroupMembers(recipient.requireGroupId(), GroupTable.MemberSet.FULL_MEMBERS_EXCLUDING_SELF);
      } else {
        recipients = Collections.singletonList(recipient);
      }

      consumer.accept(AppDependencies.getProtocolStore().aci().identities().getIdentityRecords(recipients));
    });
  }
}
