package com.example.trailermate.TrailerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.trailermate.R;
import com.example.trailermate.Utils.UtilMethods;
import com.example.trailermate.activity.PlayVideo;
import java.util.ArrayList;

public class Play_Video_Adapter extends RecyclerView.Adapter<com.example.trailermate.TrailerAdapter.Play_Video_Adapter.holder> {
    Context context;
    ArrayList<String> myList = new ArrayList<>();
    ArrayList<String> myListTitle = new ArrayList<>();
    ArrayList<String> myListVideoId = new ArrayList<>();
    ArrayList<String> myListoverview = new ArrayList<>();
    ;

    public Play_Video_Adapter(Context context, ArrayList<String> myList, ArrayList<String> myListTitle, ArrayList<String> myListVideoId, ArrayList<String> myListoverview) {
        this.context = context;
        this.myList = myList;
        this.myListTitle = myListTitle;
        this.myListVideoId = myListVideoId;
        this.myListoverview = myListoverview;
    }

    @NonNull
    @Override
    public com.example.trailermate.TrailerAdapter.Play_Video_Adapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_upcomingmovies, parent, false);
        return new com.example.trailermate.TrailerAdapter.Play_Video_Adapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.trailermate.TrailerAdapter.Play_Video_Adapter.holder holder, int position) {
        holder.movieTitle.setText(myListTitle.get(position));
        if(UtilMethods.INSTANCE.isDarkModeOn(context).equals("true")){
            holder.movieTitle.setTextColor(Color.WHITE);
            holder.play_btn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_play_circle_outline_240));
        }else {
            holder.movieTitle.setTextColor(Color.BLACK);
            holder.play_btn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_play_circle_outline_24));
        }

        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + myList.get(position)).into(holder.movieimg);
        holder.movieTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayVideo.class);
                intent.putExtra("VideoId", myListVideoId.get(position));
                intent.putExtra("orginalTitle", myListTitle.get(position));
                intent.putExtra("overview", myListoverview.get(position));
                intent.putExtra("maincatimage", myList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public static class holder extends RecyclerView.ViewHolder {
        ImageView movieimg, play_btn;
        TextView movieTitle;

        public holder(@NonNull View itemView) {
            super(itemView);
            movieimg = itemView.findViewById(R.id.movieimg);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            play_btn = itemView.findViewById(R.id.play_btn);
        }
    }
}


