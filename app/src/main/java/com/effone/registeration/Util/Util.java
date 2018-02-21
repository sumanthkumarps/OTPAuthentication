package com.effone.registeration.Util;

import org.whispersystems.signalservice.internal.util.Base64;

import static org.whispersystems.signalservice.internal.util.Util.getSecretBytes;

/**
 * Created by sumanth.peddinti on 2/21/2018.
 */

public class Util {
    public static String getSecret(int size) {
        byte[] secret = getSecretBytes(size);
        return Base64.encodeBytes(secret);
    }
}
