package codenerdteam.codenerdedu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import codenerdteam.codenerdedu.PlayVideoActivity;
import codenerdteam.codenerdedu.R;
import codenerdteam.codenerdedu.model.ModelVideo;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.holder> {
    private Context context;
    private ArrayList<ModelVideo> videoArrayList = new ArrayList<>();

    public VideoListAdapter(Context context, ArrayList<ModelVideo> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public VideoListAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_video_list, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListAdapter.holder holder, int position) {
        ModelVideo video = videoArrayList.get(position);
        holder.txtTitle.setText(video.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayVideoActivity.class);
                intent.putExtra("title", video.getTitle());
                intent.putExtra("videoUrl", video.getVideoUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    class holder extends RecyclerView.ViewHolder {
        TextView txtTitle;

        public holder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.titleTxt);
        }
    }
}
