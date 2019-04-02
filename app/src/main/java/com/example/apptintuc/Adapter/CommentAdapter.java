package com.example.apptintuc.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apptintuc.Api.ApiService;
import com.example.apptintuc.GetDataBase.FromRepository;
import com.example.apptintuc.Object.BinhLuan;
import com.example.apptintuc.Object.User;
import com.example.apptintuc.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<BinhLuan> binhLuanList;
    private ApiService apiService;
    public CommentAdapter(Context context, List<BinhLuan> binhLuanList) {
        this.context = context;
        this.binhLuanList = binhLuanList;
        apiService = FromRepository.getApiService();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_commnet_recycler,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        BinhLuan binhLuan = binhLuanList.get(i);
        viewHolder.noiDung.setText(binhLuan.getNoidung());
        viewHolder.ngayBinhLuan.setText(binhLuan.getThoigian());
        apiService.LayUserTheoId("LayUserTheoId",binhLuan.getId_user()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                viewHolder.tenUser.setText(user.getHoten());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("kiemtra","Lỗi Lấy User " + t.getMessage().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return binhLuanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView noiDung,tenUser,ngayBinhLuan;
        private LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noiDung = itemView.findViewById(R.id.noidung);
            tenUser = itemView.findViewById(R.id.tenUser);
            ngayBinhLuan = itemView.findViewById(R.id.ngayBinhLuan);
            layout = itemView.findViewById(R.id.layout_itemcomment);
        }
    }
}
