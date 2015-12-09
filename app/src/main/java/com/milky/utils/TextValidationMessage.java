package com.milky.utils;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

import com.milky.R;

/**
 * Created by Neha on 12/2/2015.
 */
public class TextValidationMessage implements TextWatcher {
    private TextInputLayout _textInputLayout;
    private Context _context;
    private boolean _isPhone = false;
    private static boolean isValid = false;

    public TextValidationMessage(final TextInputLayout txtInputlt, final Context con, final boolean phone) {
        this._textInputLayout = txtInputlt;
        this._context = con;
        this._isPhone = phone;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() > 0) {
            if (_isPhone && !isValidMobile(s.toString())) {
                _textInputLayout.setError(_context.getResources().getString(R.string.invalid_phone_no));
                isValid = false;
            } else {
                _textInputLayout.setError(null);
                isValid = true;
            }
        } else {
            _textInputLayout.setError(_context.getResources().getString(R.string.field_cant_empty));
            isValid = false;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            if (_isPhone && !isValidMobile(s.toString())) {
                _textInputLayout.setError(_context.getResources().getString(R.string.invalid_phone_no));
                isValid = false;
            } else {
                _textInputLayout.setError(null);
                isValid = true;
            }
        } else {
            _textInputLayout.setError(_context.getResources().getString(R.string.field_cant_empty));
            isValid = false;
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() > 0) {
            if (_isPhone && !isValidMobile(editable.toString())) {
                _textInputLayout.setError(_context.getResources().getString(R.string.invalid_phone_no));
                isValid = false;
            } else {
                _textInputLayout.setError(null);
                isValid = true;
            }
        } else {
            _textInputLayout.setError(_context.getResources().getString(R.string.field_cant_empty));
            isValid = false;
        }

    }

    public boolean isValidMobile(String text) {

        if (android.util.Patterns.PHONE.matcher(text).matches()) {
            return true;
        }
        return false;
    }

    public static boolean getIfValid() {
        return isValid;
    }

}
