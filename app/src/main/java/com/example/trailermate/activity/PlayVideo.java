package com.example.trailermate.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.trailermate.R;
import com.example.trailermate.Utils.ApplicationConstant;
import com.example.trailermate.Utils.UtilMethods;
import com.example.trailermate.Utils.YouTubeDownloader;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PlayVideo extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_REQUEST = 1;
    // private YouTubePlayerView youTubeView;
    private YouTubePlayerSupportFragment youTubeView;
    private YouTubePlayer player;
    String video = "";
    String Title = "";
    String overview = "";
    String maincatimage = "";
    String[] maincatimage1 = {};
    TextView tvVideoTitle, titileVideo, videodescription, downloadYoutube;
    RecyclerView recyclerView;
    ArrayList<String> myList = new ArrayList<>();
    ArrayList<String> myListvideo;
    ArrayList<String> myListtitle;
    ArrayList<String> myListoverview;
    LinearLayout  linearplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        // Set toolbar icon in ...
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_black);
        toolBar.setTitle("Back");
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvVideoTitle = (TextView) findViewById(R.id.tv_video_title);
        downloadYoutube = (TextView) findViewById(R.id.downloadYoutube);
        titileVideo = (TextView) findViewById(R.id.titileVideo);
        videodescription = (TextView) findViewById(R.id.videodescription);
        linearplay =(LinearLayout)findViewById(R.id.linearplay);

        video = getIntent().getStringExtra("VideoId");
        Title = getIntent().getStringExtra("orginalTitle");
        overview = getIntent().getStringExtra("overview");
        maincatimage = getIntent().getStringExtra("maincatimage");

        Log.e("videovvvvvv", "As   :  " + video);
        // video=  "MPPwJcCpjaA";
        //   Title= "Home.videoTitle";

        tvVideoTitle.setText(Title);
        titileVideo.setText(Title);
        videodescription.setText(overview);
        if(UtilMethods.INSTANCE.isDarkModeOn(PlayVideo.this).equals("true")){
            titileVideo.setTextColor(Color.WHITE);
            videodescription.setTextColor(Color.WHITE);
            linearplay.setBackgroundColor(Color.BLACK);
        }else {
            titileVideo.setTextColor(Color.BLACK);
            videodescription.setTextColor(Color.BLACK);
            linearplay.setBackgroundColor(Color.WHITE);

        }
        // youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_view);
        youTubeView.initialize(ApplicationConstant.INSTANCE.YOUTUBE_API_KEY, this);

               String[] image = {"/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg"};
               String[] image1 = {"The Idol"};
               String[] image2 = {"xVYUKxF0wMc"};
               String[] image3 = {"Framed in the 1940s for the double murder of his wife and her lover,upstanding banker Andy Dufresne begins a new life at the Shawshank prison"};

               myList  = new ArrayList<String>(Arrays.asList(image));
               myListtitle  = new ArrayList<String>(Arrays.asList(image1));
               myListvideo  = new ArrayList<String>(Arrays.asList(image2));
               myListoverview  = new ArrayList<String>(Arrays.asList(image3));

        //   dataParsetopvideo(response);
        SharedPreferences shrd = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonText = shrd.getString("key", "null");
        if(jsonText == null){
            Log.d("testingnull"," null  hai" + jsonText.toString());
        }else{
            Log.d("testingnullelse"," null nhi hai" +jsonText.toString());
        }

//        String jsonTexttitle = shrd.getString("jsonTexttitle", "null");
//        String jsonTextvideoid = shrd.getString("jsonTextvideoid", "null");
//        String jsonTextoverview = shrd.getString("jsonTextoverview", "null");
//        String[] text = gson.fromJson(jsonText, String[].class);
//        String[] texttitle = gson.fromJson(jsonTexttitle, String[].class);
//        String[] textvideoid = gson.fromJson(jsonTextvideoid, String[].class);
//        String[] textoverview = gson.fromJson(jsonTextoverview, String[].class);
//        //   Log.d("myList1","list : " + myList);
//        myList = new ArrayList<String>(Arrays.asList(text));
//        myListtitle = new ArrayList<String>(Arrays.asList(texttitle));
//        myListvideo = new ArrayList<String>(Arrays.asList(textvideoid));
//        myListoverview = new ArrayList<String>(Arrays.asList(textoverview));
//        Log.d("myList1", "list : " + myList);


        downloadYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] image = {};
                String[] videoid = {};
                String[] title = {};
                // myList  = new ArrayList<String>(Arrays.asList(image));
                myList.add(maincatimage);
                //    myList.add(maincatimage);
                myListtitle.add(Title);
                myListvideo.add(video);
                myListoverview.add(overview);
//                    myListtitle()
                Log.d("myList", "list : " + myList);
                Log.d("myListtitle", "list : " + myListtitle);
                Log.d("myListvideo", "list : " + myListvideo);
                Log.d("myListoverview", "list : " + myListoverview);
                SharedPreferences.Editor editor = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
                Gson gson = new Gson();
                String jsonText = gson.toJson(myList);
                String jsonTexttitle = gson.toJson(myListtitle);
                String jsonTextvideoid = gson.toJson(myListvideo);
                String jsonTextoverview = gson.toJson(myListoverview);
                editor.putString("key", jsonText);
                editor.putString("jsonTexttitle", jsonTexttitle);
                editor.putString("jsonTextvideoid", jsonTextvideoid);
                editor.putString("jsonTextoverview", jsonTextoverview);
                editor.apply();
            }
        });

    }





    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        this.player = player;

        if (!wasRestored) {

//            Log.v("video",video);
//            player.cueVideo(video.replace("?t=6",""));

            // Log.d("video",video);
            // player.cueVideo("riWWhqsO63U");
            player.cueVideo(video);
            player.play();

        }
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(ApplicationConstant.INSTANCE.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

}
