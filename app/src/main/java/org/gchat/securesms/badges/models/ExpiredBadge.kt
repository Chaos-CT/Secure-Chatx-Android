package org.gchat.securesms.badges.models

import android.view.View
import org.gchat.securesms.R
import org.gchat.securesms.badges.BadgeImageView
import org.gchat.securesms.components.settings.PreferenceModel
import org.gchat.securesms.util.adapter.mapping.LayoutFactory
import org.gchat.securesms.util.adapter.mapping.MappingAdapter
import org.gchat.securesms.util.adapter.mapping.MappingViewHolder

object ExpiredBadge {

  class Model(val badge: Badge) : PreferenceModel<Model>() {
    override fun areItemsTheSame(newItem: Model): Boolean {
      return newItem.badge.id == badge.id
    }

    override fun areContentsTheSame(newItem: Model): Boolean {
      return super.areContentsTheSame(newItem) && newItem.badge == badge
    }
  }

  class ViewHolder(itemView: View) : MappingViewHolder<Model>(itemView) {

    private val badge: BadgeImageView = itemView.findViewById(R.id.expired_badge)

    override fun bind(model: Model) {
      badge.setBadge(model.badge)
    }
  }

  fun register(adapter: MappingAdapter) {
    adapter.registerFactory(Model::class.java, LayoutFactory({ ViewHolder(it) }, R.layout.expired_badge_preference))
  }
}
