package tomesic.zvonimir.credentialmanager.views.credentials.index;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import tomesic.zvonimir.credentialmanager.R;

class CredentialsViewHolder extends RecyclerView.ViewHolder {

    TextView credentialTextView;
    ImageButton deleteButton;

    CredentialsViewHolder(View v) {
        super(v);
        credentialTextView = (TextView) v.findViewById(R.id.text_view_credential_name);
        deleteButton = (ImageButton) v.findViewById(R.id.button_delete);
    }
}
