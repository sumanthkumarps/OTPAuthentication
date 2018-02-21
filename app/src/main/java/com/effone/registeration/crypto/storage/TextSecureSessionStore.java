package com.effone.registeration.crypto.storage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.state.SessionRecord;
import org.whispersystems.libsignal.state.SessionStore;
import org.whispersystems.signalservice.api.push.SignalServiceAddress;

import java.io.File;
import java.util.List;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by sumanth.peddinti on 2/21/2018.
 */

public class TextSecureSessionStore implements SessionStore {
    @NonNull
    private final Context      context;
    public TextSecureSessionStore(Context context) {
        this.context = context;
    }

    @Override
    public SessionRecord loadSession(SignalProtocolAddress address) {
        return null;
    }

    @Override
    public List<Integer> getSubDeviceSessions(String name) {
        return null;
    }

    @Override
    public void storeSession(SignalProtocolAddress address, SessionRecord record) {

    }

    @Override
    public boolean containsSession(SignalProtocolAddress address) {
        return false;
    }

    @Override
    public void deleteSession(SignalProtocolAddress address) {

    }

    @Override
    public void deleteAllSessions(String name) {

    }
    private static final Object FILE_LOCK             = new Object();
    private static final String SESSIONS_DIRECTORY_V2 = "sessions-v2";
    public void archiveAllSessions() {
        synchronized (FILE_LOCK) {
            File directory = getSessionDirectory();

            for (File session : directory.listFiles()) {
                if (session.isFile()) {
                    SignalProtocolAddress address = getAddressName(session);

                    if (address != null) {
                        SessionRecord sessionRecord = loadSession(address);
                        sessionRecord.archiveCurrentState();
                        storeSession(address, sessionRecord);
                    }
                }
            }
        }
    }
    private File getSessionDirectory() {
        File directory = new File(context.getFilesDir(), SESSIONS_DIRECTORY_V2);

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.w(TAG, "Session directory creation failed!");
            }
        }

        return directory;
    }

    private @Nullable
    SignalProtocolAddress getAddressName(File sessionFile) {
        try {
            String[] parts = sessionFile.getName().split("[.]");

            int deviceId;

            if (parts.length > 1) deviceId = Integer.parseInt(parts[1]);
            else                  deviceId = SignalServiceAddress.DEFAULT_DEVICE_ID;

            return new SignalProtocolAddress(parts[0], deviceId);
        } catch (NumberFormatException e) {
            Log.w(TAG, e);
            return null;
        }
    }
}
