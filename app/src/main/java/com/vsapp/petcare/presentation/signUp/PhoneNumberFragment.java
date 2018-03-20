package com.vsapp.petcare.presentation.signUp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.vsapp.petcare.R;
import com.vsapp.petcare.Utils.ActivityUtils;
import com.vsapp.petcare.Utils.Utils;



public class PhoneNumberFragment extends Fragment {

    MaterialEditText materialEditText;
    static PhoneNumberFragment phoneNumberFragment;
    private static final String TAG = "PhoneNumberFragment";

    public static PhoneNumberFragment getInstance() {
        if (phoneNumberFragment == null) {
            phoneNumberFragment = new PhoneNumberFragment();
        }
        return phoneNumberFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone_number, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.dog_avatar_holder).bringToFront();
        materialEditText = view.findViewById(R.id.phone_number_met);
        view.findViewById(R.id.phone_number_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePhoneNumber();
            }
        });

        materialEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    validatePhoneNumber();
                    return true;
                }
                return false;
            }
        });
    }

    private void validatePhoneNumber() {
        if (getView() != null) {
            String phoneNumber = materialEditText.getText().toString();
            if (phoneNumber.length() == 10 && Utils.hasActiveInternetConnection(getContext())) {
                OTPFragment otpFragment = OTPFragment.getInstance();
                Bundle otpBundle = new Bundle();
                Log.i(TAG, "setUpOTPFragment: phone number " + phoneNumber);
                otpBundle.putString("phoneNumber", phoneNumber);
                otpFragment.setArguments(otpBundle);
                if (getFragmentManager() != null) {
                    ActivityUtils.replaceFragmentInActivity(getFragmentManager(), otpFragment, R.id.phone_number_fragment_holder);
                }
            } else {
                Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
