/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.recipients.ui.about

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.gchat.securesms.database.IdentityTable
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.dependencies.AppDependencies
import org.gchat.securesms.recipients.RecipientId

class AboutSheetRepository {
  fun getGroupsInCommonCount(recipientId: RecipientId): Single<Int> {
    return Single.fromCallable {
      SignalDatabase.groups.getPushGroupsContainingMember(recipientId).size
    }.subscribeOn(Schedulers.io())
  }

  fun getVerified(recipientId: RecipientId): Single<Boolean> {
    return Single.fromCallable {
      val identityRecord = AppDependencies.protocolStore.aci().identities().getIdentityRecord(recipientId)
      identityRecord.isPresent && identityRecord.get().verifiedStatus == IdentityTable.VerifiedStatus.VERIFIED
    }.subscribeOn(Schedulers.io())
  }
}
