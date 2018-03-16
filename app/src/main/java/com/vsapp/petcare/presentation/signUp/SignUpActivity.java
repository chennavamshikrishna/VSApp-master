package com.vsapp.petcare.presentation.signUp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.gson.Gson;
import com.vsapp.petcare.R;
import com.vsapp.petcare.Utils.SharedPreferenceUtil;
import com.vsapp.petcare.Utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.*;

/**
 * Created by venkat on 21/1/18.
 */

public class SignUpActivity extends AppCompatActivity {

    private final int GOOGLE_SIGN_IN = 100;
    ProgressDialog progressDialog;
    String email;
    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById(R.id.dog_avatar_holder).bringToFront();
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getContext());
        findViewById(R.id.google_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGoogleSignIn();
            }
        });
    }

    private void startGoogleSignIn() {
        if (Utils.hasActiveInternetConnection(SignUpActivity.this)) {
            //progressDialog = Utils.showLoadingDialog(SignUpActivity.this, false);
            try {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder().setIsSmartLockEnabled(false).setLogo(AuthUI.NO_LOGO)
                                .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                .setAllowNewEmailAccounts(true)
                                .build(),
                        GOOGLE_SIGN_IN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getContext(), "No Internet\nPlease try again", Toast.LENGTH_SHORT).show();
        }
    }

    private Context getContext() {
        return SignUpActivity.this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GOOGLE_SIGN_IN) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    List<UserInfo> userInfos = new ArrayList<>();
                    userInfos.addAll(firebaseUser.getProviderData());
                    JSONObject jsonObject;
                    try {
                        Gson gson = new Gson();
                        jsonObject = new JSONObject(gson.toJson(userInfos.get(1)));
                        String emailJson = jsonObject.getString("zzlnj");
                        JSONObject emailObject = new JSONObject(emailJson);
                        email = emailObject.getString("email");
                        sharedPreferenceUtil.setEmail(email);
                        sharedPreferenceUtil.setName(firebaseUser.getDisplayName());
                        if (firebaseUser.getPhotoUrl() != null) {
                            if (sharedPreferenceUtil == null)
                                sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getContext());
                            sharedPreferenceUtil.setPic(firebaseUser.getPhotoUrl().toString());
                        }
                        gotoPhoneVerificationScreen();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Some error occured, Please try again", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Toast.makeText(getContext(), "Login Failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    private void gotoPhoneVerificationScreen() {
        Intent phoneNumberVerificationIntent = new Intent(getContext(), PhoneNumberVerification.class);
        startActivity(phoneNumberVerificationIntent);
        finish();
    }
}
