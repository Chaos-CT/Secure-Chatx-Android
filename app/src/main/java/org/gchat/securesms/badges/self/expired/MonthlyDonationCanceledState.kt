/*
 * Copyright 2024 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.badges.self.expired

import androidx.annotation.StringRes
import org.gchat.securesms.badges.models.Badge

data class MonthlyDonationCanceledState(
  val loadState: LoadState = LoadState.LOADING,
  val badge: Badge? = null,
  @StringRes val errorMessage: Int = -1
) {
  enum class LoadState {
    LOADING,
    READY,
    FAILED
  }
}
