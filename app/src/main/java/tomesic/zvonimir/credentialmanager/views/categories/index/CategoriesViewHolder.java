package tomesic.zvonimir.credentialmanager.views.categories.index;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import tomesic.zvonimir.credentialmanager.R;

class CategoriesViewHolder extends RecyclerView.ViewHolder {

    TextView categoryTextView;
    ImageButton deleteButton;

    CategoriesViewHolder(View v) {
        super(v);
        categoryTextView = (TextView) v.findViewById(R.id.text_view_category_name);
        deleteButton = (ImageButton) v.findViewById(R.id.button_delete);
    }
}
