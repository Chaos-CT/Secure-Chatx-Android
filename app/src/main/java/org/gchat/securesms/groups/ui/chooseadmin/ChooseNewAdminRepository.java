package org.gchat.securesms.groups.ui.chooseadmin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import org.gchat.securesms.groups.GroupChangeException;
import org.gchat.securesms.groups.GroupId;
import org.gchat.securesms.groups.GroupManager;
import org.gchat.securesms.groups.ui.GroupChangeFailureReason;
import org.gchat.securesms.groups.ui.GroupChangeResult;
import org.gchat.securesms.recipients.RecipientId;

import java.io.IOException;
import java.util.List;

public final class ChooseNewAdminRepository {
  private final Application context;

  ChooseNewAdminRepository(@NonNull Application context) {
    this.context = context;
  }

  @WorkerThread
  @NonNull GroupChangeResult updateAdminsAndLeave(@NonNull GroupId.V2 groupId, @NonNull List<RecipientId> newAdminIds) {
    try {
      GroupManager.addMemberAdminsAndLeaveGroup(context, groupId, newAdminIds);
      return GroupChangeResult.SUCCESS;
    } catch (GroupChangeException | IOException e) {
      return GroupChangeResult.failure(GroupChangeFailureReason.fromException(e));
    }
  }
}
