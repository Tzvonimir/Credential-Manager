package tomesic.zvonimir.credentialmanager.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import tomesic.zvonimir.credentialmanager.models.Credential;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
import static tomesic.zvonimir.credentialmanager.models.Credential.TABLE_NAME;

@Dao
public interface CredentialDao {

    @Query("SELECT * FROM " + TABLE_NAME)
    LiveData<List<Credential>> getCredentials();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE id = :id")
    LiveData<Credential> getCredential(int id);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE category_id = :categoryId")
    LiveData<List<Credential>> getCredentialsWithCategoryId(int categoryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCredential(Credential credential);

    @Delete
    void deleteCredential(Credential credential);

    @Update(onConflict = REPLACE)
    void updateCredential(Credential credential);

}
