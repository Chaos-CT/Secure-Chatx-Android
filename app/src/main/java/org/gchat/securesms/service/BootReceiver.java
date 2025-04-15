package org.gchat.securesms.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.gchat.securesms.dependencies.AppDependencies;
import org.gchat.securesms.jobs.MessageFetchJob;

public class BootReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    AppDependencies.getJobManager().add(new MessageFetchJob());
  }
}
