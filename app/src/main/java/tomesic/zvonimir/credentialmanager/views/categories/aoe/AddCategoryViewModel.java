package tomesic.zvonimir.credentialmanager.views.categories.aoe;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.widget.Toast;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import tomesic.zvonimir.credentialmanager.R;
import tomesic.zvonimir.credentialmanager.injections.CredentialComponent;
import tomesic.zvonimir.credentialmanager.models.Category;
import tomesic.zvonimir.credentialmanager.repositories.categories.CategoryRepository;


public class AddCategoryViewModel extends ViewModel implements CredentialComponent.Injectable {

    @Inject
    CategoryRepository categoryRepository;
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    private void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean validateCategoryName(String name) {
        if(name != null && !name.isEmpty()) {
            setCategoryName(name);
            return true;
        }
        return false;
    }

    public void addCategory() {
        Category category = new Category(0, categoryName);
        categoryRepository.addCategory(category).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - successfully added category");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("onError - add:", e);
                    }
                });
    }


    @Override
    public void inject(CredentialComponent credentialComponent) {
        credentialComponent.inject(this);
    }
}
