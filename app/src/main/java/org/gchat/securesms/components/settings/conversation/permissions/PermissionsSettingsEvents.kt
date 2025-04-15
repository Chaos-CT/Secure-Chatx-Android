package org.gchat.securesms.components.settings.conversation.permissions

import org.gchat.securesms.groups.ui.GroupChangeFailureReason

sealed class PermissionsSettingsEvents {
  class GroupChangeError(val reason: GroupChangeFailureReason) : PermissionsSettingsEvents()
}
