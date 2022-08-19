package codenerdteam.codenerdedu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import codenerdteam.codenerdedu.R;
import codenerdteam.codenerdedu.model.CourseModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class CourseAdapter extends  RecyclerView.Adapter<CourseAdapter.CourseViewHolder> implements Filterable {
    public CourseAdapter(List<CourseModel> mListCourses) {
        this.mListCourses = mListCourses;
        this.mListCourseOld = mListCourses;
    }

    private List<CourseModel> mListCourses;
    private  List<CourseModel> mListCourseOld;

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_course,parent,false);

        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseModel course = mListCourses.get(position);
        if (course == null){
            return;
        }
        holder.imgCourse.setImageResource(course.getImage());
        holder.tvTitle.setText(course.getTitle());
        holder.tvTotalLesson.setText(course.getTotalLesson());
    }

    @Override
    public int getItemCount() {
        if (mListCourses !=null){
            return mListCourses.size();
        }
        return 0;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView imgCourse;
        private TextView tvTitle;
        private TextView tvTotalLesson;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCourse = itemView.findViewById(R.id.img_course);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTotalLesson = itemView.findViewById(R.id.tv_totalLesson);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    mListCourses = mListCourseOld;

                }else {
                    List<CourseModel> list = new ArrayList<>();
                    for (CourseModel user : mListCourseOld){
                        if(user.getTitle().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(user);
                        }
                    }
                    mListCourses = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListCourses;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                mListCourses = (List<CourseModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
