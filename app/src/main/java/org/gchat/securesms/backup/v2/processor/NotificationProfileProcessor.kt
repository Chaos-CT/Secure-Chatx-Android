/*
 * Copyright 2024 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.backup.v2.processor

import org.signal.core.util.insertInto
import org.signal.core.util.logging.Log
import org.signal.core.util.toInt
import org.gchat.securesms.backup.v2.ExportState
import org.gchat.securesms.backup.v2.ImportState
import org.gchat.securesms.backup.v2.proto.Frame
import org.gchat.securesms.backup.v2.stream.BackupFrameEmitter
import org.gchat.securesms.conversation.colors.AvatarColor
import org.gchat.securesms.database.NotificationProfileTables.NotificationProfileAllowedMembersTable
import org.gchat.securesms.database.NotificationProfileTables.NotificationProfileScheduleTable
import org.gchat.securesms.database.NotificationProfileTables.NotificationProfileTable
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.database.serialize
import org.gchat.securesms.notifications.profiles.NotificationProfile
import org.gchat.securesms.recipients.RecipientId
import java.lang.IllegalStateException
import java.time.DayOfWeek
import org.gchat.securesms.backup.v2.proto.NotificationProfile as NotificationProfileProto

/**
 * Handles exporting and importing [NotificationProfile] models.
 */
object NotificationProfileProcessor {

  private val TAG = Log.tag(NotificationProfileProcessor::class)

  fun export(db: SignalDatabase, exportState: ExportState, emitter: BackupFrameEmitter) {
    db.notificationProfileTables
      .getProfiles()
      .forEach { profile ->
        val frame = profile.toBackupFrame(includeRecipient = { id -> exportState.recipientIds.contains(id.toLong()) })
        emitter.emit(frame)
      }
  }

  fun import(profile: NotificationProfileProto, importState: ImportState) {
    val profileId = SignalDatabase
      .writableDatabase
      .insertInto(NotificationProfileTable.TABLE_NAME)
      .values(
        NotificationProfileTable.NAME to profile.name,
        NotificationProfileTable.EMOJI to (profile.emoji ?: ""),
        NotificationProfileTable.COLOR to (AvatarColor.fromColor(profile.color) ?: AvatarColor.random()).serialize(),
        NotificationProfileTable.CREATED_AT to profile.createdAtMs,
        NotificationProfileTable.ALLOW_ALL_CALLS to profile.allowAllCalls.toInt(),
        NotificationProfileTable.ALLOW_ALL_MENTIONS to profile.allowAllMentions.toInt()
      )
      .run()

    if (profileId < 0) {
      Log.w(TAG, "Notification profile name already exists")
      return
    }

    SignalDatabase
      .writableDatabase
      .insertInto(NotificationProfileScheduleTable.TABLE_NAME)
      .values(
        NotificationProfileScheduleTable.NOTIFICATION_PROFILE_ID to profileId,
        NotificationProfileScheduleTable.ENABLED to profile.scheduleEnabled.toInt(),
        NotificationProfileScheduleTable.START to profile.scheduleStartTime,
        NotificationProfileScheduleTable.END to profile.scheduleEndTime,
        NotificationProfileScheduleTable.DAYS_ENABLED to profile.scheduleDaysEnabled.map { it.toLocal() }.toSet().serialize()
      )
      .run()

    profile
      .allowedMembers
      .mapNotNull { importState.remoteToLocalRecipientId[it] }
      .forEach { recipientId ->
        SignalDatabase
          .writableDatabase
          .insertInto(NotificationProfileAllowedMembersTable.TABLE_NAME)
          .values(
            NotificationProfileAllowedMembersTable.NOTIFICATION_PROFILE_ID to profileId,
            NotificationProfileAllowedMembersTable.RECIPIENT_ID to recipientId.serialize()
          )
          .run()
      }
  }
}

private fun NotificationProfile.toBackupFrame(includeRecipient: (RecipientId) -> Boolean): Frame {
  val profile = NotificationProfileProto(
    name = this.name,
    emoji = this.emoji.takeIf { it.isNotBlank() },
    color = this.color.colorInt(),
    createdAtMs = this.createdAt,
    allowAllCalls = this.allowAllCalls,
    allowAllMentions = this.allowAllMentions,
    allowedMembers = this.allowedMembers.filter { includeRecipient(it) }.map { it.toLong() },
    scheduleEnabled = this.schedule.enabled,
    scheduleStartTime = this.schedule.start,
    scheduleEndTime = this.schedule.end,
    scheduleDaysEnabled = this.schedule.daysEnabled.map { it.toBackupProto() }
  )

  return Frame(notificationProfile = profile)
}

private fun DayOfWeek.toBackupProto(): NotificationProfileProto.DayOfWeek {
  return when (this) {
    DayOfWeek.MONDAY -> NotificationProfileProto.DayOfWeek.MONDAY
    DayOfWeek.TUESDAY -> NotificationProfileProto.DayOfWeek.TUESDAY
    DayOfWeek.WEDNESDAY -> NotificationProfileProto.DayOfWeek.WEDNESDAY
    DayOfWeek.THURSDAY -> NotificationProfileProto.DayOfWeek.THURSDAY
    DayOfWeek.FRIDAY -> NotificationProfileProto.DayOfWeek.FRIDAY
    DayOfWeek.SATURDAY -> NotificationProfileProto.DayOfWeek.SATURDAY
    DayOfWeek.SUNDAY -> NotificationProfileProto.DayOfWeek.SUNDAY
  }
}

private fun NotificationProfileProto.DayOfWeek.toLocal(): DayOfWeek {
  return when (this) {
    NotificationProfileProto.DayOfWeek.UNKNOWN -> throw IllegalStateException()
    NotificationProfileProto.DayOfWeek.MONDAY -> DayOfWeek.MONDAY
    NotificationProfileProto.DayOfWeek.TUESDAY -> DayOfWeek.TUESDAY
    NotificationProfileProto.DayOfWeek.WEDNESDAY -> DayOfWeek.WEDNESDAY
    NotificationProfileProto.DayOfWeek.THURSDAY -> DayOfWeek.THURSDAY
    NotificationProfileProto.DayOfWeek.FRIDAY -> DayOfWeek.FRIDAY
    NotificationProfileProto.DayOfWeek.SATURDAY -> DayOfWeek.SATURDAY
    NotificationProfileProto.DayOfWeek.SUNDAY -> DayOfWeek.SUNDAY
  }
}
