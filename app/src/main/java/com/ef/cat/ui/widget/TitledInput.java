package com.ef.cat.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ef.cat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitledInput extends LinearLayout {

    @BindView(R.id.titled_input_title)    TextView title;
    @BindView(R.id.titled_input_input)    EditText input;

    public TitledInput(Context context) {
        this(context, null);
    }

    public TitledInput(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitledInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        init();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.widget_titled_edittext, this);
        ButterKnife.bind(this, view);
    }

    public void init() {
//        title.setText(str);
//        input.setHint(hint);


        GradientDrawable titleBG = new GradientDrawable ();
        GradientDrawable inputBG = new GradientDrawable ();

        titleBG.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        inputBG.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        titleBG.setCornerRadii( new float[]{24, 24, 0, 0, 0, 0, 24, 24});
        inputBG.setCornerRadii( new float[]{0, 0, 24, 24, 24, 24, 0, 0});

        title.setBackground(titleBG);
        input.setBackground(inputBG);
    }


}
