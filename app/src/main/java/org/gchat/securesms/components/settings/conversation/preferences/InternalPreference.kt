package org.gchat.securesms.components.settings.conversation.preferences

import android.view.View
import org.gchat.securesms.R
import org.gchat.securesms.components.settings.PreferenceModel
import org.gchat.securesms.recipients.Recipient
import org.gchat.securesms.util.adapter.mapping.LayoutFactory
import org.gchat.securesms.util.adapter.mapping.MappingAdapter
import org.gchat.securesms.util.adapter.mapping.MappingViewHolder

object InternalPreference {

  fun register(adapter: MappingAdapter) {
    adapter.registerFactory(Model::class.java, LayoutFactory(::ViewHolder, R.layout.conversation_settings_internal_preference))
  }

  class Model(
    private val recipient: Recipient,
    val onInternalDetailsClicked: () -> Unit
  ) : PreferenceModel<Model>() {

    override fun areItemsTheSame(newItem: Model): Boolean {
      return recipient == newItem.recipient
    }
  }

  private class ViewHolder(itemView: View) : MappingViewHolder<Model>(itemView) {

    private val internalDetails: View = itemView.findViewById(R.id.internal_details)

    override fun bind(model: Model) {
      internalDetails.setOnClickListener { model.onInternalDetailsClicked() }
    }
  }
}
