package codenerdteam.codenerdedu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import codenerdteam.codenerdedu.adapter.WalkthroughAdapter;

public class StartedActivity extends AppCompatActivity {
    private ViewPager mSlideViewPager;

    private LinearLayout mDotLayout;

    private WalkthroughAdapter walkthroughAdapter;

    private TextView[] dots;

    private Button backBtn, nextBtn, skipBtn;

    private SharedPreferences preferences;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started);

        // Kiểm tra nếu mà app được mở lần đầu tiên sau khi cài đặt
        preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        String firstTime =  preferences.getString("FirstTimeInstall","");

        if (firstTime.equals("Yes")) {
            // Nếu mà app đã được mở lần đầu tiên rồi, chuyển qua UserLogin bỏ qua activity này
            startActivity(new Intent(StartedActivity.this, UserLogin.class));
        }
        else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }

        // Ẩn thanh ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);
        skipBtn = findViewById(R.id.skipBtn);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getItem(0) > 0) {
                    mSlideViewPager.setCurrentItem(getItem(-1), true);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getItem(0) < 2) {
                    mSlideViewPager.setCurrentItem(getItem(1), true);
                }
                else {
                    startActivity(new Intent(StartedActivity.this, UserLogin.class));
                    finish();
                }
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartedActivity.this, UserLogin.class));
                finish();
            }
        });

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicatior_layout);

        walkthroughAdapter = new WalkthroughAdapter(this);

        mSlideViewPager.setAdapter(walkthroughAdapter);

        setUpIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setUpIndicator(int position) {
        dots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,
                    getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.active,
                getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);

            if (position > 0) {
                backBtn.setVisibility(View.VISIBLE);
            }
            else {
                backBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getItem(int i) {
        return mSlideViewPager.getCurrentItem() + i;
    }
}