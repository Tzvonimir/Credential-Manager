package tomesic.zvonimir.credentialmanager.views.authenticate.login;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import tomesic.zvonimir.credentialmanager.CredentialsApplication;
import tomesic.zvonimir.credentialmanager.R;
import tomesic.zvonimir.credentialmanager.helpers.CryptoHelper;
import tomesic.zvonimir.credentialmanager.injections.CredentialFactory;
import tomesic.zvonimir.credentialmanager.views.categories.index.CategoriesFragment;
import tomesic.zvonimir.credentialmanager.views.credentials.index.CredentialsViewModel;
import tomesic.zvonimir.credentialmanager.views.dashboard.DashboardActivity;

public class LoginFragment extends LifecycleFragment {

    private EditText editTextMasterPassword;
    private Button buttonLogin;
    private LoginViewModel loginViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.add_category));
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        setupViews(view);
        setupClickListeners();
        setupViewModel();
        return view;
    }

    private void setupViewModel() {
        CredentialsApplication credentialsApplication = (CredentialsApplication) getActivity().getApplication();
        loginViewModel = ViewModelProviders.of(this, new CredentialFactory(credentialsApplication)).get(LoginViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupClickListeners() {
        editTextMasterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(loginViewModel.validateMasterPassword(s.toString())) {
                    buttonLogin.setEnabled(true);
                } else {
                    editTextMasterPassword.setError(getResources().getString(R.string.required));
                    buttonLogin.setEnabled(false);
                }
            }
        });
        buttonLogin.setOnClickListener(v -> {
            try {
                loginViewModel.checkMasterPassword();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            loginViewModel.getMasterPasswordStatus().observe(this, masterPasswordStatus -> {
                if(masterPasswordStatus == 1) {
                    try {
                        CryptoHelper.saveSecretKey(loginViewModel.getMasterPassword());
                    } catch (KeyStoreException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NoSuchProviderException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnrecoverableEntryException e) {
                        e.printStackTrace();
                    } catch (CertificateException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getContext(), DashboardActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    editTextMasterPassword.setError(getResources().getString(R.string.wrong_password));
                }
            });

        });
    }

    private void setupViews(View view) {
        buttonLogin = (Button) view.findViewById(R.id.button_login);
        editTextMasterPassword = (EditText) view.findViewById(R.id.edit_text_master_password);
    }

}
