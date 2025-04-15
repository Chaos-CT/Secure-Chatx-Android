/*
 * Copyright 2024 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.components.settings.app.subscription

import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import org.signal.core.util.getSerializableCompat
import org.gchat.securesms.backup.v2.MessageBackupTier
import org.gchat.securesms.backup.v2.ui.CreateBackupBottomSheet
import org.gchat.securesms.backup.v2.ui.subscription.MessageBackupsCheckoutActivity
import org.gchat.securesms.components.settings.app.subscription.donate.InAppPaymentProcessorAction
import org.gchat.securesms.util.BottomSheetUtil

object MessageBackupsCheckoutLauncher {

  fun Fragment.createBackupsCheckoutLauncher(
    onCreateBackupBottomSheetResultListener: OnCreateBackupBottomSheetResultListener = {} as OnCreateBackupBottomSheetResultListener
  ): ActivityResultLauncher<MessageBackupTier?> {
    childFragmentManager.setFragmentResultListener(CreateBackupBottomSheet.REQUEST_KEY, viewLifecycleOwner) { requestKey, bundle ->
      if (requestKey == CreateBackupBottomSheet.REQUEST_KEY) {
        val result = bundle.getSerializableCompat(CreateBackupBottomSheet.REQUEST_KEY, CreateBackupBottomSheet.Result::class.java)
        onCreateBackupBottomSheetResultListener.onCreateBackupBottomSheetResult(result != CreateBackupBottomSheet.Result.BACKUP_STARTED)
      }
    }

    return registerForActivityResult(MessageBackupsCheckoutActivity.Contract()) { result ->
      if (result?.action == InAppPaymentProcessorAction.PROCESS_NEW_IN_APP_PAYMENT || result?.action == InAppPaymentProcessorAction.UPDATE_SUBSCRIPTION) {
        CreateBackupBottomSheet().show(childFragmentManager, BottomSheetUtil.STANDARD_BOTTOM_SHEET_FRAGMENT_TAG)
      }
    }
  }

  fun interface OnCreateBackupBottomSheetResultListener {
    fun onCreateBackupBottomSheetResult(backUpLater: Boolean)
  }
}
