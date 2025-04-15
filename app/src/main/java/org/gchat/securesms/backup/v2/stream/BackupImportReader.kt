/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.backup.v2.stream

import org.gchat.securesms.backup.v2.proto.BackupInfo
import org.gchat.securesms.backup.v2.proto.Frame

interface BackupImportReader : Iterator<Frame>, AutoCloseable {
  fun getHeader(): BackupInfo?
  fun getBytesRead(): Long
  fun getStreamLength(): Long
}
