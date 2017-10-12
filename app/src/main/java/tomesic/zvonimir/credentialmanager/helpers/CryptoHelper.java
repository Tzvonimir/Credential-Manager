package tomesic.zvonimir.credentialmanager.helpers;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import java.io.IOException;
import java.security.MessageDigest;

import java.security.KeyStore;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHelper {

    static final String AES = "AES";

    private static KeyStore keyStore;
    private static String additionalCryptoString = "1234abcdad!!!sadsafsafsa78SDASDdsadas";
    private static String initalPasswordProtector = "SetInitalPasswordProtection";
    private static KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(initalPasswordProtector.toCharArray());
    private static String cryptoKey;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void saveSecretKey(String codeToSave) throws KeyStoreException, IOException, NoSuchProviderException, NoSuchAlgorithmException, UnrecoverableEntryException, CertificateException {
        String newCodeToSave = codeToSave + additionalCryptoString;

        keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);

        byte[] decodedKey = newCodeToSave.getBytes("UTF-8");

        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        SecretKeyEntry secretKeyEntry = new SecretKeyEntry(secretKey);

        keyStore.setEntry("secretKeyAlias", secretKeyEntry, protectionParameter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getSecretKey() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, IOException, CertificateException {
        if(cryptoKey == null) {
            SecretKeyEntry  secretKeyEntry = (SecretKeyEntry)keyStore.getEntry("secretKeyAlias", protectionParameter);
            SecretKey secretKey = secretKeyEntry.getSecretKey();

            cryptoKey = byteToBase64(secretKey.getEncoded());
        }
        return cryptoKey;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    static byte[] base64ToByte(String str) throws IOException {

        return Base64.decode(str, Base64.DEFAULT);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static String byteToBase64(byte[] bt) {

        return Base64.encodeToString(bt, Base64.DEFAULT);

    }

    @NonNull
    public static SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        return new SecretKeySpec(digest.digest(), "AES");
    }

    public static void clearKey() {
        cryptoKey = null;
    }

}
