package tomesic.zvonimir.credentialmanager.injections;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tomesic.zvonimir.credentialmanager.CredentialsApplication;
import tomesic.zvonimir.credentialmanager.config.CredentialDatabase;
import tomesic.zvonimir.credentialmanager.repositories.categories.CategoryRepository;
import tomesic.zvonimir.credentialmanager.repositories.categories.CategoryRepositoryImpl;
import tomesic.zvonimir.credentialmanager.repositories.credentials.CredentialRepository;
import tomesic.zvonimir.credentialmanager.repositories.credentials.CredentialRepositoryImpl;
import tomesic.zvonimir.credentialmanager.repositories.master_passwords.MasterPasswordRepository;
import tomesic.zvonimir.credentialmanager.repositories.master_passwords.MasterPasswordRepositoryImpl;

@Module
public class CredentialModule {

    private CredentialsApplication credentialsApplication;

    public CredentialModule(CredentialsApplication credentialsApplication) {
        this.credentialsApplication = credentialsApplication;
    }

    @Provides
    Context applicationContext() {
        return credentialsApplication;
    }

    @Provides
    @Singleton
    CategoryRepository providesCategoryRepository(CredentialDatabase credentialDatabase) {
        return new CategoryRepositoryImpl(credentialDatabase);
    }

    @Provides
    @Singleton
    CredentialRepository providesCredentialRepository(CredentialDatabase credentialDatabase) {
        return new CredentialRepositoryImpl(credentialDatabase);
    }

    @Provides
    @Singleton
    MasterPasswordRepository providesMasterPasswordRepository(CredentialDatabase credentialDatabase) {
        return new MasterPasswordRepositoryImpl(credentialDatabase);
    }

    @Provides
    @Singleton
    CredentialDatabase providesCategoryDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), CredentialDatabase.class, "credentials").build();
    }
}
