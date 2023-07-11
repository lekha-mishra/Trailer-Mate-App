package com.example.trailermate.Fragments;
import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.trailermate.R;
import com.example.trailermate.TrailerAdapter.Play_Video_Adapter;
import com.example.trailermate.Utils.ApplicationConstant;
import com.example.trailermate.Utils.UtilMethods;
import com.example.trailermate.databinding.FragmentDownloadBinding;
import com.example.trailermate.databinding.FragmentUpcomingBinding;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;

public class Download_Fragment extends Fragment {
    RecyclerView rec_downloadedmovie;
    ArrayList<String> myList = new ArrayList<>();
    ArrayList<String> myListTitle = new ArrayList<>();
    ArrayList<String> myListVideoId = new ArrayList<>();
    ArrayList<String> myListoverview = new ArrayList<>();
    FragmentDownloadBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDownloadBinding.inflate(inflater, container, false);
        if(UtilMethods.INSTANCE.isDarkModeOn(getActivity()).equals("true")){

            binding.recDownloadedmovie.setBackgroundColor(Color.BLACK);
            binding.lineardownloaded.setBackgroundColor(Color.BLACK);
            binding.txtdownload.setTextColor(Color.WHITE);

        }else {
            binding.recDownloadedmovie.setBackgroundColor(Color.WHITE);
            binding.lineardownloaded.setBackgroundColor(Color.WHITE);
            binding.txtdownload.setTextColor(Color.BLACK);

        }
        showDownloadedMovies();
        return binding.getRoot();
    }


    private void showDownloadedMovies() {
        SharedPreferences shrd = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonText = shrd.getString("key", "null");
        String jsonTexttitle = shrd.getString("jsonTexttitle", "null");
        String jsonTextvideoid = shrd.getString("jsonTextvideoid", "null");
        String jsonTextoverview = shrd.getString("jsonTextoverview", "null");
        String[] text = gson.fromJson(jsonText, String[].class);
        String[] texttitle = gson.fromJson(jsonTexttitle, String[].class);
        String[] textvideoid = gson.fromJson(jsonTextvideoid, String[].class);
        String[] textoverview = gson.fromJson(jsonTextoverview, String[].class);
        myList = new ArrayList<String>(Arrays.asList(text));
        myListTitle = new ArrayList<String>(Arrays.asList(texttitle));
        myListVideoId = new ArrayList<String>(Arrays.asList(textvideoid));
        myListoverview = new ArrayList<String>(Arrays.asList(textoverview));
        Log.d("myListDownload", "list: " + myList + " , " + myList);
        Log.d("myListTitle", "title: " + myListTitle);
        Log.d("myListVideoId", "title: " + myListVideoId);
        Log.d("myListoverview", "title: " + myListoverview);
        Play_Video_Adapter adapter2 = new Play_Video_Adapter(getActivity(), myList, myListTitle, myListVideoId, myListoverview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recDownloadedmovie.setLayoutManager(linearLayoutManager);
        binding.recDownloadedmovie.setAdapter(adapter2);

    }
}