package tomesic.zvonimir.credentialmanager.views.authenticate.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import tomesic.zvonimir.credentialmanager.helpers.HashHelper;
import tomesic.zvonimir.credentialmanager.injections.CredentialComponent;
import tomesic.zvonimir.credentialmanager.models.MasterPassword;
import tomesic.zvonimir.credentialmanager.repositories.master_passwords.MasterPasswordRepository;

public class LoginViewModel extends ViewModel implements CredentialComponent.Injectable {


    @Inject
    MasterPasswordRepository masterPasswordRepository;

    private String masterPasswordPassword;
    private LiveData<Integer> masterPasswordStatus = new MutableLiveData<>();

    public String getMasterPassword() {
        return masterPasswordPassword;
    }

    public void setMasterPasswordPassword(String masterPasswordPassword) {
        this.masterPasswordPassword = masterPasswordPassword;
    }

    public boolean validateMasterPassword(String password) {
        if(password != null && !password.isEmpty()) {
            setMasterPasswordPassword(password);
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void checkMasterPassword() throws IOException, NoSuchAlgorithmException {
        HashHelper hashHelper = new HashHelper();
        String hashedPassword = hashHelper.getHash(masterPasswordPassword, 10, "SHA-512");

        masterPasswordStatus = masterPasswordRepository.checkIsMasterPasswordTrue(hashedPassword);
    }

    public LiveData<Integer> getMasterPasswordStatus() {
        return masterPasswordStatus;
    }


    @Override
    public void inject(CredentialComponent credentialComponent) {
        credentialComponent.inject(this);
    }

}
