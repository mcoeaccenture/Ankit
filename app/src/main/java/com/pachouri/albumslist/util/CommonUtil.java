package com.pachouri.albumslist.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Created by Ankit on 10/4/18.
 */
public class CommonUtil {

    /**
     * Method to show toast
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to check internet connection available or not
     *
     * @param context
     * @return
     */
    public static boolean isInternetConnected(Context context) {
        boolean isInternetConnected = false;
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            if (connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo()
                    .isAvailable() && connManager.getActiveNetworkInfo().isConnected()) {
                isInternetConnected = true;
            }
        } catch (Exception ex) {
            isInternetConnected = false;
        }
        return isInternetConnected;
    }
}
