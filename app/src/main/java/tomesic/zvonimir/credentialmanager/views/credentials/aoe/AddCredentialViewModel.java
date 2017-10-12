package tomesic.zvonimir.credentialmanager.views.credentials.aoe;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Patterns;
import android.widget.Toast;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import tomesic.zvonimir.credentialmanager.helpers.CryptoHelper;
import tomesic.zvonimir.credentialmanager.helpers.EncryptHelper;
import tomesic.zvonimir.credentialmanager.injections.CredentialComponent;
import tomesic.zvonimir.credentialmanager.models.Credential;
import tomesic.zvonimir.credentialmanager.repositories.credentials.CredentialRepository;


public class AddCredentialViewModel extends ViewModel implements CredentialComponent.Injectable {

    @Inject
    public
    CredentialRepository credentialRepository;

    private LiveData<Credential> credential = new MutableLiveData<>();

    private int credentialId;
    private String credentialName;
    private String credentialPassword;
    private String credentialUsername;
    private String credentialEmail;
    private String credentialURL;
    private String credentialComment;
    private int credentialCategoryId;

    private boolean validateEmail = true;
    private boolean validateURL = true;
    private boolean validateName = false;

    public int getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(int credentialId) {
        this.credentialId = credentialId;
    }

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }

    public String getCredentialPassword() {
        return credentialPassword;
    }

    public void setCredentialPassword(String credentialPassword) {
        this.credentialPassword = credentialPassword;
    }

    public String getCredentialUsername() {
        return credentialUsername;
    }

    public void setCredentialUsername(String credentialUsername) {
        this.credentialUsername = credentialUsername;
    }

    public String getCredentialEmail() {
        return credentialEmail;
    }

    public void setCredentialEmail(String credentialEmail) {
        this.credentialEmail = credentialEmail;
    }

    public String getCredentialURL() {
        return credentialURL;
    }

    public void setCredentialURL(String credentialURL) {
        this.credentialURL = credentialURL;
    }

    public String getCredentialComment() {
        return credentialComment;
    }

    public void setCredentialComment(String credentialComment) {
        this.credentialComment = credentialComment;
    }

    public int getCredentialCategoryId() {
        return credentialCategoryId;
    }

    public void setCredentialCategoryId(int categoryId) {
        this.credentialCategoryId = categoryId;
    }

    public boolean validateCredentalName(String name) {
        if(name != null && !name.isEmpty()) {
            setCredentialName(name);
            this.validateName = true;
            return true;
        }
        this.validateName = false;
        return false;
    }

    public boolean validateCredentalEmail(String email) {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() && email != null && !email.isEmpty()) {
            this.validateEmail = false;
            return false;
        }
        this.validateEmail = true;
        setCredentialEmail(email);
        return true;
    }

    public boolean validateCredentialURL(String url) {
        if(!Patterns.WEB_URL.matcher(url).matches() && url != null && !url.isEmpty()) {
            this.validateURL = false;
            return false;
        }
        this.validateURL = true;
        setCredentialURL(url);
        return true;
    }

    public boolean checkValidation() {
        if(!validateURL || !validateName || !validateEmail) {
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addCredential(int categoryId) throws Exception {
        String secretKey = CryptoHelper.getSecretKey();

        if(credentialUsername != null && !credentialUsername.isEmpty()) {
            credentialUsername = EncryptHelper.encrypt(credentialUsername, secretKey);
        }

        if(credentialPassword != null && !credentialPassword.isEmpty()) {
            credentialPassword = EncryptHelper.encrypt(credentialPassword, secretKey);
        }

        Credential newCredential = new Credential(0, credentialName, credentialUsername, credentialEmail, credentialPassword, credentialComment, credentialURL, categoryId);
        credentialRepository.addCredential(newCredential).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - successfully added credental");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("onError - add:", e);
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateCredential() throws Exception {
        String secretKey = CryptoHelper.getSecretKey();

        if(credentialUsername != null && !credentialUsername.isEmpty()) {
            credentialUsername = EncryptHelper.encrypt(credentialUsername, secretKey);
        }

        if(credentialPassword != null && !credentialPassword.isEmpty()) {
            credentialPassword = EncryptHelper.encrypt(credentialPassword, secretKey);
        }

        Credential newCredential  = new Credential(credentialId, credentialName, credentialUsername, credentialEmail, credentialPassword, credentialComment, credentialURL, credentialCategoryId);
        credentialRepository.updateCredential(newCredential).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - successfully updated credental");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("onError - add:", e);
                    }
                });;

    }

    public LiveData<Credential> getCredential(int credentialId) {
        return credentialRepository.getCredential(credentialId);
    }


    @Override
    public void inject(CredentialComponent credentialComponent) {
        credentialComponent.inject(this);
    }
}
