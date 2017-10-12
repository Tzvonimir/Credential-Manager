package tomesic.zvonimir.credentialmanager.views.categories.index;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import tomesic.zvonimir.credentialmanager.CredentialsApplication;
import tomesic.zvonimir.credentialmanager.injections.CredentialComponent;
import tomesic.zvonimir.credentialmanager.models.Category;
import tomesic.zvonimir.credentialmanager.repositories.categories.CategoryRepository;
import tomesic.zvonimir.credentialmanager.views.SplashActivity;
import tomesic.zvonimir.credentialmanager.views.dashboard.DashboardActivity;

public class CategoriesViewModel extends ViewModel implements CredentialComponent.Injectable {

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

    public void deleteCategory(Category category) {
        categoryRepository.deleteCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - deleted category");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("OnError - deleted category: ", e);
                    }
                });
    }

}
