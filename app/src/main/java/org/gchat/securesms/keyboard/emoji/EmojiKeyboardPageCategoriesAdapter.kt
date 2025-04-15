package org.gchat.securesms.keyboard.emoji

import org.gchat.securesms.R
import org.gchat.securesms.keyboard.KeyboardPageCategoryIconViewHolder
import org.gchat.securesms.util.adapter.mapping.LayoutFactory
import org.gchat.securesms.util.adapter.mapping.MappingAdapter
import java.util.function.Consumer

class EmojiKeyboardPageCategoriesAdapter(private val onPageSelected: Consumer<String>) : MappingAdapter() {
  init {
    registerFactory(RecentsMappingModel::class.java, LayoutFactory({ v -> KeyboardPageCategoryIconViewHolder(v, onPageSelected) }, R.layout.keyboard_pager_category_icon))
    registerFactory(EmojiCategoryMappingModel::class.java, LayoutFactory({ v -> KeyboardPageCategoryIconViewHolder(v, onPageSelected) }, R.layout.keyboard_pager_category_icon))
  }
}
