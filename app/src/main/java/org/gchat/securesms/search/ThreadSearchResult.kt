package org.gchat.securesms.search

import org.gchat.securesms.database.model.ThreadRecord

data class ThreadSearchResult(val results: List<ThreadRecord>, val query: String)
