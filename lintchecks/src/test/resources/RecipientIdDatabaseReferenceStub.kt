package org.gchat.securesms.database

internal interface RecipientIdDatabaseReference {
  fun remapRecipient(fromId: RecipientId?, toId: RecipientId?)
}
