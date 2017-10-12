package tomesic.zvonimir.credentialmanager.views.categories.index;

import android.app.AlertDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
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
import tomesic.zvonimir.credentialmanager.models.Category;
import tomesic.zvonimir.credentialmanager.views.categories.aoe.AddCategoryFragment;
import tomesic.zvonimir.credentialmanager.views.credentials.index.CredentialsFragment;

public class CategoriesFragment extends LifecycleFragment {

    private static final String TAG = "CredentialsFragment";
    private CategoriesAdapter adapter;
    private CategoriesViewModel categoriesViewModel;

    private View.OnClickListener deleteClickListener = v -> {
        Category category = (Category) v.getTag();
        new AlertDialog.Builder(getContext())
            .setTitle(getResources().getString(R.string.confirmation))
            .setMessage(getResources().getString(R.string.are_u_sure))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    categoriesViewModel.deleteCategory(category);
                }})
            .setNegativeButton(getResources().getString(R.string.no), null).show();
    };

    private View.OnClickListener itemClickListener = v -> {
        Category category = (Category) v.getTag();

        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", category.getId());
        LifecycleFragment fragment = new CredentialsFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_dashboard, fragment);
        ft.addToBackStack(null);
        ft.commit();
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.categories_fragment));
        View v = inflater.inflate(R.layout.fragment_list_categories, container, false);
        setupRecyclerView(v);

        FloatingActionButton floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fab_add);
        floatingActionButton.setOnClickListener(v1 -> {
            LifecycleFragment someFragment = new AddCategoryFragment();
            android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_dashboard, someFragment );
            transaction.addToBackStack(null);
            transaction.commit();
        });

        CredentialsApplication application = (CredentialsApplication) getActivity().getApplication();
        categoriesViewModel = ViewModelProviders.of(this, new CredentialFactory(application)).get(CategoriesViewModel.class);


        categoriesViewModel.getCategories().observe(this, categories -> {
            Log.d(TAG, "Categories Changed:" + categories);
            adapter.setCategories(categories);
        });
        return v;
    }

    private void setupRecyclerView(View v) {
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_list_categories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CategoriesAdapter(new ArrayList<>(), getContext(), itemClickListener, deleteClickListener);
        recyclerView.setAdapter(adapter);
        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}
