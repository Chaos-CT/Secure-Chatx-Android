package org.gchat.securesms.safety

import org.gchat.securesms.database.model.DistributionListId
import org.gchat.securesms.recipients.Recipient

sealed class SafetyNumberBucket {
  data class DistributionListBucket(val distributionListId: DistributionListId, val name: String) : SafetyNumberBucket()
  data class GroupBucket(val recipient: Recipient) : SafetyNumberBucket()
  object ContactsBucket : SafetyNumberBucket()
}
