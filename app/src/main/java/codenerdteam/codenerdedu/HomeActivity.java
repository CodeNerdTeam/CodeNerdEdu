package codenerdteam.codenerdedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import codenerdteam.codenerdedu.databinding.ActivityHomeBinding;
import codenerdteam.codenerdedu.fragment.FavoriteFragment;
import codenerdteam.codenerdedu.fragment.HistoryFragment;
import codenerdteam.codenerdedu.fragment.HomeFragment;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // ViewBinding
    private ActivityHomeBinding binding;

    private FirebaseAuth firebaseAuth;

    private DrawerLayout drawer;

    private TextView textView;

    // Fragment
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_FAVOURITE = 1;
    private static final int FRAGMENT_HISTORY = 2;

    // Khai báo biến để check xem Fragment hiện tại, gán luôn ban đầu là FRAGMENT_HOME
    private int mCurrentFragment = FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Bắt sự kiện khi select đến item của navigation
        binding.navigationView.setNavigationItemSelectedListener(this);

//        // Khi đăng nhập vào app mặc định là vào "HomeFragment"
//        replaceFragment(new HomeFragment());
//        // Set luôn cho "nav_home" đang được select
//        binding.navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        // Ẩn thanh ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Khởi tạo firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        // Khi click vào "Các thể loại", gọi sự kiện OnClickListener
        binding.categoryLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến Category
                startActivity(new Intent(HomeActivity.this, CategoryActivity.class));
            }
        });

//        // Đăng xuất user bằng OnClickListener
//        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                firebaseAuth.signOut();
//                checkUser();
//            }
//        });
    }

    private void checkUser() {
        // Kiểm tra user chưa đăng nhập thì chuyển qua UserLogin

        // Lấy user hiện tại
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            // User chưa đăng nhập
            startActivity(new Intent(this, UserLogin.class));
        }
        else {
            // User đã được đăng nhập rồi, lấy thông tin user
            String email = firebaseUser.getEmail();

            // set đến emailTv
            binding.emailTv.setText(email);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Kiểm tra khi chọn từng item trong navigation
        if (id == R.id.nav_home) {
            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        }
        else if (id == R.id.nav_favorite) {
            if (mCurrentFragment != FRAGMENT_FAVOURITE) {
                replaceFragment(new FavoriteFragment());
                mCurrentFragment = FRAGMENT_FAVOURITE;
            }
        }
        else if (id == R.id.nav_history) {

            if (mCurrentFragment != FRAGMENT_HISTORY) {
                replaceFragment(new HistoryFragment());
                mCurrentFragment = FRAGMENT_HISTORY;
            }
        }
        else if (id == R.id.nav_profile) {

        }
        else if (id == R.id.nav_changePassword) {

        }
        else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_rate) {

        }
        else if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            checkUser();
        }

        // Đóng ngăn kéo (drawer)
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public void onBackPressed() {
        // Nếu drawer đang mở thì khi nhấn nút back ở phone thì đóng drawer
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
        // Ngược lại thì thoát app
        else {
            super.onBackPressed();
        }
    }

    // Thay thế Fragment
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}