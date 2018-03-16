package com.vsapp.petcare.presentation.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.vsapp.petcare.R;
import com.vsapp.petcare.Utils.SharedPreferenceUtil;
import com.vsapp.petcare.presentation.home.MainActivity;
import com.vsapp.petcare.presentation.signUp.PhoneNumberVerification;
import com.vsapp.petcare.presentation.signUp.SignUpActivity;

public class SplashActivity extends AppCompatActivity {

    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseApp.initializeApp(getContext());
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSignUp();
    }

    private void checkSignUp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!"null".equalsIgnoreCase(sharedPreferenceUtil.getEmail())) {
                    if ("null".equalsIgnoreCase(sharedPreferenceUtil.getPhoneNumber())) {
                        gotoPhoneVerificationScreen();
                    } else {
                        if (!sharedPreferenceUtil.getOnBoardingDone()) {
                            gotoOnBoardingScreen();
                        } else {
                            gotoMainScreen();
                        }
                    }
                } else {
                    gotoSignUpScreen();
                }
            }
        }, 800);
    }

    private void gotoOnBoardingScreen() {
        Intent signUpScreenIntent = new Intent(getContext(), OnBoardingActivity.class);
        signUpScreenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(signUpScreenIntent);
    }

    private void gotoPhoneVerificationScreen() {
        Intent phoneNumberVerificationIntent = new Intent(getContext(), PhoneNumberVerification.class);
        startActivity(phoneNumberVerificationIntent);
        finish();
    }

    private void gotoSignUpScreen() {
        Intent intent = new Intent(getContext(), SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    private void gotoMainScreen() {
        Intent signUpScreenIntent = new Intent(getContext(), MainActivity.class);
        signUpScreenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(signUpScreenIntent);
    }

    private Context getContext() {
        return SplashActivity.this;
    }
}