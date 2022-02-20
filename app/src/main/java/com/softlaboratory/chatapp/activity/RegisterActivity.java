package com.softlaboratory.chatapp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.softlaboratory.chatapp.MainActivity;
import com.softlaboratory.chatapp.databinding.ActivityRegisterBinding;
import com.softlaboratory.chatapp.utility.Constant;
import com.softlaboratory.chatapp.utility.PreferenceManager;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    //BINDING
    private ActivityRegisterBinding binding;

    //SHARED PREFERENCE MANAGER
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //BINDING
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //SHAREDPREF MANAGER
        preferenceManager = new PreferenceManager(RegisterActivity.this);

        //ON CLICK LISTENER
        onCLickListener();

    }

    //ON CLICK LISTENER
    private void onCLickListener() {

        //HAVE ACCOUNT
        binding.actRegisterHaveAccount.setOnClickListener(v -> {
            onBackPressed();
        });

        //BUTTON REGISTER
        binding.actRegisterBtnRegister.setOnClickListener(v -> {

            //CHECK VALIDITY
            if (isValid()) {
                registerAccount();
            }

        });


    }

    //REGISTER
    private void registerAccount() {

        //PROGRESS DIALOG
        ProgressDialog progressDialog = showProgressDialog();

        //REGISTERING ACCOUNT
        //SHOW PROGRESS DIALOG0
        progressDialog.show();

        //FIREBASE STORAGE
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        //ACCOUNT DATA
        HashMap<String, Object> account = new HashMap<>();
        account.put(Constant.KEY_FULLNAME,binding.actRegisterFullname.getText().toString());
        account.put(Constant.KEY_EMAIL,binding.actRegisterEmail.getText().toString());
        account.put(Constant.KEY_PASSWORD,binding.actRegisterPassword.getText().toString());

        //ADD TO FIRESTORE
        firestore.collection(Constant.KEY_COLLECTION)
                .add(account)
                .addOnSuccessListener(documentReference -> {

                    //CHANGE LOGIN STATE
                    preferenceManager.putBoolean(Constant.KEY_LOGIN_STATE, true);
                    preferenceManager.putString(Constant.KEY_ACCOUNT_ID, documentReference.getId());
                    preferenceManager.putString(Constant.KEY_FULLNAME, binding.actRegisterFullname.getText().toString());

                    //DISMISS PROGRESS DIALOG
                    progressDialog.dismiss();

                    //CHANGE ACTIVITY
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                })
                .addOnFailureListener(e -> {
                   progressDialog.dismiss();
                   showMessageDialog("Register Failed",e.getMessage());
                });


    }

    //SHOW PROGRESS DIALOG
    private ProgressDialog showProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        return progressDialog;
    }

    //SHOW MESSAGE DIALOG
    private void showMessageDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", (dialog, which) -> {

        });
        alertDialog.show();

    }

    //VALIDATOR
    private Boolean isValid() {
        Boolean isValid = false;

        if (binding.actRegisterEmail.getText().toString().trim().isEmpty()) {
            showMessageDialog("Failed","Please enter your Email!");
        }else if (binding.actRegisterFullname.getText().toString().trim().isEmpty()){
            showMessageDialog("Failed","Please Enter your Full Name!");
        }else if (binding.actRegisterPassword.getText().toString().trim().isEmpty()) {
            showMessageDialog("Failed","Please enter your Password!");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(binding.actRegisterEmail.getText().toString()).matches()) {
            showMessageDialog("Failed","Enter valid Email format!");
        }else if (!binding.actRegisterConfirmPassword.getText().toString().equals(binding.actRegisterPassword.getText().toString())) {
            showMessageDialog("Failed","Check your password confirmation!");
        }else {
            isValid = true;
        }

        return isValid;
    }
}