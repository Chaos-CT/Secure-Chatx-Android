package org.gchat.securesms.components.settings;

import androidx.annotation.NonNull;

import org.gchat.securesms.R;
import org.gchat.securesms.util.adapter.mapping.LayoutFactory;
import org.gchat.securesms.util.adapter.mapping.MappingAdapter;

/**
 * Reusable adapter for generic settings list.
 */
public class BaseSettingsAdapter extends MappingAdapter {

  public BaseSettingsAdapter() {
    registerFactory(SettingHeader.Item.class, SettingHeader.ViewHolder::new, R.layout.base_settings_header_item);
    registerFactory(SettingProgress.Item.class, SettingProgress.ViewHolder::new, R.layout.base_settings_progress_item);
  }

  public void configureSingleSelect(@NonNull SingleSelectSetting.SingleSelectSelectionChangedListener selectionChangedListener) {
    registerFactory(SingleSelectSetting.Item.class,
                    new LayoutFactory<>(v -> new SingleSelectSetting.ViewHolder(v, selectionChangedListener), R.layout.single_select_item));
  }
}
