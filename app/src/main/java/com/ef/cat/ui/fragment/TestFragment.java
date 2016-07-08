package com.ef.cat.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ef.cat.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ranzh on 7/6/2016.
 */
public class TestFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.textview)
    TextView textview;

    private String mParam1;

    public static TestFragment newInstance(String param1) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onCreateView", "onCreateView");
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        textview.setText(mParam1);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}