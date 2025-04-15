package org.gchat.securesms.components.settings.models

import android.view.View
import org.gchat.securesms.R
import org.gchat.securesms.components.settings.PreferenceModel
import org.gchat.securesms.util.adapter.mapping.LayoutFactory
import org.gchat.securesms.util.adapter.mapping.MappingAdapter
import org.gchat.securesms.util.adapter.mapping.MappingViewHolder

object IndeterminateLoadingCircle : PreferenceModel<IndeterminateLoadingCircle>() {
  override fun areItemsTheSame(newItem: IndeterminateLoadingCircle): Boolean = true

  private class ViewHolder(itemView: View) : MappingViewHolder<IndeterminateLoadingCircle>(itemView) {
    override fun bind(model: IndeterminateLoadingCircle) = Unit
  }

  fun register(mappingAdapter: MappingAdapter) {
    mappingAdapter.registerFactory(IndeterminateLoadingCircle::class.java, LayoutFactory({ ViewHolder(it) }, R.layout.indeterminate_loading_circle_pref))
  }
}
