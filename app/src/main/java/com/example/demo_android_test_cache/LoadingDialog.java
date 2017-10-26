package com.example.demo_android_test_cache;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Anson on 2017/10/26.
 */

public class LoadingDialog extends ProgressDialog{
    public LoadingDialog(Context context) {
        super(context);
        this.setMessage("Loading...");
    }
}
