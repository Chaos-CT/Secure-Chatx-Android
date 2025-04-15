package org.gchat.securesms.payments.securitysetup

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.gchat.securesms.R
import org.gchat.securesms.databinding.PaymentsSecuritySetupFragmentBinding
import org.gchat.securesms.payments.preferences.PaymentsHomeFragmentDirections
import org.gchat.securesms.util.navigation.safeNavigate

/**
 * Fragment to let user know to enable payment lock to protect their funds
 */
class PaymentsSecuritySetupFragment : Fragment(R.layout.payments_security_setup_fragment) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val binding = PaymentsSecuritySetupFragmentBinding.bind(view)

    requireActivity().onBackPressedDispatcher.addCallback(
      viewLifecycleOwner,
      object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
          showSkipDialog()
        }
      }
    )
    binding.paymentsSecuritySetupEnableLock.setOnClickListener {
      findNavController().safeNavigate(PaymentsHomeFragmentDirections.actionPaymentsHomeToPrivacySettings(true))
    }
    binding.paymentsSecuritySetupFragmentNotNow.setOnClickListener { showSkipDialog() }
    binding.toolbar.setNavigationOnClickListener { showSkipDialog() }
  }

  private fun showSkipDialog() {
    MaterialAlertDialogBuilder(requireContext())
      .setTitle(getString(R.string.PaymentsSecuritySetupFragment__skip_this_step))
      .setMessage(getString(R.string.PaymentsSecuritySetupFragment__skipping_this_step))
      .setPositiveButton(R.string.PaymentsSecuritySetupFragment__skip) { _, _ -> findNavController().popBackStack() }
      .setNegativeButton(R.string.PaymentsSecuritySetupFragment__cancel) { _, _ -> }
      .setCancelable(false)
      .show()
  }
}
