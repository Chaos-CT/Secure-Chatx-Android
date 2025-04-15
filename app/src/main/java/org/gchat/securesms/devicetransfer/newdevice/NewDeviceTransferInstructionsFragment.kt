package org.gchat.securesms.devicetransfer.newdevice

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import org.greenrobot.eventbus.EventBus
import org.signal.devicetransfer.TransferStatus
import org.gchat.securesms.LoggingFragment
import org.gchat.securesms.R
import org.gchat.securesms.util.navigation.safeNavigate

/**
 * Shows instructions for new device to being transfer.
 */
class NewDeviceTransferInstructionsFragment : LoggingFragment(R.layout.new_device_transfer_instructions_fragment) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    view
      .findViewById<View>(R.id.new_device_transfer_instructions_fragment_continue)
      .setOnClickListener { findNavController().safeNavigate(R.id.action_device_transfer_setup) }
  }

  override fun onResume() {
    super.onResume()
    EventBus.getDefault().removeStickyEvent(TransferStatus::class.java)
  }
}
