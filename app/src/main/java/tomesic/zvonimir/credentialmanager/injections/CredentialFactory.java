package tomesic.zvonimir.credentialmanager.injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import tomesic.zvonimir.credentialmanager.CredentialsApplication;

public class CredentialFactory extends ViewModelProvider.NewInstanceFactory {

    private CredentialsApplication application;

    public CredentialFactory(CredentialsApplication application) {
        this.application = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if (t instanceof CredentialComponent.Injectable) {
            ((CredentialComponent.Injectable) t).inject(application.getCredentialComponent());
        }
        return t;
    }
}
