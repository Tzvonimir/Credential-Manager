package tomesic.zvonimir.credentialmanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tomesic.zvonimir.credentialmanager.views.authenticate.index.AuthenticateActivity;
import tomesic.zvonimir.credentialmanager.views.dashboard.DashboardActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, AuthenticateActivity.class);
        startActivity(intent);
        finish();
    }
}