package com.example.trailermate.Fragments;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.trailermate.Api.ApiInterfaceTrailer;
import com.example.trailermate.DashBoadNew.DashboadmainActivity;
import com.example.trailermate.ModelsTrailer.TrendingMovieModel;
import com.example.trailermate.ModelsTrailer.TrendingResultModel;
import com.example.trailermate.R;
import com.example.trailermate.TrailerAdapter.Treding_Movies_Adapter;
import com.example.trailermate.TrailerAdapter.UpcomingMoives_Adapter;
import com.example.trailermate.Utils.ApplicationConstant;
import com.example.trailermate.Utils.TrailerUtils;
import com.example.trailermate.Utils.UtilMethods;
import com.example.trailermate.databinding.FragmentHomeBinding;
import com.example.trailermate.databinding.FragmentUpcomingBinding;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Upcoming_Fragment extends Fragment {
    ApiInterfaceTrailer apiInterfaceTrailer;
    ProgressDialog progressDialog;
    Context context;
    UpcomingMoives_Adapter upcomingMoives_adapter;
    RecyclerView rec_upcomingmovie;
    FragmentUpcomingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUpcomingBinding.inflate(inflater, container, false);
        progressDialog = new ProgressDialog(getActivity());
        Retrofit retrofit = com.example.trailermate.Api.ApiClient.getclient();
        apiInterfaceTrailer = retrofit.create(com.example.trailermate.Api.ApiInterfaceTrailer.class);
        if(UtilMethods.INSTANCE.isDarkModeOn(getActivity()).equals("true")){

            binding.recUpcomingmovie.setBackgroundColor(Color.BLACK);
            binding.linearupcoming.setBackgroundColor(Color.BLACK);
            binding.txtupcoming.setTextColor(Color.WHITE);

        }else {
            binding.recUpcomingmovie.setBackgroundColor(Color.WHITE);
            binding.linearupcoming.setBackgroundColor(Color.WHITE);
            binding.txtupcoming.setTextColor(Color.BLACK);

        }
        getupcomingmovies();
       return binding.getRoot();
    }


    private void setadapterupcomingmovies(List<TrendingResultModel> trendingResultModels) {
        upcomingMoives_adapter = new com.example.trailermate.TrailerAdapter.UpcomingMoives_Adapter(getActivity(), trendingResultModels);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recUpcomingmovie.setLayoutManager(linearLayoutManager);
        binding.recUpcomingmovie.setAdapter(upcomingMoives_adapter);
    }

    public void getupcomingmovies() {
        Log.d("apihitsssd","api responce");
        TrailerUtils.showDialog(progressDialog);
        apiInterfaceTrailer.fetchtupcomingmovies(ApplicationConstant.INSTANCE.apikey).enqueue(new Callback<TrendingMovieModel>() {
            @Override
            public void onResponse(Call<TrendingMovieModel> call, Response<TrendingMovieModel> response) {
                try {
                    if (response != null) {
                        Log.d("apihit","api responce");
                        Log.d("apihitsssdres","api responce: "+ response);
                        if (response.body().getPage()==1) {
                            setadapterupcomingmovies(response.body().getResults());
                            TrailerUtils.exitDialog(progressDialog);
                            Log.d("apihits","api responce");
                        } else {
                            Toast.makeText(getActivity(), response.body().getPage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<TrendingMovieModel> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                TrailerUtils.exitDialog(progressDialog);
                Log.d("apihitssss","api responce" +t.getMessage());
            }
        });

//
    }
}