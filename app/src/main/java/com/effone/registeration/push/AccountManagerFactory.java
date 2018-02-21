package com.effone.registeration.push;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.effone.registeration.Util.TextSecurePreferences;
import com.google.android.gms.security.ProviderInstaller;

import org.whispersystems.signalservice.api.SignalServiceAccountManager;

public class AccountManagerFactory {
  public static final String SIGNAL_CDN_URL = "https://cdn.signal.org";
  public static final String SIGNAL_URL = "https://textsecure-service.whispersystems.org";
  public static final String USER_AGENT = "OWA";

  private static final String TAG = AccountManagerFactory.class.getName();

  public static SignalServiceAccountManager createManager(Context context) {
    return new SignalServiceAccountManager(new SignalServiceNetworkAccess(context).getConfiguration(context),
                                           TextSecurePreferences.getLocalNumber(context),
                                           TextSecurePreferences.getPushServerPassword(context),
                                           USER_AGENT);
  }

  public static SignalServiceAccountManager createManager(final Context context, String number, String password) {
    if (new SignalServiceNetworkAccess(context).isCensored(number)) {
      new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... params) {
          try {
            ProviderInstaller.installIfNeeded(context);
          } catch (Throwable t) {
            Log.w(TAG, t);
          }
          return null;
        }
      }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    return new SignalServiceAccountManager(new SignalServiceNetworkAccess(context).getConfiguration(number),
                                           number, password, USER_AGENT);
  }

}
