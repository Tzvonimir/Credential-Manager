package tomesic.zvonimir.credentialmanager.views.categories.aoe;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import tomesic.zvonimir.credentialmanager.CredentialsApplication;
import tomesic.zvonimir.credentialmanager.R;
import tomesic.zvonimir.credentialmanager.injections.CredentialFactory;
import tomesic.zvonimir.credentialmanager.views.categories.index.CategoriesFragment;

public class AddCategoryFragment extends LifecycleFragment {


    private EditText editTextName;
    private Button buttonAddCategory, buttonCancelCategory;
    private AddCategoryViewModel addCategoryViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.add_category));
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);

        setupViews(view);
        setupClickListeners();
        setupViewModel();
        return view;
    }

    private void setupViewModel() {
        CredentialsApplication credentialsApplication = (CredentialsApplication) getActivity().getApplication();
        addCategoryViewModel = ViewModelProviders.of(this, new CredentialFactory(credentialsApplication)).get(AddCategoryViewModel.class);
        editTextName.setText(addCategoryViewModel.getCategoryName());
    }

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
                if(addCategoryViewModel.validateCategoryName(s.toString())) {
                    buttonAddCategory.setEnabled(true);
                } else {
                    editTextName.setError(getResources().getString(R.string.required));
                    buttonAddCategory.setEnabled(false);
                }
            }
        });
        buttonAddCategory.setOnClickListener(v -> {
            addCategoryViewModel.addCategory();
            getFragmentManager().popBackStack();
        });
        buttonCancelCategory.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });
    }

    private void setupViews(View view) {
        buttonAddCategory = (Button) view.findViewById(R.id.button_add);
        buttonCancelCategory = (Button) view.findViewById(R.id.button_cancel);
        editTextName = (EditText) view.findViewById(R.id.edit_text_name);
    }

}
