package org.gchat.securesms.conversation.ui.inlinequery

import org.gchat.securesms.R
import org.gchat.securesms.util.adapter.mapping.AnyMappingModel
import org.gchat.securesms.util.adapter.mapping.MappingAdapter

class InlineQueryAdapter(listener: (AnyMappingModel) -> Unit) : MappingAdapter() {
  init {
    registerFactory(InlineQueryEmojiResult.Model::class.java, { InlineQueryEmojiResult.ViewHolder(it, listener) }, R.layout.inline_query_emoji_result)
  }
}
