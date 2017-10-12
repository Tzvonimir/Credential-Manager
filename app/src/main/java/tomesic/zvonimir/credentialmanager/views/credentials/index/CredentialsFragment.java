package tomesic.zvonimir.credentialmanager.views.credentials.index;

import android.app.AlertDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import tomesic.zvonimir.credentialmanager.CredentialsApplication;
import tomesic.zvonimir.credentialmanager.R;
import tomesic.zvonimir.credentialmanager.injections.CredentialFactory;
import tomesic.zvonimir.credentialmanager.models.Credential;
import tomesic.zvonimir.credentialmanager.views.credentials.aoe.AddCredentialFragment;

public class CredentialsFragment extends LifecycleFragment {

    private static final String TAG = "CredentialsFragment";
    private CredentialsAdapter adapter;
    private CredentialsViewModel credentialsViewModel;

    private View.OnClickListener deleteClickListener = v -> {
        Credential credential = (Credential) v.getTag();
        new AlertDialog.Builder(getContext())
            .setTitle(getResources().getString(R.string.confirmation))
            .setMessage(getResources().getString(R.string.are_u_sure))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    credentialsViewModel.deleteCredental(credential);
                }})
            .setNegativeButton(getResources().getString(R.string.no), null).show();
    };

    private View.OnClickListener itemClickListener = v -> {
        Credential credential = (Credential) v.getTag();

        LifecycleFragment credentialFragment = new AddCredentialFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("credentialId", credential.getId());
        credentialFragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_dashboard, credentialFragment );
        transaction.addToBackStack(null);
        transaction.commit();
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.credentials_fragment));
        View v = inflater.inflate(R.layout.fragment_list_credential, container, false);
        int categoryID = getArguments().getInt("categoryId");
        setupRecyclerView(v);

        FloatingActionButton floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fab_add);
        floatingActionButton.setOnClickListener(v1 -> {
            LifecycleFragment someFragment = new AddCredentialFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("categoryId", categoryID);
            someFragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_dashboard, someFragment );
            transaction.addToBackStack(null);
            transaction.commit();
        });

        CredentialsApplication application = (CredentialsApplication) getActivity().getApplication();
        credentialsViewModel = ViewModelProviders.of(this, new CredentialFactory(application)).get(CredentialsViewModel.class);

        credentialsViewModel.getCredentialsWithCategoryId(categoryID).observe(this, credentials -> {
            Log.d(TAG, "Credentials Changed:" + credentials);
            adapter.setCredentials(credentials);
        });

        return v;
    }

    private void setupRecyclerView(View v) {
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_list_credentials);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CredentialsAdapter(new ArrayList<>(), getContext(), itemClickListener, deleteClickListener);
        recyclerView.setAdapter(adapter);
        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}
