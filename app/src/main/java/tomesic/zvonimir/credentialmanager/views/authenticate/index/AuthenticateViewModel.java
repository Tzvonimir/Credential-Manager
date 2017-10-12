package tomesic.zvonimir.credentialmanager.views.authenticate.index;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import tomesic.zvonimir.credentialmanager.injections.CredentialComponent;
import tomesic.zvonimir.credentialmanager.repositories.master_passwords.MasterPasswordRepository;

public class AuthenticateViewModel extends ViewModel implements CredentialComponent.Injectable {

    @Inject
    MasterPasswordRepository masterPasswordRepository;
    private LiveData<Integer> masterPasswordExistance = new MutableLiveData<>();

    @Override
    public void inject(CredentialComponent credentialComponent) {
        credentialComponent.inject(this);
        masterPasswordExistance = masterPasswordRepository.checkMasterPasswordExistance(1);
    }

    public LiveData<Integer> checkMasterPasswordExistance() {
        return masterPasswordExistance;
    }

}
