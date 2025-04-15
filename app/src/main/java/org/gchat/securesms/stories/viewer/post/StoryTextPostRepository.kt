package org.gchat.securesms.stories.viewer.post

import android.graphics.Typeface
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.signal.core.util.Base64
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.database.model.MmsMessageRecord
import org.gchat.securesms.database.model.databaseprotos.StoryTextPost
import org.gchat.securesms.dependencies.AppDependencies
import org.gchat.securesms.fonts.TextFont
import org.gchat.securesms.fonts.TextToScript
import org.gchat.securesms.fonts.TypefaceCache

class StoryTextPostRepository {
  fun getRecord(recordId: Long): Single<MmsMessageRecord> {
    return Single.fromCallable {
      SignalDatabase.messages.getMessageRecord(recordId) as MmsMessageRecord
    }.subscribeOn(Schedulers.io())
  }

  fun getTypeface(recordId: Long): Single<Typeface> {
    return getRecord(recordId).flatMap {
      val model = StoryTextPost.ADAPTER.decode(Base64.decode(it.body))
      val textFont = TextFont.fromStyle(model.style)
      val script = TextToScript.guessScript(model.body)

      TypefaceCache.get(AppDependencies.application, textFont, script)
    }
  }
}
