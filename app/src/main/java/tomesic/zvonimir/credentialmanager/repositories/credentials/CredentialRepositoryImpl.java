package tomesic.zvonimir.credentialmanager.repositories.credentials;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import timber.log.Timber;
import tomesic.zvonimir.credentialmanager.config.CredentialDatabase;
import tomesic.zvonimir.credentialmanager.models.Category;
import tomesic.zvonimir.credentialmanager.models.Credential;

public class CredentialRepositoryImpl implements CredentialRepository {

    @Inject
    CredentialDatabase credentialDatabase;

    public CredentialRepositoryImpl(CredentialDatabase credentialDatabase) {
        this.credentialDatabase = credentialDatabase;
    }

    @Override
    public Completable addCredential(Credential credential) {
        return Completable.fromAction(() -> credentialDatabase.credentialDao().addCredential(credential));
    }

    @Override
    public LiveData<List<Credential>> getCredentials() {
        //TODO add cache
        return credentialDatabase.credentialDao().getCredentials();
    }

    @Override
    public LiveData<Credential> getCredential(int id) {
        //TODO add cache
        return credentialDatabase.credentialDao().getCredential(id);
    }

    @Override
    public LiveData<List<Credential>> getCredentialsWithCategoryId(int categoryId) {
        //TODO add cache
        return credentialDatabase.credentialDao().getCredentialsWithCategoryId(categoryId);
    }

    @Override
    public Completable deleteCredential(Credential credential) {
        return Completable.fromAction(() -> credentialDatabase.credentialDao().deleteCredential(credential));
    }

    @Override
    public Completable updateCredential(Credential credential) {
        return Completable.fromAction(() -> credentialDatabase.credentialDao().updateCredential(credential));
    }

}
