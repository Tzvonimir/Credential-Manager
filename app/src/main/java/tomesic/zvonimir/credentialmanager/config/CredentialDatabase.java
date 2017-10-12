package tomesic.zvonimir.credentialmanager.config;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import tomesic.zvonimir.credentialmanager.dao.CategoryDao;
import tomesic.zvonimir.credentialmanager.dao.CredentialDao;
import tomesic.zvonimir.credentialmanager.dao.MasterPasswordDao;
import tomesic.zvonimir.credentialmanager.models.Category;
import tomesic.zvonimir.credentialmanager.models.Credential;
import tomesic.zvonimir.credentialmanager.models.MasterPassword;

@Database(entities = {Category.class, Credential.class, MasterPassword.class}, version = 7)
@TypeConverters(DateTypeConverter.class)
public abstract class CredentialDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();
    public abstract CredentialDao credentialDao();
    public abstract MasterPasswordDao masterPasswordDao();

}
