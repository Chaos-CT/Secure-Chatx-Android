package org.gchat.securesms.components.settings.app.subscription.subscribe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.gchat.securesms.R
import org.gchat.securesms.components.FixedRoundedCornerBottomSheetDialogFragment

class SubscribeLearnMoreBottomSheetDialogFragment : FixedRoundedCornerBottomSheetDialogFragment() {

  override val peekHeightPercentage: Float = 1f

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.subscribe_learn_more_bottom_sheet_dialog_fragment, container, false)
  }
}
