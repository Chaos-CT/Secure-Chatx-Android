package org.gchat.securesms.groups.ui;

import androidx.annotation.NonNull;

import org.gchat.securesms.recipients.Recipient;

public interface RecipientClickListener {
  void onClick(@NonNull Recipient recipient);
}
