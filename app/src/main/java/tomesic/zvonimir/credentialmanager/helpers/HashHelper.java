package tomesic.zvonimir.credentialmanager.helpers;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static tomesic.zvonimir.credentialmanager.helpers.CryptoHelper.base64ToByte;
import static tomesic.zvonimir.credentialmanager.helpers.CryptoHelper.byteToBase64;

public class HashHelper {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getHash(String password, int iterations, String algorithm) throws IOException, NoSuchAlgorithmException {

        byte[] salt = base64ToByte("salt");

        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.reset();
        messageDigest.update(salt);
        byte[] bytePassword = messageDigest.digest(password.getBytes("UTF-8"));

        for (int i = 0; i < iterations; i++) {
            messageDigest.reset();
            bytePassword = messageDigest.digest(bytePassword);
        }

        return byteToBase64(bytePassword);
    }
}