/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.components.settings.app.subscription.completed

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.gchat.securesms.badges.Badges
import org.gchat.securesms.badges.models.Badge
import org.gchat.securesms.database.model.databaseprotos.TerminalDonationQueue
import org.gchat.securesms.dependencies.AppDependencies
import org.whispersystems.signalservice.api.services.DonationsService
import java.util.Locale

class TerminalDonationRepository(
  private val donationsService: DonationsService = AppDependencies.donationsService
) {
  fun getBadge(terminalDonation: TerminalDonationQueue.TerminalDonation): Single<Badge> {
    return Single
      .fromCallable { donationsService.getDonationsConfiguration(Locale.getDefault()) }
      .flatMap { it.flattenResult() }
      .map { it.levels[terminalDonation.level.toInt()]!! }
      .map { Badges.fromServiceBadge(it.badge) }
      .subscribeOn(Schedulers.io())
  }
}
