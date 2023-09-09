package com.example.fragmenttest;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ExplainDialog extends Dialog {
    private Button ok;
    private View.OnClickListener ok_btn;

    public ExplainDialog(@NonNull Context context, View.OnClickListener ok_btn) {
        super(context);
        this.ok_btn = ok_btn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.dialog_to_explan);

        ok = findViewById(R.id.ok);
        ok.setOnClickListener(ok_btn);
    }
}
