package com.example.trailermate.Utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrailerUtils {
    Context context;

    public static void showDialog(ProgressDialog progressDialog) {
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public static void exitDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    public static boolean isValidEmail(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public void dismissDialog(Dialog dialog) {
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.dismiss();
    }


}
