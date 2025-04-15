package org.gchat.securesms.sharing.interstitial;

import org.gchat.securesms.R;
import org.gchat.securesms.util.adapter.mapping.MappingAdapter;
import org.gchat.securesms.util.viewholders.RecipientViewHolder;

class ShareInterstitialSelectionAdapter extends MappingAdapter {
  ShareInterstitialSelectionAdapter() {
    registerFactory(ShareInterstitialMappingModel.class, RecipientViewHolder.createFactory(R.layout.share_contact_selection_item, null));
  }
}
