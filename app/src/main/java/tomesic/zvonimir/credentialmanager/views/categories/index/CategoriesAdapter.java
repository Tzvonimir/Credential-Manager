package tomesic.zvonimir.credentialmanager.views.categories.index;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tomesic.zvonimir.credentialmanager.R;
import tomesic.zvonimir.credentialmanager.models.Category;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {
    private final Context context;
    private List<Category> categories;
    private View.OnClickListener deleteClickListener;
    private View.OnClickListener viewClickListener;

    CategoriesAdapter(List<Category> categories, Context context, View.OnClickListener viewClickListener, View.OnClickListener deleteClickListener) {
        this.categories = categories;
        this.context = context;
        this.deleteClickListener = deleteClickListener;
        this.viewClickListener = viewClickListener;
    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_category, parent, false);
        return new CategoriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryTextView.setText(category.getName());
        holder.itemView.setTag(category);
        holder.deleteButton.setTag(category);
        holder.deleteButton.setOnClickListener(deleteClickListener);
        holder.itemView.setOnClickListener(viewClickListener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }
}