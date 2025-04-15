package org.gchat.securesms.stories.viewer

import android.net.Uri
import org.gchat.securesms.blurhash.BlurHash
import org.gchat.securesms.database.model.MmsMessageRecord
import org.gchat.securesms.recipients.RecipientId
import org.gchat.securesms.stories.StoryTextPostModel

data class StoryViewerState(
  val pages: List<RecipientId> = emptyList(),
  val previousPage: Int = -1,
  val page: Int = -1,
  val crossfadeSource: CrossfadeSource,
  val crossfadeTarget: CrossfadeTarget? = null,
  val skipCrossfade: Boolean = false,
  val noPosts: Boolean = false
) {
  sealed class CrossfadeSource {
    object None : CrossfadeSource()
    class ImageUri(val imageUri: Uri, val imageBlur: BlurHash?) : CrossfadeSource()
    class TextModel(val storyTextPostModel: StoryTextPostModel) : CrossfadeSource()
  }

  sealed class CrossfadeTarget {
    object None : CrossfadeTarget()
    data class Record(val messageRecord: MmsMessageRecord) : CrossfadeTarget()
  }
}
