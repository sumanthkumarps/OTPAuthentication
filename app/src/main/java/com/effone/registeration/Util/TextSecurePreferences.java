package com.effone.registeration.Util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by sumanth.peddinti on 2/21/2018.
 */

public class TextSecurePreferences {
    private static final String LOCAL_NUMBER_PREF                = "pref_local_number";
    private static final String GCM_PASSWORD_PREF                = "pref_gcm_password";

    private static final String LOCAL_REGISTRATION_ID_PREF       = "pref_local_registration_id";
    public static String getLocalNumber(Context context) {
        return getStringPreference(context, LOCAL_NUMBER_PREF, null);
    }

    public static String getPushServerPassword(Context context) {
        return getStringPreference(context, GCM_PASSWORD_PREF, null);
    }

    public static String getStringPreference(Context context, String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
    }

    public static int getLocalRegistrationId(Context context) {
        return getIntegerPreference(context, LOCAL_REGISTRATION_ID_PREF, 0);
    }

    public static void setLocalRegistrationId(Context context, int registrationId) {
        setIntegerPrefrence(context, LOCAL_REGISTRATION_ID_PREF, registrationId);
    }
    private static int getIntegerPreference(Context context, String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
    }
    private static void setIntegerPrefrence(Context context, String key, int value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).apply();
    }

}
