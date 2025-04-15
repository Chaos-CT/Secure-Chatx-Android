package org.gchat.securesms.conversation.v2.groups

import org.gchat.securesms.database.GroupTable

/**
 * @param groupTableMemberLevel Self membership level
 * @param isAnnouncementGroup   Whether the group is an announcement group.
 */
data class ConversationGroupMemberLevel(
  val groupTableMemberLevel: GroupTable.MemberLevel,
  val isAnnouncementGroup: Boolean
)
