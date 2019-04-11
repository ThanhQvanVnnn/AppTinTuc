package com.example.apptintuc.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apptintuc.Api.ApiService;
import com.example.apptintuc.DetailArticle;
import com.example.apptintuc.GetDataBase.FromRepository;
import com.example.apptintuc.GetDataBase.TinTucViewModel;
import com.example.apptintuc.Object.BinhLuan;
import com.example.apptintuc.Object.TinTuc;
import com.example.apptintuc.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LuuTinAdapter extends RecyclerView.Adapter<LuuTinAdapter.ViewHolder> {
    private Context context;
    private List<TinTuc> tinTucs;
    private ApiService apiService;
    private List<BinhLuan> binhLuanList;
    private TinTucViewModel tinTucViewModel;

    public LuuTinAdapter(Context context, List<TinTuc> tinTucs,TinTucViewModel tinTucViewModel){
        this.context = context;
        this.tinTucs = tinTucs;
        apiService = FromRepository.getApiService();
        this.tinTucViewModel = tinTucViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.luu_tin_layout, viewGroup, false);
        return new LuuTinAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final TinTuc tinTuc = tinTucs.get(i);
        final int[] numBinhLuan = {0};
        apiService.getBinhLuanTheoMaTin("LayDanhSachBinhLuanTheoMa", tinTuc.getId_tin() + "").enqueue(new Callback<List<BinhLuan>>() {
            @Override
            public void onResponse(Call<List<BinhLuan>> call, Response<List<BinhLuan>> response) {
                binhLuanList = new ArrayList<>();
                binhLuanList.addAll(response.body());
                if (binhLuanList.size() != 0) {
                    numBinhLuan[0] = binhLuanList.size();
                    viewHolder.iconcomment.setVisibility(View.VISIBLE);
                    viewHolder.numbercomment.setText(numBinhLuan[0] + "");
                }
            }

            @Override
            public void onFailure(Call<List<BinhLuan>> call, Throwable t) {
                Log.d("Kiemtra", "Lỗi " + t.getMessage().toString());
            }
        });

        Picasso.get().load(tinTuc.getImg()).into(viewHolder.image);
        if (tinTuc.getTieude().length() > 65) {
            viewHolder.title.setText(tinTuc.getTieude().substring(0, 65) + "...");
        } else {
            viewHolder.title.setText(tinTuc.getTieude());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        long time = 0;
        try {
            time = sdf.parse(tinTuc.getNgaydangtin()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long now = System.currentTimeMillis();

        CharSequence ago =
                DateUtils.getRelativeTimeSpanString(time, now, DateUtils.HOUR_IN_MILLIS);
        viewHolder.time.setText(ago);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(context, DetailArticle.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idNews", tinTuc.getId_tin());
                bundle.putString("Content",tinTuc.getNoidung());
                bundle.putString("Tieude", tinTuc.getTieude());
                bundle.putInt("SizeBinhLuan", numBinhLuan[0]);
                detail.putExtras(bundle);
                context.startActivity(detail);
            }
        });
        viewHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xóa tin")
                        .setMessage("Bạn có chắc xóa tin này?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               tinTucViewModel.detete_tintuc(tinTuc.getId_tin());
                               notifyDataSetChanged();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return tinTucs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView time;
        public TextView numbercomment;
        public ImageView iconcomment;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_item);
            time = itemView.findViewById(R.id.time_ago);
            title = itemView.findViewById(R.id.title_article);
            numbercomment = itemView.findViewById(R.id.numbercomment);
            iconcomment = itemView.findViewById(R.id.iconcomment);
            linearLayout = itemView.findViewById(R.id.layout_news);

        }
    }
}
