package tomesic.zvonimir.credentialmanager.repositories.master_passwords;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import tomesic.zvonimir.credentialmanager.config.CredentialDatabase;
import tomesic.zvonimir.credentialmanager.models.MasterPassword;

public class MasterPasswordRepositoryImpl implements MasterPasswordRepository {

    @Inject
    CredentialDatabase credentialDatabase;

    public MasterPasswordRepositoryImpl(CredentialDatabase credentialDatabase) {
        this.credentialDatabase = credentialDatabase;
    }

    @Override
    public Completable addMasterPassword(MasterPassword masterPassword) {
        return Completable.fromAction(() -> credentialDatabase.masterPasswordDao().addMasterPassword(masterPassword));
    }

    @Override
    public LiveData<Integer> checkMasterPasswordExistance(int id) {
        //TODO add cache
        return credentialDatabase.masterPasswordDao().checkMasterPasswordExistance(id);
    }

    @Override
    public LiveData<Integer> checkIsMasterPasswordTrue(String password) {
        //TODO add cache
        return credentialDatabase.masterPasswordDao().checkIsMasterPasswordTrue(password);
    }

    @Override
    public Integer checkMasterPasswordExistanceInt(int id) {
        //TODO add cache
        return credentialDatabase.masterPasswordDao().checkMasterPasswordExistanceInt(id);
    }

    @Override
    public LiveData<MasterPassword> getMasterPassword(int id) {
        //TODO add cache
        return credentialDatabase.masterPasswordDao().getMasterPassword(id);
    }
}