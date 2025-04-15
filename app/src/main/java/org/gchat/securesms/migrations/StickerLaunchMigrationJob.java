package org.gchat.securesms.migrations;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.gchat.securesms.database.SignalDatabase;
import org.gchat.securesms.database.StickerTable;
import org.gchat.securesms.dependencies.AppDependencies;
import org.gchat.securesms.jobmanager.Job;
import org.gchat.securesms.jobmanager.JobManager;
import org.gchat.securesms.jobs.MultiDeviceStickerPackOperationJob;
import org.gchat.securesms.jobs.StickerPackDownloadJob;
import org.gchat.securesms.keyvalue.SignalStore;
import org.gchat.securesms.stickers.BlessedPacks;
import org.gchat.securesms.util.TextSecurePreferences;

public class StickerLaunchMigrationJob extends MigrationJob {

  public static final String KEY = "StickerLaunchMigrationJob";

  StickerLaunchMigrationJob() {
    this(new Parameters.Builder().build());
  }

  private StickerLaunchMigrationJob(@NonNull Parameters parameters) {
    super(parameters);
  }

  @Override
  public boolean isUiBlocking() {
    return false;
  }

  @Override
  public @NonNull String getFactoryKey() {
    return KEY;
  }

  @Override
  public void performMigration() {
    installPack(context, BlessedPacks.ZOZO);
    installPack(context, BlessedPacks.BANDIT);
  }

  @Override
  boolean shouldRetry(@NonNull Exception e) {
    return false;
  }

  private static void installPack(@NonNull Context context, @NonNull BlessedPacks.Pack pack) {
    JobManager   jobManager      = AppDependencies.getJobManager();
    StickerTable stickerDatabase = SignalDatabase.stickers();

    if (stickerDatabase.isPackAvailableAsReference(pack.getPackId())) {
      stickerDatabase.markPackAsInstalled(pack.getPackId(), false);
    }

    jobManager.add(StickerPackDownloadJob.forInstall(pack.getPackId(), pack.getPackKey(), false));

    if (SignalStore.account().hasLinkedDevices()) {
      jobManager.add(new MultiDeviceStickerPackOperationJob(pack.getPackId(), pack.getPackKey(), MultiDeviceStickerPackOperationJob.Type.INSTALL));
    }
  }

  public static class Factory implements Job.Factory<StickerLaunchMigrationJob> {
    @Override
    public @NonNull
    StickerLaunchMigrationJob create(@NonNull Parameters parameters, @Nullable byte[] serializedData) {
      return new StickerLaunchMigrationJob(parameters);
    }
  }
}
