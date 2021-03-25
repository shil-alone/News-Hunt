package com.codershil.newshunt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.codershil.newshunt.R;
import com.codershil.newshunt.interfaces.SourceItemClicked;
import com.codershil.newshunt.models.Source;
import java.util.ArrayList;

public class SavedSourceAdapter extends RecyclerView.Adapter<SavedSourceHolder> {

    /**
     * listener : SourceItemClicked interface object
     * sourceList : list for saving news objects
     */
    ArrayList<Source> sourceList = new ArrayList<>();
    SourceItemClicked listener ;

    // constructor
    public SavedSourceAdapter(SourceItemClicked listener) {
        this.listener = listener;
    }

    // called when view is created
    @NonNull
    @Override
    public SavedSourceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // converting the saved_source_item layout into the view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_source_item, parent, false);
        SavedSourceHolder sourceHolder = new SavedSourceHolder(view);
        // initializing the views
        Button btnVisit = sourceHolder.btnVisit;
        ImageView btnDeleteSource = sourceHolder.btnDeleteSource;

        // setting up onClickListeners on source, btnVisit and btnDelete
        btnVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sourceItemClicked(sourceList.get(sourceHolder.getAdapterPosition()));
            }
        });
        btnDeleteSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sourceDeleteButtonClicked(sourceList.get(sourceHolder.getAdapterPosition()),sourceHolder.getAdapterPosition());
            }
        });
        return sourceHolder;
    }

    // this method is called when viewHolder is bind on recyclerview
    @Override
    public void onBindViewHolder(@NonNull SavedSourceHolder holder, int position) {
        // loading all the data in the source eg title,description,etc
        Source source = sourceList.get(position);
        holder.txtLanguage.setText(source.getLanguage());
        holder.txtCat.setText(source.getCategory());
        holder.txtDescription.setText(source.getDescription());
        holder.txtSourceTitle.setText(source.getName());
        holder.txtCon.setText(source.getCountry());
    }

    // return the no. of items in the recyclerview
    @Override
    public int getItemCount() {
        return sourceList.size();
    }

    // this is the method for passing the data to the adapter
    public void updateSource(ArrayList<Source> updatedSource){
        sourceList.clear();
        sourceList.addAll(updatedSource);
        notifyDataSetChanged();
    }
}

class SavedSourceHolder extends RecyclerView.ViewHolder{

    // declaring views
    TextView txtSourceTitle ,txtDescription , txtCon,txtCat,txtLanguage;
    Button btnVisit;
    ImageView btnDeleteSource ;
    public SavedSourceHolder(@NonNull View itemView) {
        super(itemView);
        // initializing the views
        txtSourceTitle = itemView.findViewById(R.id.txtSourceTitle);
        txtDescription = itemView.findViewById(R.id.txtDescription);
        txtCon = itemView.findViewById(R.id.txtCon);
        txtCat = itemView.findViewById(R.id.txtCat);
        txtLanguage = itemView.findViewById(R.id.txtLanguage);
        btnVisit = itemView.findViewById(R.id.btnVisit);
        btnDeleteSource = itemView.findViewById(R.id.btnDeleteSource);
    }
}
