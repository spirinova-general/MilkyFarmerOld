package com.milky.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Neha on 12/2/2015.
 */
public class EnableEditableFields implements View.OnTouchListener {
    private InputMethodManager inputMethodManager;
    private Context _context;
    private EditText _editableField;
    private static boolean _enabled = false;

    public EnableEditableFields(final EditText view, final Context con, final InputMethodManager inptMngr) {
        this._context = con;
        this.inputMethodManager = inptMngr;
        this._editableField = view;
    }

    public void blockDefaultKeys() {
        inputMethodManager.hideSoftInputFromWindow(_editableField.getWindowToken(), 0);
    }

    private void showKeyBoard() {
        inputMethodManager.showSoftInput(_editableField, 0);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (_enabled) {
            showKeyBoard();
            return false;
        } else
            return true;

    }

    public static void setIsEnabled(boolean status) {
        _enabled = status;
    }

    public static boolean getIsEnabled() {
        return _enabled;
    }

}
