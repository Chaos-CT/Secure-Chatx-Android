/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.backup.v2.processor

import org.signal.core.util.logging.Log
import org.gchat.securesms.backup.v2.ExportState
import org.gchat.securesms.backup.v2.ImportState
import org.gchat.securesms.backup.v2.MessageBackupTier
import org.gchat.securesms.backup.v2.database.getThreadsForBackup
import org.gchat.securesms.backup.v2.importer.ChatArchiveImporter
import org.gchat.securesms.backup.v2.proto.Chat
import org.gchat.securesms.backup.v2.proto.Frame
import org.gchat.securesms.backup.v2.stream.BackupFrameEmitter
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.keyvalue.SignalStore
import org.gchat.securesms.recipients.RecipientId

/**
 * Handles importing/exporting [Chat] frames for an archive.
 */
object ChatArchiveProcessor {
  val TAG = Log.tag(ChatArchiveProcessor::class.java)

  fun export(db: SignalDatabase, exportState: ExportState, emitter: BackupFrameEmitter) {
    val includeImageWallpapers = SignalStore.backup.backupTier == MessageBackupTier.PAID
    Log.i(TAG, "Including wallpapers: $includeImageWallpapers")

    db.threadTable.getThreadsForBackup(db, includeImageWallpapers).use { reader ->
      for (chat in reader) {
        if (exportState.recipientIds.contains(chat.recipientId)) {
          exportState.threadIds.add(chat.id)
          exportState.threadIdToRecipientId[chat.id] = chat.recipientId
          emitter.emit(Frame(chat = chat))
        } else {
          Log.w(TAG, "dropping thread for deleted recipient ${chat.recipientId}")
        }
      }
    }
  }

  fun import(chat: Chat, importState: ImportState) {
    val recipientId: RecipientId? = importState.remoteToLocalRecipientId[chat.recipientId]
    if (recipientId == null) {
      Log.w(TAG, "Missing recipient for chat ${chat.id}")
      return
    }

    val threadId = ChatArchiveImporter.import(chat, recipientId, importState)
    importState.chatIdToLocalRecipientId[chat.id] = recipientId
    importState.chatIdToLocalThreadId[chat.id] = threadId
    importState.chatIdToBackupRecipientId[chat.id] = chat.recipientId
    importState.recipientIdToLocalThreadId[recipientId] = threadId
  }
}
