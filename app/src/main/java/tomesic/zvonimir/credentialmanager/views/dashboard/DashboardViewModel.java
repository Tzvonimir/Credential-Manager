package tomesic.zvonimir.credentialmanager.views.dashboard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import tomesic.zvonimir.credentialmanager.injections.CredentialComponent;
import tomesic.zvonimir.credentialmanager.models.Category;
import tomesic.zvonimir.credentialmanager.repositories.categories.CategoryRepository;

public class DashboardViewModel extends ViewModel implements CredentialComponent.Injectable {

    @Inject
    CategoryRepository categoryRepository;
    private LiveData<List<Category>> categories = new MutableLiveData<>();

    @Override
    public void inject(CredentialComponent credentialComponent) {
        credentialComponent.inject(this);
        categories = categoryRepository.getCategories();
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }
}
