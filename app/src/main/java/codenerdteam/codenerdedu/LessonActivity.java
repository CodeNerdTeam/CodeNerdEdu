package codenerdteam.codenerdedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import codenerdteam.codenerdedu.adapter.CourseListAdapter;
import codenerdteam.codenerdedu.model.ModelCourse;

public class LessonActivity extends AppCompatActivity {
    private FloatingActionButton addVideoBtn;

    private ArrayList<ModelCourse> videoArraylist;
    private CourseListAdapter adapterVideo;
    private RecyclerView videosRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        videosRv = findViewById(R.id.courseRv);
        addVideoBtn = findViewById(R.id.addVideoBtn);
        Intent intent = getIntent();

        addVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LessonActivity.this, AddCourseActivity.class));
            }
        });

        loadCourseList();
    }

    private void loadCourseList() {
        videoArraylist = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(AddCourseActivity.COURSES);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ModelCourse modelVideo = ds.getValue(ModelCourse.class);
                    videoArraylist.add(modelVideo);
                }
                adapterVideo = new CourseListAdapter(LessonActivity.this, videoArraylist);
                videosRv.setAdapter(adapterVideo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}