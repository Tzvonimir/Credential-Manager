package tomesic.zvonimir.credentialmanager.views.credentials.index;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tomesic.zvonimir.credentialmanager.R;
import tomesic.zvonimir.credentialmanager.models.Category;
import tomesic.zvonimir.credentialmanager.models.Credential;

public class CredentialsAdapter extends RecyclerView.Adapter<CredentialsViewHolder> {
    private final Context context;
    private List<Credential> credentials;
    private View.OnClickListener deleteClickListener;
    private View.OnClickListener viewClickListener;

    CredentialsAdapter(List<Credential> credentials, Context context, View.OnClickListener viewClickListener, View.OnClickListener deleteClickListener) {
        this.credentials = credentials;
        this.context = context;
        this.deleteClickListener = deleteClickListener;
        this.viewClickListener = viewClickListener;
    }

    @Override
    public CredentialsViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_credential, parent, false);
        return new CredentialsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CredentialsViewHolder holder, int position) {
        Credential credential = credentials.get(position);
        holder.credentialTextView.setText(credential.getName());
        holder.itemView.setTag(credential);
        holder.deleteButton.setTag(credential);
        holder.deleteButton.setOnClickListener(deleteClickListener);
        holder.itemView.setOnClickListener(viewClickListener);
    }

    @Override
    public int getItemCount() {
        return credentials.size();
    }

    void setCredentials(List<Credential> credentials) {
        this.credentials = credentials;
        notifyDataSetChanged();
    }
}