package tomesic.zvonimir.credentialmanager.repositories.credentials;

import android.arch.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import tomesic.zvonimir.credentialmanager.models.Credential;

public interface CredentialRepository {

    Completable addCredential(Credential credential);

    LiveData<List<Credential>> getCredentials();

    LiveData<Credential> getCredential(int id);

    LiveData<List<Credential>> getCredentialsWithCategoryId(int categoryId);

    Completable deleteCredential(Credential credential);

    Completable updateCredential(Credential credential);

}