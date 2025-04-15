/*
 * Copyright 2024 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.components.settings.app.backups.remote

import androidx.compose.runtime.Composable
import androidx.navigation.fragment.findNavController
import org.gchat.securesms.backup.v2.ui.subscription.MessageBackupsKeyRecordScreen
import org.gchat.securesms.compose.ComposeFragment
import org.gchat.securesms.keyvalue.SignalStore
import org.gchat.securesms.util.Util

/**
 * Fragment which only displays the backup key to the user.
 */
class BackupKeyDisplayFragment : ComposeFragment() {
  @Composable
  override fun FragmentContent() {
    MessageBackupsKeyRecordScreen(
      backupKey = SignalStore.account.accountEntropyPool.value,
      onNavigationClick = { findNavController().popBackStack() },
      onCopyToClipboardClick = { Util.copyToClipboard(requireContext(), it) },
      onNextClick = { findNavController().popBackStack() }
    )
  }
}
