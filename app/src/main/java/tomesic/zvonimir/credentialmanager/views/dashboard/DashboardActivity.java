package tomesic.zvonimir.credentialmanager.views.dashboard;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

import tomesic.zvonimir.credentialmanager.CredentialsApplication;
import tomesic.zvonimir.credentialmanager.R;
import tomesic.zvonimir.credentialmanager.helpers.CryptoHelper;
import tomesic.zvonimir.credentialmanager.injections.CredentialFactory;
import tomesic.zvonimir.credentialmanager.models.Category;
import tomesic.zvonimir.credentialmanager.views.authenticate.index.AuthenticateActivity;
import tomesic.zvonimir.credentialmanager.views.authenticate.login.LoginFragment;
import tomesic.zvonimir.credentialmanager.views.authenticate.login.LoginViewModel;
import tomesic.zvonimir.credentialmanager.views.categories.index.CategoriesFragment;
import tomesic.zvonimir.credentialmanager.views.credentials.index.CredentialsFragment;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DashboardViewModel dashboardViewModel;
    private int categoriesSize;

    public TextView textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();

        CredentialsApplication application = (CredentialsApplication) getApplication();
        dashboardViewModel = ViewModelProviders.of(this, new CredentialFactory(application)).get(DashboardViewModel.class);

        dashboardViewModel.getCategories().observeForever(categories -> {
            menu.removeGroup(R.id.group_categories);
            categoriesSize = categories.size();
            for (Category c : categories) {
                menu.add(R.id.group_categories, c.getId(), Menu.NONE, c.getName()).setIcon(R.drawable.ic_lock_black_24dp);;
            }
        });

        textPassword = findViewById(R.id.textViewPassword);

        CryptoHelper cryptoHelper = new CryptoHelper();

        String text = null;

        textPassword.setText(text);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            Intent intent = new Intent(this, AuthenticateActivity.class);
            CryptoHelper.clearKey();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

    private void displaySelectedActivity(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_categories:
                fragment = new CategoriesFragment();
                break;
            default:
                Bundle bundle = new Bundle();
                bundle.putInt("categoryId", id);
                fragment = new CredentialsFragment();
                fragment.setArguments(bundle);
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_dashboard, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedActivity(id);
        return true;
    }

}

