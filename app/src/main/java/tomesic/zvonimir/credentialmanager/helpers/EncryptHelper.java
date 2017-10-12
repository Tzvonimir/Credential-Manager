package tomesic.zvonimir.credentialmanager.helpers;

import android.os.Build;
import android.support.annotation.RequiresApi;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static tomesic.zvonimir.credentialmanager.helpers.CryptoHelper.AES;
import static tomesic.zvonimir.credentialmanager.helpers.CryptoHelper.byteToBase64;

public class EncryptHelper {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String data, String secretKey) throws Exception {
        SecretKeySpec generatedKey = CryptoHelper.generateKey(secretKey);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, generatedKey);
        byte[] encryptValue = cipher.doFinal(data.getBytes());
        return byteToBase64(encryptValue);
    }
}
