package tomesic.zvonimir.credentialmanager.views.authenticate.register;

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

public class RegisterViewModel extends ViewModel implements CredentialComponent.Injectable {

    @Inject
    MasterPasswordRepository masterPasswordRepository;

    private String masterPasswordPassword;

    public String getMasterPassword() {
        return masterPasswordPassword;
    }

    public void setMasterPasswordPassword(String masterPasswordPassword) {
        this.masterPasswordPassword = masterPasswordPassword;
    }

    public boolean validateMasterPassword(String password) {
        if(this.masterPasswordPassword.equals(password)) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addMasterPassword() throws IOException, NoSuchAlgorithmException {
        HashHelper hashHelper = new HashHelper();

        String hashedPassword = hashHelper.getHash(masterPasswordPassword, 10, "SHA-512");
        MasterPassword masterPassword = new MasterPassword(0, hashedPassword, "salt");
        masterPasswordRepository.addMasterPassword(masterPassword).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


    @Override
    public void inject(CredentialComponent credentialComponent) {
        credentialComponent.inject(this);
    }
}
