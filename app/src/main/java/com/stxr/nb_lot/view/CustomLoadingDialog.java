package com.stxr.nb_lot.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.stxr.nb_lot.R;

/**
 * Created by stxr on 2018/4/26.
 */

public class CustomLoadingDialog extends Dialog {
    private String mText;
    public CustomLoadingDialog(@NonNull Context context, String text) {
        super(context, R.style.Theme_Dialog);
        setContentView(R.layout.dialog_loading);
        mText = text;
        setCancelable(false);
    }
    public void setText(String text) {
        this.mText = text;
    }

    public void show(String text) {
        this.mText = text;
        TextView tv_loading_dialog =findViewById(R.id.tv_loading_dialog);
        tv_loading_dialog.setText(mText);
        super.show();
    }
}
