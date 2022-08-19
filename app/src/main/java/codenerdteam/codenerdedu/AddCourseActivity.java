package codenerdteam.codenerdedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String COURSES = "courses";

    private String[] courses = {"Web", "Frontend", "Backend", "Database", "Android", "Machine Learning", "UI/UX"};

    private Spinner spinner;

    private EditText editText_1, editText_2;

    private CardView cardView;

    private String courseCategory = "";

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        spinner =findViewById(R.id.courseSpinner);
        spinner.setOnItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.please_wait);
        progressDialog.setMessage("Video đang tải lên");
        progressDialog.setCanceledOnTouchOutside(false);

        editText_1 = findViewById(R.id.courseTitleEt);
        editText_2 = findViewById(R.id.totalVideoEt);

        cardView = findViewById(R.id.proceedCartView);

        ArrayAdapter adapter =new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateData();
            }
        });

    }

    private void ValidateData() {
        String title = editText_1.getText().toString();
        String totalLessons = editText_2.getText().toString();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(AddCourseActivity.this, "Chưa nhập tiêu đề khoá học", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(totalLessons)) {
            Toast.makeText(AddCourseActivity.this, "Chưa nhập số lượng bài học", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            String timestamp = String.valueOf(System.currentTimeMillis());

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", firebaseAuth.getUid());
            hashMap.put("title", title);
            hashMap.put("timestamp", timestamp);
            hashMap.put("totalLessons", totalLessons);
            hashMap.put("category", courseCategory);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(AddCourseActivity.COURSES);
            reference.child(title).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(AddCourseActivity.this, AddVideoActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("totalLessons", totalLessons);
                    intent.putExtra("category", courseCategory);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddCourseActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), courses[position], Toast.LENGTH_SHORT).show();
        courseCategory = courses[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}