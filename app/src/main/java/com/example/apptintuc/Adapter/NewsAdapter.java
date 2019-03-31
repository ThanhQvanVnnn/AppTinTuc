package com.example.apptintuc.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptintuc.Object.ISO8601Parse;
import com.example.apptintuc.Object.TinTuc;
import com.example.apptintuc.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<TinTuc> tinTucs;

    public NewsAdapter(Context context, List<TinTuc> tinTucs) {
        this.context = context;
        this.tinTucs = tinTucs;
        tinTucs.remove(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news_recycler,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TinTuc tinTuc = tinTucs.get(i);
        Picasso.get().load(tinTuc.getImg()).into(viewHolder.image);
        viewHolder.title.setText(tinTuc.getTieude());
        Date date = null;
        try {
            date = ISO8601Parse.parse(tinTuc.getNgaydangtin());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
//        viewHolder.time.setReferenceTime(date.getTime());
    }

    @Override
    public int getItemCount() {
        return tinTucs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    private ImageView image;
    private TextView title;
    private RelativeTimeTextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_item);
            time = itemView.findViewById(R.id.time_ago);
            title = itemView.findViewById(R.id.title_article);
        }
    }
}
