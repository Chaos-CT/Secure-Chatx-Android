package org.gchat.securesms.groups.ui;

import androidx.annotation.NonNull;

import org.gchat.securesms.recipients.Recipient;

public interface RecipientLongClickListener {
  boolean onLongClick(@NonNull Recipient recipient);
}
