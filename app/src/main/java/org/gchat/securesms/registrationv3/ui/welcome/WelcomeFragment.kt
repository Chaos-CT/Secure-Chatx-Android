/*
 * Copyright 2024 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.registrationv3.ui.welcome

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.signal.core.util.getSerializableCompat
import org.signal.core.util.logging.Log
import org.gchat.securesms.LoggingFragment
import org.gchat.securesms.R
import org.gchat.securesms.components.ViewBinderDelegate
import org.gchat.securesms.databinding.FragmentRegistrationWelcomeV3Binding
import org.gchat.securesms.permissions.Permissions
import org.gchat.securesms.registration.fragments.RegistrationViewDelegate.setDebugLogSubmitMultiTapView
import org.gchat.securesms.registration.fragments.WelcomePermissions
import org.gchat.securesms.registrationv3.ui.RegistrationCheckpoint
import org.gchat.securesms.registrationv3.ui.RegistrationViewModel
import org.gchat.securesms.registrationv3.ui.permissions.GrantPermissionsFragment
import org.gchat.securesms.registrationv3.ui.phonenumber.EnterPhoneNumberMode
import org.gchat.securesms.util.BackupUtil
import org.gchat.securesms.util.CommunicationActions
import org.gchat.securesms.util.navigation.safeNavigate
import org.gchat.securesms.util.visible

/**
 * First screen that is displayed on the very first app launch.
 */
class WelcomeFragment : LoggingFragment(R.layout.fragment_registration_welcome_v3) {
  companion object {
    private val TAG = Log.tag(WelcomeFragment::class.java)
    private const val TERMS_AND_CONDITIONS_URL = "https://signal.org/legal"
  }

  private val sharedViewModel by activityViewModels<RegistrationViewModel>()
  private val binding: FragmentRegistrationWelcomeV3Binding by ViewBinderDelegate(FragmentRegistrationWelcomeV3Binding::bind)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setDebugLogSubmitMultiTapView(binding.image)
    setDebugLogSubmitMultiTapView(binding.title)

    binding.welcomeContinueButton.setOnClickListener { onContinueClicked() }
    binding.welcomeTermsButton.setOnClickListener { onTermsClicked() }
    binding.welcomeTransferOrRestore.setOnClickListener { onRestoreOrTransferClicked() }
    binding.welcomeTransferOrRestore.visible = !sharedViewModel.isReregister

    childFragmentManager.setFragmentResultListener(RestoreWelcomeBottomSheet.REQUEST_KEY, viewLifecycleOwner) { requestKey, bundle ->
      if (requestKey == RestoreWelcomeBottomSheet.REQUEST_KEY) {
        when (val userSelection = bundle.getSerializableCompat(RestoreWelcomeBottomSheet.REQUEST_KEY, WelcomeUserSelection::class.java)) {
          WelcomeUserSelection.RESTORE_WITH_OLD_PHONE,
          WelcomeUserSelection.RESTORE_WITH_NO_PHONE -> afterRestoreOrTransferClicked(userSelection)
          else -> Unit
        }
      }
    }

    if (Permissions.isRuntimePermissionsRequired()) {
      parentFragmentManager.setFragmentResultListener(GrantPermissionsFragment.REQUEST_KEY, viewLifecycleOwner) { requestKey, bundle ->
        if (requestKey == GrantPermissionsFragment.REQUEST_KEY) {
          when (val userSelection = bundle.getSerializableCompat(GrantPermissionsFragment.REQUEST_KEY, WelcomeUserSelection::class.java)) {
            WelcomeUserSelection.RESTORE_WITH_OLD_PHONE,
            WelcomeUserSelection.RESTORE_WITH_NO_PHONE -> navigateToNextScreenViaRestore(userSelection)
            WelcomeUserSelection.CONTINUE -> navigateToNextScreenViaContinue()
            null -> Unit
          }
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()
    sharedViewModel.resetRestoreDecision()
  }

  private fun onContinueClicked() {
    if (Permissions.isRuntimePermissionsRequired() && !hasAllPermissions()) {
      findNavController().safeNavigate(WelcomeFragmentDirections.actionWelcomeFragmentToGrantPermissionsFragment(WelcomeUserSelection.CONTINUE))
    } else {
      navigateToNextScreenViaContinue()
    }
  }

  private fun navigateToNextScreenViaContinue() {
    sharedViewModel.maybePrefillE164(requireContext())
    findNavController().safeNavigate(WelcomeFragmentDirections.goToEnterPhoneNumber(EnterPhoneNumberMode.NORMAL))
  }

  private fun onTermsClicked() {
    CommunicationActions.openBrowserLink(requireContext(), TERMS_AND_CONDITIONS_URL)
  }

  private fun onRestoreOrTransferClicked() {
    RestoreWelcomeBottomSheet().show(childFragmentManager, null)
  }

  private fun afterRestoreOrTransferClicked(userSelection: WelcomeUserSelection) {
    if (Permissions.isRuntimePermissionsRequired() && !hasAllPermissions()) {
      findNavController().safeNavigate(WelcomeFragmentDirections.actionWelcomeFragmentToGrantPermissionsFragment(userSelection))
    } else {
      navigateToNextScreenViaRestore(userSelection)
    }
  }

  private fun navigateToNextScreenViaRestore(userSelection: WelcomeUserSelection) {
    sharedViewModel.maybePrefillE164(requireContext())
    sharedViewModel.setRegistrationCheckpoint(RegistrationCheckpoint.PERMISSIONS_GRANTED)

    when (userSelection) {
      WelcomeUserSelection.CONTINUE -> throw IllegalArgumentException()
      WelcomeUserSelection.RESTORE_WITH_OLD_PHONE -> {
        sharedViewModel.intendToRestore(hasOldDevice = true, fromRemote = true)
        findNavController().safeNavigate(WelcomeFragmentDirections.goToRestoreViaQr())
      }
      WelcomeUserSelection.RESTORE_WITH_NO_PHONE -> {
        sharedViewModel.intendToRestore(hasOldDevice = false, fromRemote = true)
        findNavController().safeNavigate(WelcomeFragmentDirections.goToSelectRestoreMethod(userSelection))
      }
    }
  }

  private fun hasAllPermissions(): Boolean {
    val isUserSelectionRequired = BackupUtil.isUserSelectionRequired(requireContext())
    return WelcomePermissions.getWelcomePermissions(isUserSelectionRequired).all { ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED }
  }
}
