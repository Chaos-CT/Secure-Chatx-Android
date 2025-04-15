/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.backup.v2.database

import org.signal.core.util.select
import org.signal.core.util.withinTransaction
import org.gchat.securesms.backup.v2.exporters.DistributionListArchiveExporter
import org.gchat.securesms.database.DistributionListTables
import org.gchat.securesms.database.model.DistributionListId
import org.gchat.securesms.database.model.DistributionListPrivacyMode
import org.gchat.securesms.recipients.RecipientId

fun DistributionListTables.getAllForBackup(selfRecipientId: RecipientId): DistributionListArchiveExporter {
  val cursor = readableDatabase
    .select()
    .from(DistributionListTables.ListTable.TABLE_NAME)
    .run()

  return DistributionListArchiveExporter(cursor, this, selfRecipientId)
}

fun DistributionListTables.getMembersForBackup(id: DistributionListId): List<RecipientId> {
  lateinit var privacyMode: DistributionListPrivacyMode
  lateinit var rawMembers: List<RecipientId>

  readableDatabase.withinTransaction {
    privacyMode = getPrivacyMode(id)
    rawMembers = getRawMembers(id, privacyMode)
  }

  return when (privacyMode) {
    DistributionListPrivacyMode.ALL -> emptyList()
    DistributionListPrivacyMode.ONLY_WITH -> rawMembers
    DistributionListPrivacyMode.ALL_EXCEPT -> rawMembers
  }
}
