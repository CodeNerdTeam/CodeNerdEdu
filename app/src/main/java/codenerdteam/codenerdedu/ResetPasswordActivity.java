package codenerdteam.codenerdedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import codenerdteam.codenerdedu.databinding.ActivityResetPasswordBinding;

public class ResetPasswordActivity extends AppCompatActivity {
    // ViewBinding
    private ActivityResetPasswordBinding binding;

    private FirebaseAuth firebaseAuth;

    private ActionBar actionBar;

    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Cấu hình actionbar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Quên mật khẩu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Khởi tạo firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Sự kiện OnClickListener khi click vào "Gửi" button
        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    // Ràng buộc dữ liệu
    private void validateData() {
        // Lấy dữ liệu thông qua biến email
        email = binding.emailEt.getText().toString().trim();

        // Ràng buộc dữ liệu
        if (TextUtils.isEmpty(email)){
            // Nếu để trống email hoặc không nhập email
            binding.emailEt.setError("Hãy nhập tài khoản Email");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Định dạng email không hợp lệ, không cho tiếp tục
            binding.emailEt.setError("Email không hợp lệ");
        }
        else {
            // Dữ liệu đã hợp lệ, tiến tới đăng nhập với firebase
            firebaseResetPassword();
        }
    }

    private void firebaseResetPassword() {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Gửi thành công, thông báo cho user
                if (task.isSuccessful()) {
                    Toast.makeText(ResetPasswordActivity.this,
                            "Mật khẩu gửi đến email của bạn", Toast.LENGTH_SHORT).show();
                }
                // Gửi thất bại, thống báo lỗi từ hệ thống
                else {
                    Toast.makeText(ResetPasswordActivity.this,
                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}