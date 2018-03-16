package com.vsapp.petcare.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.vsapp.petcare.R;

/**
 * Created by venkat on 21/1/18.
 */

public class Utils {
    /*public static ProgressDialog showLoadingDialog(Context context, boolean cancelable) {
        Log.d("utils ","called");

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(cancelable);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }*/

    public static boolean hasActiveInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }
}
