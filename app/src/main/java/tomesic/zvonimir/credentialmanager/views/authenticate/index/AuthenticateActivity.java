package tomesic.zvonimir.credentialmanager.views.authenticate.index;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import tomesic.zvonimir.credentialmanager.CredentialsApplication;
import tomesic.zvonimir.credentialmanager.R;
import tomesic.zvonimir.credentialmanager.injections.CredentialFactory;
import tomesic.zvonimir.credentialmanager.views.authenticate.login.LoginFragment;
import tomesic.zvonimir.credentialmanager.views.authenticate.register.RegisterFragment;

public class AuthenticateActivity extends LifecycleActivity{

    private AuthenticateViewModel authenticateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        CredentialsApplication application = (CredentialsApplication) getApplication();
        authenticateViewModel = ViewModelProviders.of(this, new CredentialFactory(application)).get(AuthenticateViewModel.class);

        authenticateViewModel.checkMasterPasswordExistance().observe(this, checkMasterPasswordExistance -> {
                Fragment fragment = null;

                if (checkMasterPasswordExistance == 1) {
                    fragment = new LoginFragment();
                } else {
                    fragment = new RegisterFragment();
                }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_authenticate, fragment);
                ft.commit();
        });

    }
}
