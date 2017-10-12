package tomesic.zvonimir.credentialmanager.views.credentials.index;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import tomesic.zvonimir.credentialmanager.injections.CredentialComponent;
import tomesic.zvonimir.credentialmanager.models.Credential;
import tomesic.zvonimir.credentialmanager.repositories.credentials.CredentialRepository;
import tomesic.zvonimir.credentialmanager.views.authenticate.index.AuthenticateViewModel;
import tomesic.zvonimir.credentialmanager.views.authenticate.login.LoginViewModel;
import tomesic.zvonimir.credentialmanager.views.authenticate.register.RegisterViewModel;
import tomesic.zvonimir.credentialmanager.views.categories.aoe.AddCategoryViewModel;
import tomesic.zvonimir.credentialmanager.views.categories.index.CategoriesViewModel;
import tomesic.zvonimir.credentialmanager.views.credentials.aoe.AddCredentialViewModel;
import tomesic.zvonimir.credentialmanager.views.dashboard.DashboardViewModel;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static junit.framework.Assert.assertEquals;


public class CredentialsViewModelTest {

    CredentialsViewModel credentialsViewModel;

    @Mock
    CredentialRepository credentialRepository;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @BeforeClass
    public static void setUpClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        credentialsViewModel = new CredentialsViewModel();
        credentialsViewModel.credentialRepository = credentialRepository;
    }

    @AfterClass
    public static void tearDownClass() {
        RxAndroidPlugins.reset();
    }

    @Test
    public void GetCredentials() throws InterruptedException {
        MutableLiveData<List<Credential>> fakeCredentals = getCredentalListMutableData();
        when(credentialRepository.getCredentialsWithCategoryId(1)).thenReturn(fakeCredentals);

        credentialsViewModel.inject(new CredentialComponent() {
            @Override
            public void inject(AuthenticateViewModel authenticateViewModel) {

            }

            @Override
            public void inject(LoginViewModel loginViewModel) {

            }

            @Override
            public void inject(RegisterViewModel registerViewModel) {

            }

            @Override
            public void inject(DashboardViewModel dashboardViewModel) {

            }

            @Override
            public void inject(CredentialsViewModel credentialsViewModel) {
                credentialsViewModel.credentialRepository = credentialRepository;
            }

            @Override
            public void inject(AddCredentialViewModel addCredentialViewModel) {

            }

            @Override
            public void inject(AddCategoryViewModel addCategoryViewModel) {

            }

            @Override
            public void inject(CategoriesViewModel categoriesViewModel) {

            }
        });

        List<Credential> credentialsReturned = getValue(credentialsViewModel.getCredentialsWithCategoryId(1));

        verify(credentialRepository).getCredentialsWithCategoryId(1);
        assertEquals(1, credentialsReturned.size());
        assertEquals("Name", credentialsReturned.get(0).getName());
        assertEquals("Username", credentialsReturned.get(0).getUsername());
        assertEquals("email@email.com", credentialsReturned.get(0).getEmail());
        assertEquals("password", credentialsReturned.get(0).getPassword());
        assertEquals("comment", credentialsReturned.get(0).getComment());
        assertEquals("www.url.com", credentialsReturned.get(0).getURL());
    }

    @NonNull
    private MutableLiveData<List<Credential>> getCredentalListMutableData() {
        List<Credential> credentials = new ArrayList<>();
        Credential credential = new Credential(1, "Name", "Username", "email@email.com", "password", "comment", "www.url.com", 1);
        credentials.add(credential);
        MutableLiveData<List<Credential>> fakeCredentials = new MutableLiveData<>();
        fakeCredentials.setValue(credentials);
        return fakeCredentials;
    }

    public static <T> T getValue(LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);

        return (T) data[0];
    }
}