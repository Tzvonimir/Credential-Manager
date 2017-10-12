package tomesic.zvonimir.credentialmanager.views.credentials.index;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import tomesic.zvonimir.credentialmanager.injections.CredentialComponent;
import tomesic.zvonimir.credentialmanager.models.Credential;
import tomesic.zvonimir.credentialmanager.repositories.credentials.CredentialRepository;
import tomesic.zvonimir.credentialmanager.views.dashboard.DashboardActivity;

public class CredentialsViewModel extends ViewModel implements CredentialComponent.Injectable {

    @Inject
    public
    CredentialRepository credentialRepository;
    public LiveData<List<Credential>> credentials = new MutableLiveData<>();

    @Override
    public void inject(CredentialComponent credentialComponent) {
        credentialComponent.inject(this);
    }

    public LiveData<List<Credential>> getCredentialsWithCategoryId(int categoryId) {
        return credentialRepository.getCredentialsWithCategoryId(categoryId);
    }

    public void deleteCredental(Credential credential) {
        credentialRepository.deleteCredential(credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - deleted credential");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("OnError - deleted credential: ", e);
                    }
                });
    }

}
