package com.softlaboratory.chatapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.softlaboratory.chatapp.MainActivity;
import com.softlaboratory.chatapp.R;
import com.softlaboratory.chatapp.databinding.ActivityLoginBinding;
import com.softlaboratory.chatapp.utility.Constant;
import com.softlaboratory.chatapp.utility.PreferenceManager;

public class LoginActivity extends AppCompatActivity {

    //BINDING
    private ActivityLoginBinding binding;

    //SHAREDPREFS MANAGER
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //BINDING
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //SHAREDPREF MANAGER
        preferenceManager = new PreferenceManager(LoginActivity.this);

        //ALREADY LOGIN
        if (preferenceManager.getBoolean(Constant.KEY_LOGIN_STATE)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        //ON CLICK
        setOnClickListener();
    }

    //ONCLICK LISTENER
    private void setOnClickListener() {
        //BTN REGISTER ACCOUNT
        binding.actLoginRegisterAccount.setOnClickListener((v) -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        //BUTTON LOGIN
        binding.actLoginBtnLogin.setOnClickListener(v -> {
            login();
        });
    }

    //LOGIN
    private void login() {
        //PROGRESS DIALOG
        ProgressDialog progressDialog = showProgressDialog();
        progressDialog.show();

        //LOGIN PROCESS
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(Constant.KEY_COLLECTION)
                .whereEqualTo(Constant.KEY_EMAIL, binding.actLoginEmail.getText().toString())
                .whereEqualTo(Constant.KEY_PASSWORD, binding.actLoginPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                        //GET DOCUMENT
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        //PUT TO SHAREDPREF
                        preferenceManager.putBoolean(Constant.KEY_LOGIN_STATE, true);
                        preferenceManager.putString(Constant.KEY_ACCOUNT_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constant.KEY_FULLNAME, documentSnapshot.getString(Constant.KEY_FULLNAME));

                        //PROGRESS DIALOG DISMISS
                        progressDialog.dismiss();

                        //CHANGE ACTIVITY
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        progressDialog.dismiss();
                        showMessageDialog("Login Failed","Please try again...");
                    }

                });
    }

    //SHOW MESSAGE DIALOG
    private void showMessageDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", (dialog, which) -> {

        });
        alertDialog.show();

    }

    //SHOW PROGRESS DIALOG
    private ProgressDialog showProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        return progressDialog;
    }
}