package tomesic.zvonimir.credentialmanager.views.categories.aoe;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import tomesic.zvonimir.credentialmanager.repositories.categories.CategoryRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddCategoryViewModelTest {

    AddCategoryViewModel addCategoryViewModel;

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
        addCategoryViewModel = new AddCategoryViewModel();
        addCategoryViewModel.categoryRepository = categoryRepository;
    }

    @AfterClass
    public static void tearDownClass(){
        RxAndroidPlugins.reset();
    }

    @Test
    public void addCategory() {
        when(categoryRepository.addCategory(any())).thenReturn(Completable.complete());

        addCategoryViewModel.addCategory();

        verify(categoryRepository).addCategory(any());
    }
}
