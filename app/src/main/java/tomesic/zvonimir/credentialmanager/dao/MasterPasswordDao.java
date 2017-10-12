package tomesic.zvonimir.credentialmanager.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import tomesic.zvonimir.credentialmanager.models.MasterPassword;

import static tomesic.zvonimir.credentialmanager.models.MasterPassword.TABLE_NAME;

@Dao
public interface MasterPasswordDao {

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE id = :id LIMIT 1")
    LiveData<MasterPassword> getMasterPassword(int id);

    @Query("SELECT COUNT(1) FROM " + TABLE_NAME + " WHERE id = :id LIMIT 1")
    LiveData<Integer> checkMasterPasswordExistance(int id);

    @Query("SELECT COUNT(1) FROM " + TABLE_NAME + " WHERE password = :password LIMIT 1")
    LiveData<Integer> checkIsMasterPasswordTrue(String password);

    @Query("SELECT COUNT(1) FROM " + TABLE_NAME + " WHERE id = :id LIMIT 1")
    Integer checkMasterPasswordExistanceInt(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMasterPassword(MasterPassword masterPassword);
}
