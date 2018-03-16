package com.vsapp.petcare.presentation.signUp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vsapp.petcare.R;
import com.vsapp.petcare.Utils.ActivityUtils;

/**
 * Created by venkat on 21/1/18.
 */

public class PhoneNumberVerification extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);
        setUpFragmentView();
    }

    private void setUpFragmentView() {
        PhoneNumberFragment phoneNumberFragment = (PhoneNumberFragment) getSupportFragmentManager().findFragmentById(R.id.phone_number_fragment_holder);
        if (phoneNumberFragment == null) {
            phoneNumberFragment = PhoneNumberFragment.getInstance();
            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), phoneNumberFragment, R.id.phone_number_fragment_holder);
        }
    }
}
