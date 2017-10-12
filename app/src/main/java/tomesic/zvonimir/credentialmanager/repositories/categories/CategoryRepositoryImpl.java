package tomesic.zvonimir.credentialmanager.repositories.categories;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import tomesic.zvonimir.credentialmanager.config.CredentialDatabase;
import tomesic.zvonimir.credentialmanager.models.Category;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Inject
    CredentialDatabase credentialDatabase;

    public CategoryRepositoryImpl(CredentialDatabase credentialDatabase) {
        this.credentialDatabase = credentialDatabase;
    }

    @Override
    public Completable addCategory(Category category) {
        return Completable.fromAction(() -> credentialDatabase.categoryDao().addCategory(category));
    }

    @Override
    public LiveData<List<Category>> getCategories() {
        //TODO add cache
        return credentialDatabase.categoryDao().getCategories();
    }

    @Override
    public Completable deleteCategory(Category category) {
        return Completable.fromAction(() -> credentialDatabase.categoryDao().deleteCategory(category));
    }

}
