package codenerdteam.codenerdedu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import codenerdteam.codenerdedu.adapter.CourseAdapter;
import codenerdteam.codenerdedu.model.CourseModel;

public class ListCourseUIUX extends AppCompatActivity {
    private RecyclerView rcvCourses;

    private ActionBar actionBar;

    private CourseAdapter courseAdapter;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_course);

        // Cấu hình actionbar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Danh sách các khoá học");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rcvCourses = findViewById(R.id.rcv_courses);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvCourses.setLayoutManager(linearLayoutManager);
        courseAdapter = new CourseAdapter(getListUsers());
        rcvCourses.setAdapter(courseAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvCourses.addItemDecoration(itemDecoration);
    }

    private List<CourseModel> getListUsers(){
        List<CourseModel> list = new ArrayList<>();
        list.add(new CourseModel(R.drawable.adobe_xd,"Adobe XD","Số lượng bài học: 10"));
        list.add(new CourseModel(R.drawable.after_effect_icon,"After Effect","Số lượng bài học: 10"));
        list.add(new CourseModel(R.drawable.figma_icon,"Figma","Số lượng bài học: 12"));
        list.add(new CourseModel(R.drawable.photoshop_icon,"Photoshop","Số lượng bài học: 20"));
        list.add(new CourseModel(R.drawable.sketch_icon,"Sketch","Số lượng bài học: 16"));

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                courseAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                courseAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}