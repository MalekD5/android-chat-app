package com.example.chatapp;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;

import java.util.OptionalInt;

public class Utils {

    private Utils() {}

    public static void openDialog(Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("Close", null);

        builder.show();
    }

    public static OptionalInt parseNumber(String rawInput) {
        try {
             int num = Integer.parseInt(rawInput);
             return OptionalInt.of(num);
        } catch (NumberFormatException e) {
            return OptionalInt.empty();
        }
    }

    public static void clearInputs(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            for (int i = 0; i < group.getChildCount(); i++) {
                clearInput(group.getChildAt(i));
            }
        }
    }

    public static void clearInput(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setText("");
        } else if (view instanceof CheckBox) {
            ((CheckBox) view).setChecked(false);
        } else if (view instanceof RadioButton) {
            ((RadioButton) view).setChecked(false);
        } else if (view instanceof Switch) {
            ((Switch) view).setChecked(false);
        } else if (view instanceof ToggleButton) {
            ((ToggleButton) view).setChecked(false);
        }
    }

}
