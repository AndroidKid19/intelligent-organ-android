/*
 * Copyright 2018 AndroidKid19
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yway.scomponent.commonres.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.yway.scomponent.commonres.R;

/**
 * ================================================
 * Created by AndroidKid19 on 16/04/2018 17:01
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19Coding">Follow me</a>
 * ================================================
 */
public class ProgresDialog extends Dialog {

    private static ProgresDialog alertDialog = null;

    public static ProgresDialog getInstance(Context context){
        if (alertDialog==null){
            synchronized (ProgresDialog.class) {
                if (alertDialog == null) {
                    alertDialog = new ProgresDialog(context);
                }
            }
        }
        return alertDialog;
    }

    public ProgresDialog(@NonNull Context context) {
        super(context, R.style.public_dialog_progress);
        setContentView(R.layout.public_dialog_porgress);
        setCanceledOnTouchOutside(false);
    }

    public void dismissDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

}
