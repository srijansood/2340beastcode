package com.beastcode;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Adway on 2/26/2015.
 */
@SuppressWarnings("DefaultFileTemplate")
class Message {
    /**
     * method that creates a toast message.
     * @param context this usually
     * @param message message to be displayed.
     */
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
