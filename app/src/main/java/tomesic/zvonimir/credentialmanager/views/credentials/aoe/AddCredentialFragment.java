package tomesic.zvonimir.credentialmanager.views.credentials.aoe;

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
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import tomesic.zvonimir.credentialmanager.CredentialsApplication;
import tomesic.zvonimir.credentialmanager.R;
import tomesic.zvonimir.credentialmanager.helpers.CryptoHelper;
import tomesic.zvonimir.credentialmanager.helpers.DecryptHelper;
import tomesic.zvonimir.credentialmanager.injections.CredentialFactory;

public class AddCredentialFragment extends LifecycleFragment {


    private EditText editTextName, editTextUsername, editTextEmail, editTextPassword, editTextComment, editTextURL;
    private Button buttonAddCredential, buttonCancelCredential;
    private AddCredentialViewModel addCredentialViewModel;
    private int categoryId;
    private int credentialId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_credential, container, false);
        categoryId = getArguments().getInt("categoryId");
        credentialId = getArguments().getInt("credentialId");

        setupViews(view);
        setupClickListeners();
        setupViewModel();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupViewModel() {
        String fragmentTitle = getResources().getString(R.string.add_credential);
        CredentialsApplication credentialsApplication = (CredentialsApplication) getActivity().getApplication();
        addCredentialViewModel = ViewModelProviders.of(this, new CredentialFactory(credentialsApplication)).get(AddCredentialViewModel.class);
        if(credentialId != 0) {
            fragmentTitle = getResources().getString(R.string.edit_credential);
            addCredentialViewModel.getCredential(credentialId).observe(this, credential -> {
                try {
                    String secretKey = CryptoHelper.getSecretKey();
                    addCredentialViewModel.setCredentialCategoryId(credential.getCategoryId());
                    addCredentialViewModel.setCredentialId(credential.getId());
                    editTextName.setText(credential.getName());
                    editTextEmail.setText(credential.getEmail());
                    editTextUsername.setText(DecryptHelper.decrypt(credential.getUsername(), secretKey));
                    editTextPassword.setText(DecryptHelper.decrypt(credential.getPassword(), secretKey));
                    editTextComment.setText(credential.getComment());
                    editTextURL.setText(credential.getURL());
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (UnrecoverableEntryException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        getActivity().setTitle(fragmentTitle);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupClickListeners() {
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!addCredentialViewModel.validateCredentalName(s.toString())) {
                    editTextName.setError(getResources().getString(R.string.required));
                }
                buttonAddCredential.setEnabled(addCredentialViewModel.checkValidation());
            }
        });
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addCredentialViewModel.setCredentialUsername(s.toString());
            }
        });
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!addCredentialViewModel.validateCredentalEmail(s.toString())) {
                    editTextEmail.setError(getResources().getString(R.string.email_format));
                }
                buttonAddCredential.setEnabled(addCredentialViewModel.checkValidation());
            }
        });
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addCredentialViewModel.setCredentialPassword(s.toString());
            }
        });
        editTextComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addCredentialViewModel.setCredentialComment(s.toString());
            }
        });
        editTextURL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!addCredentialViewModel.validateCredentialURL(s.toString())) {
                    editTextURL.setError(getResources().getString(R.string.url_format));
                }
                buttonAddCredential.setEnabled(addCredentialViewModel.checkValidation());
            }
        });
        buttonAddCredential.setOnClickListener((View v) -> {

            if(credentialId == 0) {
                try {
                    addCredentialViewModel.addCredential(categoryId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getFragmentManager().popBackStack();
            } else {
                try {
                    addCredentialViewModel.updateCredential();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        buttonCancelCredential.setOnClickListener((View v) -> {
            getFragmentManager().popBackStack();
        });
    }

    private void setupViews(View view) {
        buttonAddCredential = (Button) view.findViewById(R.id.button_add);
        buttonCancelCredential = (Button) view.findViewById(R.id.button_cancel);
        editTextName = (EditText) view.findViewById(R.id.edit_text_name);
        editTextUsername = (EditText) view.findViewById(R.id.edit_text_username);
        editTextEmail = (EditText) view.findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) view.findViewById(R.id.edit_text_password);
        editTextComment = (EditText) view.findViewById(R.id.edit_text_comment);
        editTextURL = (EditText) view.findViewById(R.id.edit_text_url);
    }

}
