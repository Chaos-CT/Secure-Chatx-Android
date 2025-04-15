package org.gchat.securesms.database

internal interface ThreadIdDatabaseReference {
  fun remapThread(fromId: Long, toId: Long)
}
