package com.internship.droidz.talkin.ui.activity.login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.presenter.forgotPassword.ForgotPasswordPresenter;
import com.internship.droidz.talkin.presentation.view.forgotPassword.ForgotPasswordView;

/**
 * Created by st18r on 02.02.2017.
 *
 */

public class ForgotPasswordDialog extends DialogFragment implements ForgotPasswordView {

    ForgotPasswordPresenter forgotPasswordPresenter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // inflate the custom dialog layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.forgot_password_dialog, null);

        forgotPasswordPresenter = new ForgotPasswordPresenter(this);

        EditText etForgotPassword = (EditText) view.findViewById(R.id.forgotPasswordEditText);
        etForgotPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                forgotPasswordPresenter.setPositiveButtonState(etForgotPassword.getText().toString());
            }
        });

        // build the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setView(view)
                .setTitle(R.string.forgot_password_dialog_title)
                .setPositiveButton(R.string.dialog_positive_button, (dialog1, id) -> {

                })
                .setNegativeButton(R.string.dialog_negative_button, (dialog12, id) ->
                        ForgotPasswordDialog.this.getDialog().cancel());
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        // disable positive button by default
        forgotPasswordPresenter.disablePositiveButton();
    }

    @Override
    public void enablePositiveButton() {
        AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
    }

    @Override
    public void disablePositiveButton() {
        AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }
}
