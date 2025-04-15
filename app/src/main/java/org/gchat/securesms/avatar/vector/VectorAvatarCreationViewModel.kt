package org.gchat.securesms.avatar.vector

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.gchat.securesms.avatar.Avatar
import org.gchat.securesms.avatar.Avatars
import org.gchat.securesms.util.livedata.Store

class VectorAvatarCreationViewModel(initialAvatar: Avatar.Vector) : ViewModel() {

  private val store = Store(VectorAvatarCreationState(initialAvatar))

  val state: LiveData<VectorAvatarCreationState> = store.stateLiveData

  fun setColor(colorPair: Avatars.ColorPair) {
    store.update { it.copy(currentAvatar = it.currentAvatar.copy(color = colorPair)) }
  }

  fun getCurrentAvatar() = store.state.currentAvatar

  class Factory(private val initialAvatar: Avatar.Vector) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return requireNotNull(modelClass.cast(VectorAvatarCreationViewModel(initialAvatar)))
    }
  }
}
