package org.gchat.securesms.conversation.colors.ui

import androidx.lifecycle.LiveData
import org.signal.core.util.concurrent.SignalExecutors
import org.gchat.securesms.conversation.colors.ChatColors
import org.gchat.securesms.conversation.colors.ChatColorsPalette
import org.gchat.securesms.database.ChatColorsTable
import org.gchat.securesms.database.DatabaseObserver
import org.gchat.securesms.database.SignalDatabase
import org.gchat.securesms.dependencies.AppDependencies
import org.gchat.securesms.util.concurrent.SerialMonoLifoExecutor
import java.util.concurrent.Executor

class ChatColorsOptionsLiveData : LiveData<List<ChatColors>>() {
  private val chatColorsTable: ChatColorsTable = SignalDatabase.chatColors
  private val observer: DatabaseObserver.Observer = DatabaseObserver.Observer { refreshChatColors() }
  private val executor: Executor = SerialMonoLifoExecutor(SignalExecutors.BOUNDED)

  override fun onActive() {
    refreshChatColors()
    AppDependencies.databaseObserver.registerChatColorsObserver(observer)
  }

  override fun onInactive() {
    AppDependencies.databaseObserver.unregisterObserver(observer)
  }

  private fun refreshChatColors() {
    executor.execute {
      val options = mutableListOf<ChatColors>().apply {
        addAll(ChatColorsPalette.Bubbles.all)
        addAll(chatColorsTable.getSavedChatColors())
      }

      postValue(options)
    }
  }
}
