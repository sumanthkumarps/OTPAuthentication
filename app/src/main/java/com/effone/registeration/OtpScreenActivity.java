package com.effone.registeration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.effone.registeration.Util.TextSecurePreferences;
import com.effone.registeration.Util.Util;
import com.effone.registeration.crypto.IdentityKeyUtil;
import com.effone.registeration.crypto.SessionUtil;
import com.effone.registeration.push.AccountManagerFactory;

import org.whispersystems.libsignal.IdentityKeyPair;
import org.whispersystems.libsignal.state.PreKeyRecord;
import org.whispersystems.libsignal.state.SignedPreKeyRecord;
import org.whispersystems.libsignal.util.KeyHelper;
import org.whispersystems.libsignal.util.guava.Optional;
import org.whispersystems.signalservice.api.SignalServiceAccountManager;
import org.whispersystems.signalservice.api.util.PhoneNumberFormatter;

import java.io.IOException;
import java.util.List;

/**
 * Created by sumanth.peddinti on 2/20/2018.
 */

public class OtpScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText mEtNumber;
    private Button mBtnSend;
    private String UserNumber;
    private SignalServiceAccountManager accountManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserNumber=getIntent().getStringExtra("opt");
        sendOtp();

        mEtNumber=(TextInputEditText)findViewById(R.id.et_number);
        mBtnSend=findViewById(R.id.button);
        mBtnSend.setOnClickListener(this);

    }

    private void sendOtp() {
        new AsyncTask<Void, Void, Pair<String, Optional<String>>>() {
            @Override
            protected @Nullable
            Pair<String, Optional<String>> doInBackground(Void... voids) {
                try {


                    String password = Util.getSecret(18);

                    Optional<String> gcmToken;

                   /* if (gcmSupported) {
                        gcmToken = Optional.of(GoogleCloudMessaging.getInstance(RegistrationActivity.this).register(GcmRefreshJob.REGISTRATION_ID));
                    } else {
                        gcmToken = Optional.absent();
                    }*/

                    accountManager = AccountManagerFactory.createManager(OtpScreenActivity.this, "+91"+UserNumber, password);
                    accountManager.requestVoiceVerificationCode();

                    return  null/*new Pair<>(password, gcmToken)*/;
                } catch (IOException e) {

                    return null;
                }
            }

            protected void onPostExecute(@Nullable Pair<String, Optional<String>> result) {
                if (result == null) {
                    Toast.makeText(OtpScreenActivity.this, R.string.RegistrationActivity_unable_to_connect_to_service, Toast.LENGTH_LONG).show();
                    return;
                }

                /*registrationState = new RegistrationState(RegistrationState.State.VERIFYING, e164number, result.first, result.second);
                displayVerificationView(e164number, 64);*/
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    @Override
    public void onClick(View view) {
        String number= mEtNumber.getText().toString().trim();
        verifyOtp(number);
    }

    private void verifyOtp(final String number) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    int registrationId = KeyHelper.generateRegistrationId(false);
                    TextSecurePreferences.setLocalRegistrationId(OtpScreenActivity.this, registrationId);
                    SessionUtil.archiveAllSessions(OtpScreenActivity.this);

                    String signalingKey = Util.getSecret(52);

                    accountManager.verifyAccountWithCode(number, signalingKey, registrationId, true);

      /*              IdentityKeyPair identityKey  = IdentityKeyUtil.getIdentityKeyPair(RegistrationActivity.this);
                    List<PreKeyRecord> records      = PreKeyUtil.generatePreKeys(RegistrationActivity.this);
                    SignedPreKeyRecord signedPreKey = PreKeyUtil.generateSignedPreKey(RegistrationActivity.this, identityKey, true);

                    accountManager.setPreKeys(identityKey.getPublicKey(), signedPreKey, records);

                    if (registrationState.gcmToken.isPresent()) {
                        accountManager.setGcmId(registrationState.gcmToken);
                    }

                    TextSecurePreferences.setGcmRegistrationId(RegistrationActivity.this, registrationState.gcmToken.orNull());
                    TextSecurePreferences.setGcmDisabled(RegistrationActivity.this, !registrationState.gcmToken.isPresent());
                    TextSecurePreferences.setWebsocketRegistered(RegistrationActivity.this, true);

                    DatabaseFactory.getIdentityDatabase(RegistrationActivity.this)
                            .saveIdentity(Address.fromSerialized(registrationState.e164number),
                                    identityKey.getPublicKey(), IdentityDatabase.VerifiedStatus.VERIFIED,
                                    true, System.currentTimeMillis(), true);

                    TextSecurePreferences.setVerifying(RegistrationActivity.this, false);
                    TextSecurePreferences.setPushRegistered(RegistrationActivity.this, true);
                    TextSecurePreferences.setLocalNumber(RegistrationActivity.this, registrationState.e164number);
                    TextSecurePreferences.setPushServerPassword(RegistrationActivity.this, registrationState.password);
                    TextSecurePreferences.setSignalingKey(RegistrationActivity.this, signalingKey);
                    TextSecurePreferences.setSignedPreKeyRegistered(RegistrationActivity.this, true);
                    TextSecurePreferences.setPromptedPushRegistration(RegistrationActivity.this, true);
                    TextSecurePreferences.setUnauthorizedReceived(RegistrationActivity.this, false);*/

                    return true;
                } catch (IOException e) {

                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    Toast.makeText(OtpScreenActivity.this,"Done",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OtpScreenActivity.this,"UNDone",Toast.LENGTH_LONG).show();

                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private String getConfiguredE164Number() {
        return PhoneNumberFormatter.formatE164("+91",
                mEtNumber.getText().toString());
    }
}
