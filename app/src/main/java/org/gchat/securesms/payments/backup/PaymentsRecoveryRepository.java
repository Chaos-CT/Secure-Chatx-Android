package org.gchat.securesms.payments.backup;

import androidx.annotation.NonNull;

import org.gchat.securesms.keyvalue.SignalStore;
import org.gchat.securesms.payments.Mnemonic;

public final class PaymentsRecoveryRepository {
  public @NonNull Mnemonic getMnemonic() {
    return SignalStore.payments().getPaymentsMnemonic();
  }
}
