package codenerdteam.codenerdedu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import codenerdteam.codenerdedu.CategoryActivity;
import codenerdteam.codenerdedu.R;
import codenerdteam.codenerdedu.UserLogin;

public class HomeFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    TextView tv1, tv2;

    LinearLayout linearLayout;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        tv1 = (TextView) rootView.findViewById(R.id.emailTv);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.categoryLl);

        // Khởi tạo firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        // Khi click vào "Các thể loại", gọi sự kiện OnClickListener
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến Category
                startActivity(new Intent(getActivity(), CategoryActivity.class));
            }
        });

        return rootView;
    }

    private void checkUser() {
        // Kiểm tra user chưa đăng nhập thì chuyển qua UserLogin

        // Lấy user hiện tại
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            // User chưa đăng nhập
            startActivity(new Intent(getActivity(), UserLogin.class));
        }
        else {
            // User đã được đăng nhập rồi, lấy thông tin user
            String email = firebaseUser.getEmail();

            // set đến emailTv
            tv1.setText(email);
        }
    }

}


