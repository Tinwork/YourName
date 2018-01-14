package com.yellowman.tinwork.yourname.UIKit.errors;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.yellowman.tinwork.yourname.R;

/**
 * ❤️ Happy new year️ !!! 🎊 L∞∞∞∞∞∞∞∞MM∞∞∞∞∞∞∞∞L 🎊 ❤️
 *
 * Created by Marc Intha-amnouay on 30/12/2017.
 * Created by Didier Youn on 30/12/2017.
 * Created by Abdel-Atif Mabrouck on 30/12/2017.
 * Created by Antoine Renault on 30/12/2017.
 */

public class UIErrorManager {
    // Public static field use to know which kind of strategy need to be use
    public final static int ALERT     = 1;
    public final static int SNACKBAR  = 2;
    public final static int COMPONENT = 3;
    public final static int TOAST     = 4;
    public final static int SNACKBAR_UNOBTRUSIVE = 5;

    // Public static field use to know which kind of action should the UIErrorManager should do..
    public static final int END   = 10;
    public static final int RETRY = 11;

    // Private field
    private Context ctx;
    private String errCode;
    private String errMessage;
    private int retryMode;

    /**
     * UI Error Manager::Constructor
     *
     */
    public UIErrorManager(Context context) {
        this.ctx = context;
        this.retryMode = END;
    }

    /**
     * Set Error
     *      Set the error which will be used to be display later
     *
     * @param code String
     * @param message String
     * @return this
     */
    public UIErrorManager setError(String code, String message) {
        this.errCode    = code;
        this.errMessage = message;

        return this;
    }

    /**
     * Set Opts Mode
     *
     * @return UIErrorManager
     */
    public UIErrorManager setOptsMode(int code) {
        this.retryMode = code;

        return this;
    }

    /**
     * Set Error Strategy
     *
     * @param strategyCode int
     */
    public void setErrorStrategy(int strategyCode) {
        switch (strategyCode) {
            case ALERT:
                alertBox();
                break;
            case SNACKBAR:
                snackBar();
                break;
            case COMPONENT:
                break;
            case TOAST:
                toast();
                break;
            case SNACKBAR_UNOBTRUSIVE:
                break;
            default:
                toast();
        }
    }

    /**
     * Alert Box
     *
     */
    private void alertBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(errMessage);

        if (retryMode == RETRY) {
            builder.setPositiveButton(R.string.retry_mode, (dialog, id) -> {
                        Log.d("Debug", "CLICK ON RETRY !!!!");
                    })
                    .setNegativeButton(R.string.cancel_mode, (dialog, id) -> {
                        Log.d("Debug", "CLICK ON DENY");
                    });
        } else {
            builder.setPositiveButton(R.string.end_mode, ((dialog, which) -> {
                Log.d("Debug", "User has cancal the action");
            }));
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Snack Bar
     *      Display a snack bar
     */
    private void snackBar() {
        Snackbar snackbar = Snackbar.make(
                    ((Activity) ctx).findViewById(R.id.coordinator_layout),
                    errMessage,
                    Snackbar.LENGTH_SHORT
                );

        if (retryMode == RETRY) {
            snackbar.setAction(R.string.retry_mode, listener -> {
                Log.d("Debug", "Click on retry of the snackbar");
            });
        }

        snackbar.show();
    }

    /**
     * Toast
     */
    private void toast() {
        Toast message = Toast.makeText(ctx, errMessage, Toast.LENGTH_SHORT);
        message.show();
    }
}
