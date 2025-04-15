package org.gchat.securesms.deeplinks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import org.gchat.securesms.MainActivity;
import org.gchat.securesms.PassphraseRequiredActivity;

public class DeepLinkEntryActivity extends PassphraseRequiredActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState, boolean ready) {
    Intent intent = MainActivity.clearTop(this);
    Uri    data   = getIntent().getData();
    intent.setData(data);
    startActivity(intent);
  }
}
