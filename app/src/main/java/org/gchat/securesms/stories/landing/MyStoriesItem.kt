package org.gchat.securesms.stories.landing

import android.view.View
import org.gchat.securesms.R
import org.gchat.securesms.avatar.view.AvatarView
import org.gchat.securesms.components.settings.PreferenceModel
import org.gchat.securesms.recipients.Recipient
import org.gchat.securesms.util.adapter.mapping.LayoutFactory
import org.gchat.securesms.util.adapter.mapping.MappingAdapter
import org.gchat.securesms.util.adapter.mapping.MappingViewHolder

/**
 * Item displayed on an empty Stories landing page allowing the user to add a new story.
 */
object MyStoriesItem {

  fun register(mappingAdapter: MappingAdapter) {
    mappingAdapter.registerFactory(Model::class.java, LayoutFactory(::ViewHolder, R.layout.stories_landing_item_my_stories))
  }

  class Model(
    val onClick: () -> Unit
  ) : PreferenceModel<Model>() {
    override fun areItemsTheSame(newItem: Model): Boolean = true
  }

  private class ViewHolder(itemView: View) : MappingViewHolder<Model>(itemView) {

    private val avatarView: AvatarView = itemView.findViewById(R.id.avatar)

    override fun bind(model: Model) {
      itemView.setOnClickListener { model.onClick() }
      avatarView.displayProfileAvatar(Recipient.self())
    }
  }
}
