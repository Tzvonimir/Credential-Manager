package tomesic.zvonimir.credentialmanager;


import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import timber.log.Timber;
import tomesic.zvonimir.credentialmanager.injections.CredentialComponent;
import tomesic.zvonimir.credentialmanager.injections.CredentialModule;
import tomesic.zvonimir.credentialmanager.injections.DaggerCredentialComponent;

public class CredentialsApplication extends Application {

    private final CredentialComponent credentialComponent = createCredentialComponent();

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            //TODO Create crash tree for production
        }
    }

    protected CredentialComponent createCredentialComponent() {
        return DaggerCredentialComponent.builder()
                .credentialModule(new CredentialModule(this))
                .build();
    }

    public CredentialComponent getCredentialComponent() {
        return credentialComponent;
    }

}
