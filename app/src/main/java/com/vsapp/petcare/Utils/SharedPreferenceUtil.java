package com.vsapp.petcare.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by venkat on 19/1/18.
 */

public class SharedPreferenceUtil {
    private static SharedPreferenceUtil sharedPreferenceUtil = null;
    private SharedPreferences sharedPreferences;

    public synchronized static SharedPreferenceUtil getInstance(Context context) {
        if (sharedPreferenceUtil == null) {
            sharedPreferenceUtil = new SharedPreferenceUtil(context);
        }
        return sharedPreferenceUtil;
    }

    private SharedPreferenceUtil(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getEmail() {
        return sharedPreferences.getString(Constants.EMAIL_ID, "null");
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.EMAIL_ID, email);
        editor.apply();
    }

    public String getPhoneNumber() {
        return sharedPreferences.getString(Constants.PHONE_NUMBER, "null");
    }

    public void setPhoneNumber(String phoneNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PHONE_NUMBER, phoneNumber);
        editor.apply();
    }

    public void setName(String displayName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.DISPLAY_NAME, displayName);
        editor.apply();
    }

    public String getName() {
        return sharedPreferences.getString(Constants.DISPLAY_NAME, "null");
    }

    public void setPic(String profilePicture) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PROFILE_PIC, profilePicture);
        editor.apply();
    }

    public String getPic() {
        return sharedPreferences.getString(Constants.PROFILE_PIC, "null");
    }

    public void setOnBoardingDone(boolean isDone) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.ONBOARDING_DONE, isDone);
        editor.apply();
    }

    public Boolean getOnBoardingDone() {
        return sharedPreferences.getBoolean(Constants.ONBOARDING_DONE, false);
    }

}
