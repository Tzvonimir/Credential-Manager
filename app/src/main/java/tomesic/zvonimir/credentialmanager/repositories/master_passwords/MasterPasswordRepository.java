package tomesic.zvonimir.credentialmanager.repositories.master_passwords;

import android.arch.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import tomesic.zvonimir.credentialmanager.models.MasterPassword;

public interface MasterPasswordRepository {

    Completable addMasterPassword(MasterPassword masterPassword);

    LiveData<Integer> checkMasterPasswordExistance(int id);

    LiveData<Integer> checkIsMasterPasswordTrue(String password);

    Integer checkMasterPasswordExistanceInt(int id);

    LiveData<MasterPassword> getMasterPassword(int id);

}
