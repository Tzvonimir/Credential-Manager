package tomesic.zvonimir.credentialmanager.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import tomesic.zvonimir.credentialmanager.models.Category;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
import static tomesic.zvonimir.credentialmanager.models.Category.TABLE_NAME;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM " + TABLE_NAME)
    LiveData<List<Category>> getCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Update(onConflict = REPLACE)
    void updateCategory(Category category);

}
