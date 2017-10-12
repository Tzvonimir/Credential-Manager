package tomesic.zvonimir.credentialmanager.views.categories.index;

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

import tomesic.zvonimir.credentialmanager.injections.CredentialComponent;
import tomesic.zvonimir.credentialmanager.models.Category;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import tomesic.zvonimir.credentialmanager.repositories.categories.CategoryRepository;
import tomesic.zvonimir.credentialmanager.views.authenticate.index.AuthenticateViewModel;
import tomesic.zvonimir.credentialmanager.views.authenticate.login.LoginViewModel;
import tomesic.zvonimir.credentialmanager.views.authenticate.register.RegisterViewModel;
import tomesic.zvonimir.credentialmanager.views.categories.aoe.AddCategoryViewModel;
import tomesic.zvonimir.credentialmanager.views.credentials.aoe.AddCredentialViewModel;
import tomesic.zvonimir.credentialmanager.views.credentials.index.CredentialsViewModel;
import tomesic.zvonimir.credentialmanager.views.dashboard.DashboardViewModel;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static junit.framework.Assert.assertEquals;


public class CategoriesViewModelTest {

    CategoriesViewModel categoriesViewModel;

    @Mock
    CategoryRepository categoryRepository;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @BeforeClass
    public static void setUpClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        categoriesViewModel = new CategoriesViewModel();
        categoriesViewModel.categoryRepository = categoryRepository;
    }

    @AfterClass
    public static void tearDownClass() {
        RxAndroidPlugins.reset();
    }

    @Test
    public void GetCategories() throws InterruptedException {
        MutableLiveData<List<Category>> fakeCategories = getCategoryListMutableData();
        when(categoryRepository.getCategories()).thenReturn(fakeCategories);

        categoriesViewModel.inject(new CredentialComponent() {
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

            }

            @Override
            public void inject(AddCredentialViewModel addCredentialViewModel) {

            }

            @Override
            public void inject(AddCategoryViewModel addCategoryViewModel) {

            }

            @Override
            public void inject(CategoriesViewModel categoriesViewModel) {
                categoriesViewModel.categoryRepository = categoryRepository;
            }
        });

        List<Category> categoriesReturned = getValue(categoriesViewModel.getCategories());

        verify(categoryRepository).getCategories();
        assertEquals(1, categoriesReturned.size());
        assertEquals("Name", categoriesReturned.get(0).getName());
    }

    @NonNull
    private MutableLiveData<List<Category>> getCategoryListMutableData() {
        List<Category> categories = new ArrayList<>();
        tomesic.zvonimir.credentialmanager.models.Category category = new Category(1, "Name");
        categories.add(category);
        MutableLiveData<List<Category>> fakeCategories = new MutableLiveData<>();
        fakeCategories.setValue(categories);
        return fakeCategories;
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
        //noinspection unchecked
        return (T) data[0];
    }
}
