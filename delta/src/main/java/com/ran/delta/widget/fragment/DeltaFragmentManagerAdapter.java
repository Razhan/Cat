package com.ran.delta.widget.fragment;

import android.support.v4.app.Fragment;

public interface DeltaFragmentManagerAdapter {

    Fragment onCreateFragment(int position);

    String getTag(int position);

    int getCount();
}
