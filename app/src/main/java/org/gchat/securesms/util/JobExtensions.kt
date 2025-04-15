/*
 * Copyright 2024 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.util

import org.gchat.securesms.dependencies.AppDependencies
import org.gchat.securesms.jobmanager.Job
import org.gchat.securesms.jobmanager.JobManager

/** Starts a new chain with this job. */
fun Job.asChain(): JobManager.Chain {
  return AppDependencies.jobManager.startChain(this)
}
