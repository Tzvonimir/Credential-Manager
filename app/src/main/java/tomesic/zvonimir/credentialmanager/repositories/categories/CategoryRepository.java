package tomesic.zvonimir.credentialmanager.repositories.categories;

import android.arch.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import tomesic.zvonimir.credentialmanager.models.Category;

public interface CategoryRepository {

    Completable addCategory(Category category);

    LiveData<List<Category>> getCategories();

    Completable deleteCategory(Category category);

}
