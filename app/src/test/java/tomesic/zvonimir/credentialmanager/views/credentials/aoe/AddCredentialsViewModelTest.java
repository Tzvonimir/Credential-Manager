package tomesic.zvonimir.credentialmanager.views.credentials.aoe;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.util.Base64;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Completable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import tomesic.zvonimir.credentialmanager.helpers.CryptoHelper;
import tomesic.zvonimir.credentialmanager.repositories.credentials.CredentialRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CryptoHelper.class})
public class AddCredentialsViewModelTest {

    AddCredentialViewModel addCredentialViewModel;

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
        addCredentialViewModel = new AddCredentialViewModel();
        addCredentialViewModel.credentialRepository = credentialRepository;
    }

    @AfterClass
    public static void tearDownClass(){
        RxAndroidPlugins.reset();
    }

    @Test
    public void addCredental() throws Exception {
        PowerMockito.mockStatic(CryptoHelper.class);
        CryptoHelper.saveSecretKey("Test");

        when(credentialRepository.addCredential(any())).thenReturn(Completable.complete());

        addCredentialViewModel.addCredential(1);

        verify(credentialRepository).addCredential(any());
    }
}
