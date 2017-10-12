package tomesic.zvonimir.credentialmanager.injections;

import javax.inject.Singleton;

import dagger.Component;
import tomesic.zvonimir.credentialmanager.views.authenticate.index.AuthenticateViewModel;
import tomesic.zvonimir.credentialmanager.views.authenticate.login.LoginViewModel;
import tomesic.zvonimir.credentialmanager.views.authenticate.register.RegisterViewModel;
import tomesic.zvonimir.credentialmanager.views.categories.aoe.AddCategoryViewModel;
import tomesic.zvonimir.credentialmanager.views.categories.index.CategoriesViewModel;

import tomesic.zvonimir.credentialmanager.views.credentials.aoe.AddCredentialViewModel;
import tomesic.zvonimir.credentialmanager.views.credentials.index.CredentialsViewModel;
import tomesic.zvonimir.credentialmanager.views.dashboard.DashboardViewModel;

@Singleton
@Component(modules = {CredentialModule.class})
public interface CredentialComponent {

    void inject(AuthenticateViewModel authenticateViewModel);
    void inject(LoginViewModel loginViewModel);
    void inject(RegisterViewModel registerViewModel);

    void inject(DashboardViewModel dashboardViewModel);

    void inject(CredentialsViewModel credentialsViewModel);
    void inject(AddCredentialViewModel addCredentialViewModel);

    void inject(AddCategoryViewModel addCategoryViewModel);
    void inject(CategoriesViewModel categoriesViewModel);

    interface Injectable {
        void inject(CredentialComponent credentialComponent);
    }
}
