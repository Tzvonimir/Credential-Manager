package tomesic.zvonimir.credentialmanager.helpers;

import android.os.Build;
import android.support.annotation.RequiresApi;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static tomesic.zvonimir.credentialmanager.helpers.CryptoHelper.AES;
import static tomesic.zvonimir.credentialmanager.helpers.CryptoHelper.base64ToByte;

public class DecryptHelper {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String data, String secretKey) throws Exception {
        SecretKeySpec generateKey = CryptoHelper.generateKey(secretKey);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, generateKey);
        byte[] decryptValue = base64ToByte(data);
        byte[] decryptedValue = cipher.doFinal(decryptValue);
        return new String(decryptedValue);
    }
}
