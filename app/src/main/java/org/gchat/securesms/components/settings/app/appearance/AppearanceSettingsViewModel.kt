package org.gchat.securesms.components.settings.app.appearance

import android.app.Activity
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Flowable
import org.gchat.securesms.jobs.EmojiSearchIndexDownloadJob
import org.gchat.securesms.keyvalue.SettingsValues.Theme
import org.gchat.securesms.keyvalue.SignalStore
import org.gchat.securesms.util.SplashScreenUtil
import org.gchat.securesms.util.rx.RxStore

class AppearanceSettingsViewModel : ViewModel() {
  private val store = RxStore(getState())
  val state: Flowable<AppearanceSettingsState> = store.stateFlowable

  override fun onCleared() {
    super.onCleared()
    store.dispose()
  }

  fun refreshState() {
    store.update { getState() }
  }

  fun setTheme(activity: Activity?, theme: Theme) {
    store.update { it.copy(theme = theme) }
    SignalStore.settings.theme = theme
    SplashScreenUtil.setSplashScreenThemeIfNecessary(activity, theme)
  }

  fun setLanguage(language: String) {
    store.update { it.copy(language = language) }
    SignalStore.settings.language = language
    EmojiSearchIndexDownloadJob.scheduleImmediately()
  }

  fun setMessageFontSize(size: Int) {
    store.update { it.copy(messageFontSize = size) }
    SignalStore.settings.messageFontSize = size
  }

  private fun getState(): AppearanceSettingsState {
    return AppearanceSettingsState(
      SignalStore.settings.theme,
      SignalStore.settings.messageFontSize,
      SignalStore.settings.language,
      SignalStore.settings.useCompactNavigationBar
    )
  }
}
