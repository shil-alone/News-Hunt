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

    ArrayList<Source> sourceList = new ArrayList<>();
    SourceItemClicked listener ;

    public SavedSourceAdapter(SourceItemClicked listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public SavedSourceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_source_item, parent, false);
        SavedSourceHolder sourceHolder = new SavedSourceHolder(view);
        Button btnVisit = sourceHolder.btnVisit;
        ImageView btnDeleteSource = sourceHolder.btnDeleteSource;
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

    @Override
    public void onBindViewHolder(@NonNull SavedSourceHolder holder, int position) {
        Source source = sourceList.get(position);
        holder.txtLanguage.setText(source.getLanguage());
        holder.txtCat.setText(source.getCategory());
        holder.txtDescription.setText(source.getDescription());
        holder.txtSourceTitle.setText(source.getName());
        holder.txtCon.setText(source.getCountry());
    }

    @Override
    public int getItemCount() {
        return sourceList.size();
    }

    public void updateSource(ArrayList<Source> updatedSource){
        sourceList.clear();
        sourceList.addAll(updatedSource);
        notifyDataSetChanged();
    }
}

class SavedSourceHolder extends RecyclerView.ViewHolder{

    TextView txtSourceTitle ,txtDescription , txtCon,txtCat,txtLanguage;
    Button btnVisit;
    ImageView btnDeleteSource ;
    public SavedSourceHolder(@NonNull View itemView) {
        super(itemView);
        txtSourceTitle = itemView.findViewById(R.id.txtSourceTitle);
        txtDescription = itemView.findViewById(R.id.txtDescription);
        txtCon = itemView.findViewById(R.id.txtCon);
        txtCat = itemView.findViewById(R.id.txtCat);
        txtLanguage = itemView.findViewById(R.id.txtLanguage);
        btnVisit = itemView.findViewById(R.id.btnVisit);
        btnDeleteSource = itemView.findViewById(R.id.btnDeleteSource);
    }
}
