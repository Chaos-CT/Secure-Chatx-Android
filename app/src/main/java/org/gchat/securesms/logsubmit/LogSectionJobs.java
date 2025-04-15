package org.gchat.securesms.logsubmit;

import android.content.Context;

import androidx.annotation.NonNull;

import org.gchat.securesms.dependencies.AppDependencies;

public class LogSectionJobs implements LogSection {

  @Override
  public @NonNull String getTitle() {
    return "JOBS";
  }

  @Override
  public @NonNull CharSequence getContent(@NonNull Context context) {
    return AppDependencies.getJobManager().getDebugInfo();
  }
}
