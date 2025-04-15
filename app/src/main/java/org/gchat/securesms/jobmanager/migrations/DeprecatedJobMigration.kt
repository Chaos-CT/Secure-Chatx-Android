/*
 * Copyright 2025 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.jobmanager.migrations

import org.gchat.securesms.jobmanager.JobMigration

/**
 * Used as a replacement for another JobMigration that is no longer necessary.
 */
class DeprecatedJobMigration(version: Int) : JobMigration(version) {
  override fun migrate(jobData: JobData): JobData = jobData
}
