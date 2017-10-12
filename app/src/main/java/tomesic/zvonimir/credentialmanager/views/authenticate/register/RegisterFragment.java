package tomesic.zvonimir.credentialmanager.views.authenticate.register;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import tomesic.zvonimir.credentialmanager.CredentialsApplication;
import tomesic.zvonimir.credentialmanager.R;
import tomesic.zvonimir.credentialmanager.injections.CredentialFactory;
import tomesic.zvonimir.credentialmanager.views.authenticate.login.LoginFragment;

public class RegisterFragment extends LifecycleFragment {

    private EditText editTextMasterPassword, editTextMasterPasswordRepeat;
    private Button buttonRegister;
    private RegisterViewModel registerViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.add_category));
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        setupViews(view);
        setupClickListeners();
        setupViewModel();
        return view;
    }

    private void setupViewModel() {
        CredentialsApplication credentialsApplication = (CredentialsApplication) getActivity().getApplication();
        registerViewModel = ViewModelProviders.of(this, new CredentialFactory(credentialsApplication)).get(RegisterViewModel.class);
        editTextMasterPassword.setText(registerViewModel.getMasterPassword());
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
                registerViewModel.setMasterPasswordPassword(s.toString());
            }
        });
        editTextMasterPasswordRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(registerViewModel.validateMasterPassword(s.toString())) {
                    buttonRegister.setEnabled(true);
                } else {
                    buttonRegister.setEnabled(false);
                    editTextMasterPasswordRepeat.setError(getResources().getString(R.string.password_missmatch));
                }
            }
        });
        buttonRegister.setOnClickListener(v -> {
            try {
                registerViewModel.addMasterPassword();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_authenticate, new LoginFragment());
            transaction.commit();
        });
    }

    private void setupViews(View view) {
        buttonRegister = (Button) view.findViewById(R.id.button_register);
        editTextMasterPassword = (EditText) view.findViewById(R.id.edit_text_master_password);
        editTextMasterPasswordRepeat = (EditText) view.findViewById(R.id.edit_text_master_password_repeat);
    }
}
