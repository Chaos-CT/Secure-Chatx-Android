/*
 * Copyright 2024 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.registration.util;

import org.signal.core.util.logging.Log;
import org.gchat.securesms.dependencies.AppDependencies;
import org.gchat.securesms.jobs.DirectoryRefreshJob;
import org.gchat.securesms.jobs.EmojiSearchIndexDownloadJob;
import org.gchat.securesms.jobs.RefreshAttributesJob;
import org.gchat.securesms.jobs.StorageSyncJob;
import org.gchat.securesms.keyvalue.PhoneNumberPrivacyValues.PhoneNumberDiscoverabilityMode;
import org.gchat.securesms.keyvalue.RestoreDecisionStateUtil;
import org.gchat.securesms.keyvalue.SignalStore;
import org.gchat.securesms.recipients.Recipient;
import org.gchat.securesms.util.RemoteConfig;

public final class RegistrationUtil {

  private static final String TAG = Log.tag(RegistrationUtil.class);

  private RegistrationUtil() {}

  /**
   * There's several events where a registration may or may not be considered complete based on what
   * path a user has taken. This will only truly mark registration as complete if all of the
   * requirements are met.
   */
  public static void maybeMarkRegistrationComplete() {
    if (!SignalStore.registration().isRegistrationComplete() &&
        SignalStore.account().isRegistered() &&
        !Recipient.self().getProfileName().isEmpty() &&
        (SignalStore.svr().hasOptedInWithAccess() || SignalStore.svr().hasOptedOut()) &&
        (!RemoteConfig.restoreAfterRegistration() || RestoreDecisionStateUtil.isTerminal(SignalStore.registration().getRestoreDecisionState())))
    {
      Log.i(TAG, "Marking registration completed.", new Throwable());
      SignalStore.registration().markRegistrationComplete();
      SignalStore.registration().setLocalRegistrationMetadata(null);
      SignalStore.registration().setRestoreMethodToken(null);

      if (SignalStore.phoneNumberPrivacy().getPhoneNumberDiscoverabilityMode() == PhoneNumberDiscoverabilityMode.UNDECIDED) {
        Log.w(TAG, "Phone number discoverability mode is still UNDECIDED. Setting to DISCOVERABLE.");
        SignalStore.phoneNumberPrivacy().setPhoneNumberDiscoverabilityMode(PhoneNumberDiscoverabilityMode.DISCOVERABLE);
      }

      AppDependencies.getJobManager().startChain(new RefreshAttributesJob())
                     .then(new StorageSyncJob())
                     .then(new DirectoryRefreshJob(false))
                     .enqueue();

      SignalStore.emoji().clearSearchIndexMetadata();
      EmojiSearchIndexDownloadJob.scheduleImmediately();

    } else if (!SignalStore.registration().isRegistrationComplete()) {
      Log.i(TAG, "Registration is not yet complete.", new Throwable());
    }
  }
}
