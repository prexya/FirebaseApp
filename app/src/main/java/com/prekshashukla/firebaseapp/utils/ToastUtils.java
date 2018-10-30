package com.prekshashukla.firebaseapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by preksha.shukla on 7/3/2017.
 */

public class ToastUtils {
    public static void showToast(Context context, String message, boolean showLong) {

        int duration = showLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        Toast.makeText(context, message, duration).show();
    }
}
