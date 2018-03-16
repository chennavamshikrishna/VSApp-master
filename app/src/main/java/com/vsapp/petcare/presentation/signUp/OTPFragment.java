package com.vsapp.petcare.presentation.signUp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.vsapp.petcare.R;
import com.vsapp.petcare.Utils.OTP;
import com.vsapp.petcare.Utils.SharedPreferenceUtil;
import com.vsapp.petcare.presentation.home.MainActivity;
import com.vsapp.petcare.presentation.splash.OnBoardingActivity;

import java.util.concurrent.TimeUnit;

/**
 * Created by venkat on 23/1/18.
 */

public class OTPFragment extends Fragment {

    static OTPFragment otpFragment;
    FirebaseDatabase firebaseDatabase;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    SharedPreferenceUtil sharedPreferenceUtil;
    private String phoneNumber;
    CountDownTimer countDownTimer;
    private PhoneAuthProvider.ForceResendingToken token;
    private OTP otp;
    TextView resendText, timerHolder, otpStatus, codeSentTo;
    private static final String TAG = "OTPFragment";

    public static OTPFragment getInstance() {
        if (otpFragment == null) {
            otpFragment = new OTPFragment();
        }
        return otpFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            phoneNumber = "+91" + getArguments().getString("phoneNumber");
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getContext());

        return inflater.inflate(R.layout.fragment_otp, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        timerHolder = view.findViewById(R.id.resend_timer);
        resendText = view.findViewById(R.id.otp_resend);
        otpStatus = view.findViewById(R.id.otp_status);
        codeSentTo = view.findViewById(R.id.code_sent_text);
        otp = view.findViewById(R.id.otp_holder);
        codeSentTo.setText("Code is being sent");
        otpStatus.setText("Waiting for OTP");
        timerHolder.setVisibility(View.VISIBLE);
        resendText.setVisibility(View.INVISIBLE);

        view.findViewById(R.id.dog_avatar_holder).bringToFront();

        countDownTimer = new CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                timerHolder.setText("Resend in " + String.valueOf(millisUntilFinished / 1000));
                resendText.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFinish() {
                timerHolder.setText("");
                resendText.setVisibility(View.VISIBLE);
            }
        };

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                otp.setText(phoneAuthCredential.getSmsCode());
                if (phoneAuthCredential.getSmsCode() == null) {
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                } else {
                    gotoMainScreen();
                }
                otpStatus.setText("Verification Success");
                countDownTimer.cancel();
                timerHolder.setText("");
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                e.printStackTrace();
                otpStatus.setText("Verification Failed");
                countDownTimer.cancel();
                timerHolder.setText("");
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.d(TAG, "onCodeSent: " + s);
                codeSentTo.setText(String.valueOf("Code Sent to " + phoneNumber));
                token = forceResendingToken;
                countDownTimer.start();
            }
        };

        requestOTP(phoneNumber);

        resendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestOTP(phoneNumber);
            }
        });
    }

    private void gotoMainScreen() {
        sharedPreferenceUtil.setPhoneNumber(phoneNumber);
        Intent signUpScreenIntent = new Intent(getContext(), OnBoardingActivity.class);
        signUpScreenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(signUpScreenIntent);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Log.i(TAG, "signInWithPhoneAuthCredential: ");
        if (getActivity() != null) {
            mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.i(TAG, "onComplete: ");
                    if (task.isSuccessful()) {
                        gotoMainScreen();
                    } else {
                        Log.i(TAG, "onComplete: ");
                    }
                }
            });
        }
    }

    public void requestOTP(String s) {
        Log.i(TAG, "requestOTP: " + s);
        if (getActivity() != null && s != null) {
            phoneNumber = s;
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    s,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    getActivity(),               // Activity (for callback binding)
                    mCallbacks);
        } else {
            Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
