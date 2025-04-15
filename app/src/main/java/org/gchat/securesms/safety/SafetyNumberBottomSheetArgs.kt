package org.gchat.securesms.safety

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.gchat.securesms.contacts.paged.ContactSearchKey
import org.gchat.securesms.database.model.MessageId
import org.gchat.securesms.recipients.RecipientId

/**
 * Fragment argument for `SafetyNumberBottomSheetFragment`
 */
@Parcelize
data class SafetyNumberBottomSheetArgs(
  val untrustedRecipients: List<RecipientId>,
  val destinations: List<ContactSearchKey.RecipientSearchKey>,
  val messageId: MessageId? = null
) : Parcelable
