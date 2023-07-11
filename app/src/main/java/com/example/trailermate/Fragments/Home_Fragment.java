package com.example.trailermate.Fragments;
import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.trailermate.Api.ApiInterfaceTrailer;
import com.example.trailermate.Api.ApiInterfaceYoutube;
import com.example.trailermate.DashBoadNew.DashboadmainActivity;
import com.example.trailermate.ModelsTrailer.TrendingMovieModel;
import com.example.trailermate.ModelsTrailer.TrendingResultModel;
import com.example.trailermate.R;
import com.example.trailermate.TrailerAdapter.DashboardAdapter;
import com.example.trailermate.TrailerAdapter.Treding_Movies_Adapter;
import com.example.trailermate.Utils.ApplicationConstant;
import com.example.trailermate.Utils.TrailerUtils;
import com.example.trailermate.Utils.UtilMethods;
import com.example.trailermate.activity.SettingActivity;
import com.example.trailermate.databinding.FragmentHomeBinding;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderView;

import java.nio.file.StandardWatchEventKinds;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Home_Fragment extends Fragment {
    Context context;
    ApiInterfaceTrailer apiInterfaceTrailer;
    ApiInterfaceYoutube apiInterfaceYoutube;
    Treding_Movies_Adapter treding_movies_adapter;
    DashboardAdapter dashboardAdapter;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Retrofit retrofit = com.example.trailermate.Api.ApiClient.getclient();
        Retrofit retrofityoutube = com.example.trailermate.Api.ApiClientYoutube.getclient();
        apiInterfaceTrailer = retrofit.create(com.example.trailermate.Api.ApiInterfaceTrailer.class);
        apiInterfaceYoutube = retrofityoutube.create(com.example.trailermate.Api.ApiInterfaceYoutube.class);
        progressDialog = new ProgressDialog(getActivity());
        gettrendingmovies();
        gettrendingtv();
        getpopularmovies();
        getupcomingmovies();
        gettopratedmovies();
        getdiscovermovies();

        Boolean b = true;
        Log.d("switch","test : " + b);
        String bool = String.valueOf(b) ;
        SharedPreferences.Editor editor =getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
        editor.putString("isSwitchOn",bool );
        editor.apply();

        if(UtilMethods.INSTANCE.isDarkModeOn(getActivity()).equals("true")){
         binding.mainlayout.setBackgroundColor(Color.BLACK);
         binding.recTrendingmovie.setBackgroundColor(Color.BLACK);
         binding.recTrendingtv.setBackgroundColor(Color.BLACK);
         binding.recUpcomingmovie.setBackgroundColor(Color.BLACK);
         binding.recPopular.setBackgroundColor(Color.BLACK);
         binding.recToprated.setBackgroundColor(Color.BLACK);
         binding.txttrendingmovie.setTextColor(Color.WHITE);
         binding.txttrendingtv.setTextColor(Color.WHITE);
         binding.txtpopular.setTextColor(Color.WHITE);
         binding.txtupcomingmovie.setTextColor(Color.WHITE);
         binding.txtTopRated.setTextColor(Color.WHITE);
         binding.txttrailermate.setTextColor(Color.WHITE);


        }else {
            binding.mainlayout.setBackgroundColor(Color.WHITE);
            binding.recTrendingmovie.setBackgroundColor(Color.WHITE);
            binding.recTrendingtv.setBackgroundColor(Color.WHITE);
            binding.recUpcomingmovie.setBackgroundColor(Color.WHITE);
            binding.recPopular.setBackgroundColor(Color.WHITE);
            binding.recToprated.setBackgroundColor(Color.WHITE);
            binding.txttrendingmovie.setTextColor(Color.BLACK);
            binding.txttrendingtv.setTextColor(Color.BLACK);
            binding.txtpopular.setTextColor(Color.BLACK);
            binding.txtupcomingmovie.setTextColor(Color.BLACK);
            binding.txtTopRated.setTextColor(Color.BLACK);
            binding.txttrailermate.setTextColor(Color.BLACK);

        }
      Log.d("switchtest","" +UtilMethods.INSTANCE.isDarkModeOn(getActivity()) );
        binding.notificationicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DashboadmainActivity.class);
                startActivity(intent);
            }
        });
        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of LoginFragment");
        super.onResume();
        if(UtilMethods.INSTANCE.isDarkModeOn(getActivity()).equals("true")){
            binding.mainlayout.setBackgroundColor(Color.BLACK);
            binding.recTrendingmovie.setBackgroundColor(Color.BLACK);
            binding.recTrendingtv.setBackgroundColor(Color.BLACK);
            binding.recUpcomingmovie.setBackgroundColor(Color.BLACK);
            binding.recPopular.setBackgroundColor(Color.BLACK);
            binding.recToprated.setBackgroundColor(Color.BLACK);
            binding.txttrendingmovie.setTextColor(Color.WHITE);
            binding.txttrendingtv.setTextColor(Color.WHITE);
            binding.txtpopular.setTextColor(Color.WHITE);
            binding.txtupcomingmovie.setTextColor(Color.WHITE);
            binding.txtTopRated.setTextColor(Color.WHITE);
            binding.settingicon.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_settings_24));
            binding.txttrailermate.setTextColor(Color.WHITE);

        }else {
            binding.mainlayout.setBackgroundColor(Color.WHITE);
            binding.recTrendingmovie.setBackgroundColor(Color.WHITE);
            binding.recTrendingtv.setBackgroundColor(Color.WHITE);
            binding.recUpcomingmovie.setBackgroundColor(Color.WHITE);
            binding.recPopular.setBackgroundColor(Color.WHITE);
            binding.recToprated.setBackgroundColor(Color.WHITE);
            binding.txttrendingmovie.setTextColor(Color.BLACK);
            binding.txttrendingtv.setTextColor(Color.BLACK);
            binding.txtpopular.setTextColor(Color.BLACK);
            binding.txtupcomingmovie.setTextColor(Color.BLACK);
            binding.txtTopRated.setTextColor(Color.BLACK);
            binding.settingicon.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_settings_240));
            binding.txttrailermate.setTextColor(Color.BLACK);

        }
    }

    private void setadapter(List<TrendingResultModel> trendingResultModels) {
        treding_movies_adapter = new com.example.trailermate.TrailerAdapter.Treding_Movies_Adapter(getActivity(), trendingResultModels, false);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.recTrendingmovie.setLayoutManager(linearLayoutManager);
        binding.recTrendingmovie.setAdapter(treding_movies_adapter);
    }

    private void setadaptertrendingtv(List<TrendingResultModel> trendingResultModels) {
        treding_movies_adapter = new com.example.trailermate.TrailerAdapter.Treding_Movies_Adapter(getActivity(), trendingResultModels, false);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.recTrendingtv.setLayoutManager(linearLayoutManager);
        binding.recTrendingtv.setAdapter(treding_movies_adapter);
    }

    private void setadapterpopularmovies(List<TrendingResultModel> trendingResultModels) {
        treding_movies_adapter = new com.example.trailermate.TrailerAdapter.Treding_Movies_Adapter(getActivity(), trendingResultModels, false);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.recPopular.setLayoutManager(linearLayoutManager);
        binding.recPopular.setAdapter(treding_movies_adapter);
    }

    private void setadapterupcomingmovies(List<TrendingResultModel> trendingResultModels) {
        treding_movies_adapter = new com.example.trailermate.TrailerAdapter.Treding_Movies_Adapter(getActivity(), trendingResultModels, false);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.recUpcomingmovie.setLayoutManager(linearLayoutManager);
        binding.recUpcomingmovie.setAdapter(treding_movies_adapter);
    }

    private void setadaptertoprated(List<TrendingResultModel> trendingResultModels) {
        treding_movies_adapter = new com.example.trailermate.TrailerAdapter.Treding_Movies_Adapter(getActivity(), trendingResultModels, false);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.recToprated.setLayoutManager(linearLayoutManager);
        binding.recToprated.setAdapter(treding_movies_adapter);


    }

    private void setadapterdiscovermovies(List<TrendingResultModel> trendingResultModels) {
        dashboardAdapter = new com.example.trailermate.TrailerAdapter.DashboardAdapter(getActivity(), trendingResultModels);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.homerec.setLayoutManager(linearLayoutManager);
        binding.homerec.setAdapter(dashboardAdapter);
    }

    public void getdiscovermovies() {
        Log.d("apihitsssd", "api responce");
        TrailerUtils.showDialog(progressDialog);
        apiInterfaceTrailer.fetchtdiscovermovies(ApplicationConstant.INSTANCE.apikey).enqueue(new Callback<TrendingMovieModel>() {
            @Override
            public void onResponse(Call<TrendingMovieModel> call, Response<TrendingMovieModel> response) {
                try {
                    if (response != null) {
                        Log.d("apihit", "api responce");
                        Log.d("apihitsssdres", "api responce: " + response);
                        if (response.body().getPage() == 1) {
                            setadapterdiscovermovies(response.body().getResults());
                            TrailerUtils.exitDialog(progressDialog);
                            Log.d("apihits", "api responce");
                        } else {
                            Toast.makeText(getActivity(), response.body().getPage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<TrendingMovieModel> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                TrailerUtils.exitDialog(progressDialog);
                Log.d("apihitssss", "api responce" + t.getMessage());
            }
        });

//
    }


    public void gettrendingmovies() {
        Log.d("apihitsssd", "api responce");
        TrailerUtils.showDialog(progressDialog);
        apiInterfaceTrailer.fetchtrendingmovies(ApplicationConstant.INSTANCE.apikey).enqueue(new Callback<TrendingMovieModel>() {
            @Override
            public void onResponse(Call<TrendingMovieModel> call, Response<TrendingMovieModel> response) {
                try {
                    if (response != null) {
                        Log.d("apihit", "api responce");
                        Log.d("apihitsssdres", "api responce: " + response);
                        if (response.body().getPage() == 1) {
                            setadapter(response.body().getResults());
                            TrailerUtils.exitDialog(progressDialog);
                            Log.d("apihits", "api responce");
                        } else {
                            Toast.makeText(getActivity(), response.body().getPage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<TrendingMovieModel> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                TrailerUtils.exitDialog(progressDialog);
                Log.d("apihitssss", "api responce" + t.getMessage());
            }
        });

    }

    public void gettrendingtv() {
        Log.d("apihitsssd", "api responce");
        TrailerUtils.showDialog(progressDialog);
        apiInterfaceTrailer.fetchtrendingtv(ApplicationConstant.INSTANCE.apikey).enqueue(new Callback<TrendingMovieModel>() {
            @Override
            public void onResponse(Call<TrendingMovieModel> call, Response<TrendingMovieModel> response) {
                try {
                    if (response != null) {
                        Log.d("apihit", "api responce");
                        Log.d("apihitsssdres", "api responce: " + response);
                        if (response.body().getPage() == 1) {
                            setadaptertrendingtv(response.body().getResults());
                            TrailerUtils.exitDialog(progressDialog);
                            Log.d("apihits", "api responce");
                        } else {
                            Toast.makeText(getActivity(), response.body().getPage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<TrendingMovieModel> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                TrailerUtils.exitDialog(progressDialog);
                Log.d("apihitssss", "api responce" + t.getMessage());
            }
        });

//
    }


    public void getpopularmovies() {
        Log.d("apihitsssd", "api responce");
        TrailerUtils.showDialog(progressDialog);
        apiInterfaceTrailer.fetchpopularmovies(ApplicationConstant.INSTANCE.apikey).enqueue(new Callback<TrendingMovieModel>() {
            @Override
            public void onResponse(Call<TrendingMovieModel> call, Response<TrendingMovieModel> response) {
                try {
                    if (response != null) {
                        Log.d("apihit", "api responce");
                        Log.d("apihitsssdres", "api responce: " + response);
                        if (response.body().getPage() == 1) {
                            setadapterpopularmovies(response.body().getResults());
                            TrailerUtils.exitDialog(progressDialog);
                            Log.d("apihits", "api responce");
                        } else {
                            Toast.makeText(getActivity(), response.body().getPage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<TrendingMovieModel> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                TrailerUtils.exitDialog(progressDialog);
                Log.d("apihitssss", "api responce" + t.getMessage());
            }
        });

//
    }


    public void getupcomingmovies() {
        Log.d("apihitsssd", "api responce");
        TrailerUtils.showDialog(progressDialog);
        apiInterfaceTrailer.fetchtupcomingmovies(ApplicationConstant.INSTANCE.apikey).enqueue(new Callback<TrendingMovieModel>() {
            @Override
            public void onResponse(Call<TrendingMovieModel> call, Response<TrendingMovieModel> response) {
                try {
                    if (response != null) {
                        Log.d("apihit", "api responce");
                        Log.d("apihitsssdres", "api responce: " + response);
                        if (response.body().getPage() == 1) {
                            setadapterupcomingmovies(response.body().getResults());
                            TrailerUtils.exitDialog(progressDialog);
                            Log.d("apihits", "api responce");
                        } else {
                            Toast.makeText(getActivity(), response.body().getPage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<TrendingMovieModel> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                TrailerUtils.exitDialog(progressDialog);
                Log.d("apihitssss", "api responce" + t.getMessage());
            }
        });

//
    }


    public void gettopratedmovies() {
        Log.d("apihitsssd", "api responce");
        TrailerUtils.showDialog(progressDialog);
        apiInterfaceTrailer.fetchtopratedmovies(ApplicationConstant.INSTANCE.apikey).enqueue(new Callback<TrendingMovieModel>() {
            @Override
            public void onResponse(Call<TrendingMovieModel> call, Response<TrendingMovieModel> response) {
                try {
                    if (response != null) {
                        Log.d("apihit", "api responce");
                        Log.d("apihitsssdres", "api responce: " + response);
                        if (response.body().getPage() == 1) {
                            setadaptertoprated(response.body().getResults());
                            TrailerUtils.exitDialog(progressDialog);
                            Log.d("apihits", "api responce");
                        } else {
                            Toast.makeText(getActivity(), response.body().getPage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<TrendingMovieModel> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                TrailerUtils.exitDialog(progressDialog);
                Log.d("apihitssss", "api responce" + t.getMessage());
            }
        });
//
    }


    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}