package com.example.trailermate.DashBoadNew;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.trailermate.Fragments.Download_Fragment;
import com.example.trailermate.Fragments.Home_Fragment;
import com.example.trailermate.Fragments.TopSearch_Fragment;
import com.example.trailermate.Fragments.Upcoming_Fragment;
import com.example.trailermate.R;
import com.example.trailermate.Utils.ApplicationConstant;
import com.example.trailermate.Utils.UtilMethods;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import retrofit2.Retrofit;
public class DashboadmainActivity extends AppCompatActivity {
    TextView txt_mycart, txt_mywishlist, home, cartvalue;
    ImageView notificationicon;
    ProgressDialog progressDialog;
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboadmain);
        Retrofit retrofit = com.example.trailermate.Api.ApiClient.getclient();
        progressDialog = new ProgressDialog(this);
        txt_mycart = findViewById(R.id.txt_mycart);
        notificationicon = findViewById(R.id.notificationicon);
        home = findViewById(R.id.home);
        bnv = findViewById(R.id.bottomnavigation);
        String a = "0";
        a = getIntent().getStringExtra("orderplace");



        if (a == "1") {
            bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    setMyFragment(new Home_Fragment());
                    return true;
                }
            });


        } else {

            setMyFragment(new Home_Fragment());
            bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.menu_home:
                            Home_Fragment homeFragment1 = new Home_Fragment();
                            replaceMyFragment(homeFragment1);
                            break;
                        case R.id.menu_upcoming:
                            Upcoming_Fragment upcomingFragment = new Upcoming_Fragment();
                            replaceMyFragment(upcomingFragment);
                            break;
                        case R.id.menu_topsearch:
                            TopSearch_Fragment topSearchFragment = new TopSearch_Fragment();
                            replaceMyFragment(topSearchFragment);
                            break;
                        case R.id.menu_download:
                            Download_Fragment downloadFragment = new Download_Fragment();
                            replaceMyFragment(downloadFragment);
                            break;
                    }
                    return true;
                }
            });


        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UtilMethods.INSTANCE.isDarkModeOn(DashboadmainActivity.this).equals("true")){

            bnv.setBackgroundColor(Color.BLACK);




        }else {
            bnv.setBackgroundColor(Color.WHITE);


        }
    }

    private void setMyFragment(Fragment fragment) {
        //get current fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //get fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //set new fragment in fragment_container (FrameLayout)
        fragmentTransaction.add(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }

    private void replaceMyFragment(Fragment fragment) {
        //get current fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //get fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //set new fragment in fragment_container (FrameLayout)
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }


}