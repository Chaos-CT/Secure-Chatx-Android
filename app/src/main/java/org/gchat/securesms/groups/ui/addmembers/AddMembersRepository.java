package org.gchat.securesms.groups.ui.addmembers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import org.gchat.securesms.contacts.SelectedContact;
import org.gchat.securesms.database.SignalDatabase;
import org.gchat.securesms.dependencies.AppDependencies;
import org.gchat.securesms.groups.GroupId;
import org.gchat.securesms.recipients.RecipientId;

final class AddMembersRepository {

  private final Context context;
  private final GroupId groupId;

  AddMembersRepository(@NonNull GroupId groupId) {
    this.groupId = groupId;
    this.context = AppDependencies.getApplication();
  }

  @WorkerThread
  RecipientId getOrCreateRecipientId(@NonNull SelectedContact selectedContact) {
    return selectedContact.getOrCreateRecipientId();
  }

  @WorkerThread
  String getGroupTitle() {
    return SignalDatabase.groups().requireGroup(groupId).getTitle();
  }
}
