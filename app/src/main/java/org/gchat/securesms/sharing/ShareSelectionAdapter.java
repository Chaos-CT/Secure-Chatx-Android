package org.gchat.securesms.sharing;

import org.gchat.securesms.R;
import org.gchat.securesms.util.adapter.mapping.MappingAdapter;

public class ShareSelectionAdapter extends MappingAdapter {
  public ShareSelectionAdapter() {
    registerFactory(ShareSelectionMappingModel.class,
                    ShareSelectionViewHolder.createFactory(R.layout.share_contact_selection_item));
  }
}
