package com.example.trailermate.Fragments;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.trailermate.Api.ApiInterfaceTrailer;
import com.example.trailermate.ModelsTrailer.TrendingMovieModel;
import com.example.trailermate.ModelsTrailer.TrendingResultModel;
import com.example.trailermate.R;
import com.example.trailermate.TrailerAdapter.Treding_Movies_Adapter;
import com.example.trailermate.TrailerAdapter.UpcomingMoives_Adapter;
import com.example.trailermate.Utils.ApplicationConstant;
import com.example.trailermate.Utils.TrailerUtils;
import com.example.trailermate.Utils.UtilMethods;
import com.example.trailermate.databinding.FragmentTopSearchBinding;
import com.example.trailermate.databinding.FragmentUpcomingBinding;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TopSearch_Fragment extends Fragment {
    ApiInterfaceTrailer apiInterfaceTrailer;
    ProgressDialog progressDialog;
    Context context;
    Treding_Movies_Adapter treding_movies_adapter;
    UpcomingMoives_Adapter upcomingMoives_adapter;
    RecyclerView rec_upcomingmovie;
    FragmentTopSearchBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTopSearchBinding.inflate(inflater, container, false);
        progressDialog = new ProgressDialog(getActivity());
        Retrofit retrofit = com.example.trailermate.Api.ApiClient.getclient();
        apiInterfaceTrailer = retrofit.create(com.example.trailermate.Api.ApiInterfaceTrailer.class);
        if(UtilMethods.INSTANCE.isDarkModeOn(getActivity()).equals("true")){

            binding.recSearchedmovies.setBackgroundColor(Color.BLACK);
            binding.recUpcomingmovies.setBackgroundColor(Color.BLACK);
            binding.linearsearch.setBackgroundColor(Color.BLACK);
            binding.txttopsearch.setTextColor(Color.WHITE);

        }else {
            binding.recSearchedmovies.setBackgroundColor(Color.WHITE);
            binding.recUpcomingmovies.setBackgroundColor(Color.WHITE);
            binding.linearsearch.setBackgroundColor(Color.WHITE);
            binding.txttopsearch.setTextColor(Color.BLACK);

        }
        getupcomingmovies();
        binding.searchhit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllSeachedMovies(ApplicationConstant.INSTANCE.apikey,binding.searchtext.getText().toString());
            }
        });


        binding.searchtext.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {


            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                getAllSeachedMovies(ApplicationConstant.INSTANCE.apikey,binding.searchtext.getText().toString());
            }
        });

        return binding.getRoot();
    }

    private void setproductsdata(List<TrendingResultModel> trendingResultModels) {
        treding_movies_adapter = new Treding_Movies_Adapter(getActivity(), trendingResultModels,true);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),3 , GridLayoutManager.VERTICAL, false);
        binding.recSearchedmovies.setLayoutManager(manager);
        binding.recSearchedmovies.setAdapter(treding_movies_adapter);
    }
    public void getAllSeachedMovies(String catid,String Search) {
      //  TrailerUtils.showDialog(progressDialog);
        Log.d("catid" , "catid are"  +catid);
        apiInterfaceTrailer.fetchsearchedmovies(catid,Search).enqueue(new Callback<TrendingMovieModel>() {
            @Override
            public void onResponse(Call<TrendingMovieModel> call, Response<TrendingMovieModel> response) {
                try {
                    if (response != null) {
                        Log.e("apihit","api responce: "+ response.body().getPage());
                        if (response.body().getPage()==1) {
                            setproductsdata(response.body().getResults());
                            binding.recUpcomingmovies.setVisibility(View.GONE);
                          //  TrailerUtils.exitDialog(progressDialog);
                        } else {
                         //   TrailerUtils.exitDialog(progressDialog);
                            Toast.makeText(getActivity(), response.body().getPage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("exp", e.getLocalizedMessage());
                  //  TrailerUtils.exitDialog(progressDialog);
                }
            }
            @Override
            public void onFailure(Call<TrendingMovieModel> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });

//
    }

    private void setadapterpopularmovies(List<TrendingResultModel> trendingResultModels) {
        upcomingMoives_adapter = new com.example.trailermate.TrailerAdapter.UpcomingMoives_Adapter(getActivity(), trendingResultModels);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recUpcomingmovies.setLayoutManager(linearLayoutManager);
        binding.recUpcomingmovies.setAdapter(upcomingMoives_adapter);
    }

    public void getupcomingmovies() {
        Log.d("apihitsssd","api responce");
        TrailerUtils.showDialog(progressDialog);
        apiInterfaceTrailer.fetchpopularmovies(ApplicationConstant.INSTANCE.apikey).enqueue(new Callback<TrendingMovieModel>() {
            @Override
            public void onResponse(Call<TrendingMovieModel> call, Response<TrendingMovieModel> response) {
                try {
                    if (response != null) {
                        Log.d("apihit","api responce");
                        Log.d("apihitsssdres","api responce: "+ response);
                        if (response.body().getPage()==1) {
                            setadapterpopularmovies(response.body().getResults());
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