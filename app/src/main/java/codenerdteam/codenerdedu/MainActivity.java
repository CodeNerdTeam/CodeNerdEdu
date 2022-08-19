package codenerdteam.codenerdedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import codenerdteam.codenerdedu.databinding.ActivityMainBinding;
import codenerdteam.codenerdedu.fragment.FavoriteFragment;
import codenerdteam.codenerdedu.fragment.HistoryFragment;
import codenerdteam.codenerdedu.fragment.HomeFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    // ViewBinding
    private ActivityMainBinding binding;

    private FirebaseAuth firebaseAuth;

    private FirebaseUser currentUser;

    private DrawerLayout drawer;

    private TextView tv1, tv2;

    // Fragment
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_FAVOURITE = 2;
    private static final int FRAGMENT_HISTORY = 3;

    // Khai báo biến để check xem Fragment hiện tại, gán luôn ban đầu là FRAGMENT_HOME
    private int mCurrentFragment = FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        drawer = findViewById(R.id.drawer_layout);

        // Khởi tạo firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // Bắt sự kiện khi select đến item của navigation
        binding.navigationView.setNavigationItemSelectedListener(this);

//        // Khi đăng nhập vào app mặc định là vào "HomeFragment"
        replaceFragment(new HomeFragment());
        // Set luôn cho "nav_home" đang được select
        binding.navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        // Ẩn thanh ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Mở ngăn kéo navigation
        binding.topAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        // Hàm cập nhật info user từ firebase lên nav header
        updateNavHeader();
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
        }

        // Đóng ngăn kéo (drawer)
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        // Nếu drawer đang mở thì khi nhấn nút back ở phone thì đóng drawer
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

    // Đưa dữ liệu user lên header navigation
    public void updateNavHeader() {
        View headerView = binding.navigationView.getHeaderView(0);
        tv1 = headerView.findViewById(R.id.nav_email);
        tv2 = headerView.findViewById(R.id.nav_userId);

        tv1.setText(currentUser.getEmail());
        tv2.setText(currentUser.getUid());
    }
}