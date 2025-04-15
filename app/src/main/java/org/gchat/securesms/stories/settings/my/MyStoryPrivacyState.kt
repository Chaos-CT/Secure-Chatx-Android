package org.gchat.securesms.stories.settings.my

import org.gchat.securesms.database.model.DistributionListPrivacyMode

data class MyStoryPrivacyState(val privacyMode: DistributionListPrivacyMode? = null, val connectionCount: Int = 0)
