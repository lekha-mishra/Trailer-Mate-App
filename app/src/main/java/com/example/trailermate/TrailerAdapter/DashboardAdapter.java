package com.example.trailermate.TrailerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.trailermate.Api.ApiInterfaceYoutube;
import com.example.trailermate.ModelsTrailer.TrendingResultModel;
import com.example.trailermate.ModelsTrailer.YoutubeMainModel;
import com.example.trailermate.R;
import com.example.trailermate.Utils.ApplicationConstant;
import com.example.trailermate.Utils.TrailerUtils;
import com.example.trailermate.activity.PlayVideo;
import com.google.gson.Gson;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DashboardAdapter extends RecyclerView.Adapter<com.example.trailermate.TrailerAdapter.DashboardAdapter.holder> {
    Context context;
    private List<TrendingResultModel> trendingResultModels;
    String orginalTitle;
    String overview;
    String search;
    String VideoId, maincatimage, maincatimage1;
    ApiInterfaceYoutube apiInterfaceYoutube;
    ProgressDialog progressDialog;

    public DashboardAdapter(Context context, List<TrendingResultModel> trendingResultModels) {
        this.context = context;
        this.trendingResultModels = trendingResultModels;
        Retrofit retrofit = com.example.trailermate.Api.ApiClientYoutube.getclient();
        apiInterfaceYoutube = retrofit.create(com.example.trailermate.Api.ApiInterfaceYoutube.class);
        progressDialog = new ProgressDialog(context);
    }

    @NonNull
    @Override
    public com.example.trailermate.TrailerAdapter.DashboardAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_movie_item, parent, false);
        return new DashboardAdapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.holder holder, int position) {
        Log.d("chekposition", "position is" + position);
        TrendingResultModel model = trendingResultModels.get(position);
        maincatimage = model.getPoster_path();
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + maincatimage)
                .placeholder(R.drawable.placeimg)
                .into(holder.poster_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maincatimage1 = model.getPoster_path();
                orginalTitle = model.getOriginal_title();
                overview = model.getOverview();
                if (orginalTitle == null) {
                    orginalTitle = model.getOriginal_name();
                }
                Log.d("testtitle", " :" + orginalTitle + ", ");
                getVideoId();
            }
        });
    }

    @Override
    public int getItemCount() {
        return trendingResultModels.size();
    }

    public static class holder extends RecyclerView.ViewHolder {
        ImageView poster_image;
        TextView btnplay, btndownload;
        public holder(@NonNull View itemView) {
            super(itemView);
            poster_image = itemView.findViewById(R.id.imghome);
            btnplay = itemView.findViewById(R.id.btnplay);
            btndownload = itemView.findViewById(R.id.btndownload);


        }
    }


    public void getVideoId() {
        Log.d("apihitsssd", "api responce");
        TrailerUtils.showDialog(progressDialog);
        apiInterfaceYoutube.fetchYouTubeId(ApplicationConstant.INSTANCE.YOUTUBE_API_KEY, orginalTitle).enqueue(new Callback<YoutubeMainModel>() {
            @Override
            public void onResponse(Call<YoutubeMainModel> call, Response<YoutubeMainModel> response) {
                try {
                    if (response != null) {
                        Log.d("apihit", "api responce");
                        Log.d("apihitsssdres", "api responce: " + response);
                        if (response.code() == 200) {
                            VideoId = response.body().getItems().get(0).getId().getVideoId();
                            TrailerUtils.exitDialog(progressDialog);
                            Log.d("apihits", "api responce");
                            Intent intent = new Intent(context, PlayVideo.class);
                            intent.putExtra("VideoId", VideoId);
                            intent.putExtra("orginalTitle", orginalTitle);
                            intent.putExtra("overview", overview);
                            intent.putExtra("maincatimage", maincatimage1);
                            context.startActivity(intent);
                        } else {
                            Log.d("apihitss", "api responce");
                            TrailerUtils.exitDialog(progressDialog);
                        }
                    }

                } catch (Exception e) {
                    Log.e("exp", e.getLocalizedMessage());
                    Log.d("apihitsss", "api responce");
                    TrailerUtils.exitDialog(progressDialog);
                }
            }

            @Override
            public void onFailure(Call<YoutubeMainModel> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                TrailerUtils.exitDialog(progressDialog);
                Log.d("apihitssss", "api responce" + t.getMessage());
            }
        });

//
    }
}


