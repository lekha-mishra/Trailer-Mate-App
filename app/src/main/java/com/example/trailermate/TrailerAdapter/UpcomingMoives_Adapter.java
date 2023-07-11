package com.example.trailermate.TrailerAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.trailermate.DashBoadNew.DashboadmainActivity;
import com.example.trailermate.ModelsTrailer.TrendingResultModel;
import com.example.trailermate.ModelsTrailer.YoutubeMainModel;
import com.example.trailermate.R;
import com.example.trailermate.Utils.TrailerUtils;
import com.example.trailermate.Utils.UtilMethods;
import com.example.trailermate.activity.PlayVideo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UpcomingMoives_Adapter extends RecyclerView.Adapter<com.example.trailermate.TrailerAdapter.UpcomingMoives_Adapter.holder> {

    Context context;

    private List<TrendingResultModel> trendingResultModels;
    String orginalTitle;
    String overview;
    String search;
    String VideoId,maincatimage,maincatimage1;
    String  apikey = "AIzaSyCOMdEECzY8cMddROuoGly6WjKYQ8VyRnk";
    ApiInterfaceYoutube apiInterfaceYoutube;
    ProgressDialog progressDialog;

//    Context context;
//    int[] images;
//    String[] title;


//    public Interior_Adapter(Context context, int[] images, String[] title) {
//        this.context = context;
//        this.images = images;
//        this.title = title;
//
//    }


    public UpcomingMoives_Adapter(Context context, List<TrendingResultModel> trendingResultModels) {
        this.context = context;
        this.trendingResultModels = trendingResultModels;

        Retrofit retrofit = com.example.trailermate.Api.ApiClientYoutube.getclient();
        apiInterfaceYoutube = retrofit.create(com.example.trailermate.Api.ApiInterfaceYoutube.class);
        progressDialog = new ProgressDialog(context);
    }

    @NonNull
    @Override
    public com.example.trailermate.TrailerAdapter.UpcomingMoives_Adapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_upcomingmovies, parent, false);
        return new UpcomingMoives_Adapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingMoives_Adapter.holder holder, int position) {

        Log.d("chekposition","position is"+ position);


        TrendingResultModel model =trendingResultModels.get(position);
//        SubCat_Item_Model_api model =subCat_item_model_apis.get(position);
//      String   sublinks=  model.getLinks().getSub_categories();
//        Log.d("sublinks","  sublinks are " +  sublinks);
        String name =model.getBackdrop_path();
        if(UtilMethods.INSTANCE.isDarkModeOn(context).equals("true")){
            holder.movieTitle.setTextColor(Color.WHITE);
            holder.play_btn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_play_circle_outline_240));
        }else {
            holder.movieTitle.setTextColor(Color.BLACK);
            holder.play_btn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_play_circle_outline_24));
        }

         maincatimage=model.getPoster_path();
         holder.movieTitle.setText(model.getOriginal_title());


        //  holder.product_title.setText(model.getId());

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/"+maincatimage)
                .placeholder(R.drawable.placeimg)
                .into(holder.movieimg);

//        Glide.with(context).load(images[position]).into(holder.product_iamge);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maincatimage1=model.getPoster_path();
                orginalTitle=model.getOriginal_title();
                overview=model.getOverview();
                if (orginalTitle==null){
                    orginalTitle=model.getOriginal_name();
                }
                Log.d("testtitle"," :" + orginalTitle + ", ");
                getVideoId();
//                context.startActivity(new Intent(context, Interior_Categories_Activity.class));

//                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();


               /* Intent intent = new Intent(context, Activity_Subcategories_api.class);
                Bundle bundle = new Bundle();
                bundle.putString("named",model+"");
                bundle.putString("slug",slug);
                intent.putExtras(bundle);

              context.startActivity(intent);
*/


//                context.startActivity(new Intent(context, Activity_Subcategories_api.class));
            }
        });
    }

    @Override
    public int getItemCount(){ return  trendingResultModels.size();
    }

    public static class holder extends RecyclerView.ViewHolder {

        ImageView movieimg,play_btn;
        TextView  movieTitle;
        public holder(@NonNull View itemView) {
            super(itemView);
            movieimg = itemView.findViewById(R.id.movieimg);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            play_btn = itemView.findViewById(R.id.play_btn);


        }
    }




    public void getVideoId() {

        Log.d("apihitsssd","api responce");

        TrailerUtils.showDialog(progressDialog);
        apiInterfaceYoutube.fetchYouTubeId(apikey,orginalTitle).enqueue(new Callback<YoutubeMainModel>() {
            @Override
            public void onResponse(Call<YoutubeMainModel> call, Response<YoutubeMainModel> response) {



                try {
                    if (response != null) {
                        Log.d("apihit","api responce");

                        Log.d("apihitsssdres","api responce: "+ response);


                        if (response.code()==200) {
                            VideoId = response.body().getItems().get(0).getId().getVideoId();
                            TrailerUtils.exitDialog(progressDialog);
                            Log.d("apihits","api responce");
                            Intent intent = new Intent(context, PlayVideo.class);
                            intent.putExtra("VideoId",VideoId);
                            intent.putExtra("orginalTitle",orginalTitle);
                            intent.putExtra("overview",overview);
                            intent.putExtra("maincatimage",maincatimage1);
                            context.startActivity(intent);


                        } else {

                            Log.d("apihitss","api responce");
                            TrailerUtils.exitDialog(progressDialog);
                        }

                    }

                } catch (Exception e) {
                    Log.e("exp", e.getLocalizedMessage());

                    Log.d("apihitsss","api responce");
                    TrailerUtils.exitDialog(progressDialog);
                }
            }

            @Override
            public void onFailure(Call<YoutubeMainModel> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                TrailerUtils.exitDialog(progressDialog);
                Log.d("apihitssss","api responce" +t.getMessage());
            }
        });

//
    }


}

