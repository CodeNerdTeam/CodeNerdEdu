package codenerdteam.codenerdedu.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import codenerdteam.codenerdedu.AddCourseActivity;
import codenerdteam.codenerdedu.PlayCourseActivity;
import codenerdteam.codenerdedu.R;
import codenerdteam.codenerdedu.model.ModelCourse;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.viewHolder> {
    private Context context;
    private ArrayList<ModelCourse> videoArrayList;

    public CourseListAdapter(Context context, ArrayList<ModelCourse> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public CourseListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_course_list, parent, false);
        return new CourseListAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListAdapter.viewHolder holder, int position) {
        ModelCourse modelVideo = videoArrayList.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(modelVideo.getTimestamp()));
        String formattedDate= DateFormat.format("dd/MM/yyyy", calendar).toString();

        holder.txtTitle.setText(modelVideo.getTitle());
        holder.txtTime.setText(formattedDate);
        holder.txtTotalLessons.setText(modelVideo.getTotalLessons() + " bài học");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayCourseActivity.class);
                intent.putExtra("courseTitle", modelVideo.getTitle());
                context.startActivity(intent);
            }
        });

        holder.deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xoá")
                        .setMessage("Bạn có chắc chắn muốn xóa khóa học không?")
                        .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteVideo(modelVideo);
                            }
                        }).setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void deleteVideo(ModelCourse modelVideo) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(AddCourseActivity.COURSES);
        databaseReference.child(modelVideo.title)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Khóa học đã được xóa thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtTime, txtTotalLessons;
        ImageView deleteIv;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.titleVid);
            txtTime = itemView.findViewById(R.id.timeTv);
            txtTotalLessons = itemView.findViewById(R.id.totalLessonsTv);

            deleteIv = itemView.findViewById(R.id.deleteIcon);
        }
    }
}
