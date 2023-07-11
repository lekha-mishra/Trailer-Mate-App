package com.example.trailermate.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.trailermate.DashBoadNew.DashboadmainActivity;
import com.example.trailermate.R;
import com.example.trailermate.Utils.ApplicationConstant;
import com.example.trailermate.Utils.UtilMethods;
import com.google.gson.Gson;

public class SettingActivity extends AppCompatActivity {
       Switch aSwitch;
       LinearLayout linearsetting;
       TextView home,invitefriend,rateus,darkmode,logout,nav_name,nav_email;
       ImageView  homearrow,invitefreindarrow,rateusarrow,logoutarrow;
       View viewbelowhome,usernameview,viewbelowfriend,viewbelowrateus,viewbelowdark,viewbelowlogout;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        aSwitch =findViewById(R.id.switch1);
        home =findViewById(R.id.home);
        invitefriend =findViewById(R.id.invitefriend);
        rateus =findViewById(R.id.rateus);
        darkmode =findViewById(R.id.darkmode);
        logout =findViewById(R.id.logout);
        nav_name =findViewById(R.id.nav_name);
        nav_email =findViewById(R.id.nav_email);
        linearsetting =findViewById(R.id.linearsetting);
        homearrow =findViewById(R.id.homearrow);
        invitefreindarrow =findViewById(R.id.invitefreindarrow);
        rateusarrow =findViewById(R.id.rateusarrow);
        logoutarrow =findViewById(R.id.logoutarrow);
        viewbelowhome =findViewById(R.id.viewbelowhome);
        usernameview =findViewById(R.id.usernameview);
        viewbelowfriend =findViewById(R.id.viewbelowfriend);
        viewbelowrateus =findViewById(R.id.viewbelowrateus);
        viewbelowdark =findViewById(R.id.viewbelowdark);
        viewbelowlogout =findViewById(R.id.viewbelowlogout);

        // Get the original drawable from the TextView
        Drawable originalDrawable = home.getCompoundDrawables()[0]; // Assuming it's the left drawable
        // Create a new drawable with the desired tint color
        Drawable tintedDrawable = DrawableCompat.wrap(originalDrawable);
        DrawableCompat.setTint(tintedDrawable, getResources().getColor(R.color.white));
        // Set the tinted drawable back to the TextView
        home.setCompoundDrawablesWithIntrinsicBounds(tintedDrawable, null, null, null);
        invitefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                String sharebody = "Download Trailer Mate App   Now Click this link :-                                                            https://play.google.com/store/apps/details?id=com.example.trailermate";
                sendIntent.putExtra(Intent.EXTRA_TEXT,sharebody);
                startActivity(Intent.createChooser(sendIntent , "Choose Your Account : "));
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SettingActivity.this, DashboadmainActivity.class);
                startActivity(intent);
            }
        });

        if (UtilMethods.INSTANCE.isDarkModeOn(this).equals("true")){
            aSwitch.setChecked(true);
            linearsetting.setBackgroundColor(Color.BLACK);
            home.setTextColor(Color.WHITE);
            invitefriend.setTextColor(Color.WHITE);
            rateus.setTextColor(Color.WHITE);
            darkmode.setTextColor(Color.WHITE);
            logout.setTextColor(Color.WHITE);
            nav_name.setTextColor(Color.WHITE);
            nav_email.setTextColor(Color.WHITE);
            aSwitch.getThumbDrawable().setTint(ContextCompat.getColor(this,R.color.white));
            aSwitch.getTrackDrawable().setTint(ContextCompat.getColor(this, R.color.Background));
            homearrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_chevron_right_24w));
            invitefreindarrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_chevron_right_24w));
            rateusarrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_chevron_right_24w));
            logoutarrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_chevron_right_24w));
            viewbelowhome.setBackgroundColor(Color.GRAY);
            usernameview.setBackgroundColor(Color.GRAY);
            viewbelowfriend.setBackgroundColor(Color.GRAY);
            viewbelowrateus.setBackgroundColor(Color.GRAY);
            viewbelowdark.setBackgroundColor(Color.GRAY);
            viewbelowlogout.setBackgroundColor(Color.GRAY);

        }else{
            aSwitch.setChecked(false);
            linearsetting.setBackgroundColor(Color.WHITE);
            home.setTextColor(Color.BLACK);
            invitefriend.setTextColor(Color.BLACK);
            rateus.setTextColor(Color.BLACK);
            darkmode.setTextColor(Color.BLACK);
            logout.setTextColor(Color.BLACK);
            nav_name.setTextColor(Color.BLACK);
            nav_email.setTextColor(Color.BLACK);
            aSwitch.getThumbDrawable().setTint(ContextCompat.getColor(this,R.color.black));
            aSwitch.getTrackDrawable().setTint(ContextCompat.getColor(this,R.color.sprintkart));
            homearrow.setImageDrawable(getResources().getDrawable(R.drawable.right_arrow));
            invitefreindarrow.setImageDrawable(getResources().getDrawable(R.drawable.right_arrow));
            rateusarrow.setImageDrawable(getResources().getDrawable(R.drawable.right_arrow));
            logoutarrow.setImageDrawable(getResources().getDrawable(R.drawable.right_arrow));
            viewbelowhome.setBackgroundColor(Color.LTGRAY);
            usernameview.setBackgroundColor(Color.LTGRAY);
            viewbelowfriend.setBackgroundColor(Color.LTGRAY);
            viewbelowrateus.setBackgroundColor(Color.LTGRAY);
            viewbelowdark.setBackgroundColor(Color.LTGRAY);
            viewbelowlogout.setBackgroundColor(Color.LTGRAY);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("switch","test : " + b);
                String bool = String.valueOf(b) ;
                SharedPreferences.Editor editor = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
                editor.putString("isSwitchOn",bool );
                editor.apply();
               recreate();

            }
        });

    }
}