package codenerdteam.codenerdedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import codenerdteam.codenerdedu.databinding.ActivityUserRegisterBinding;

public class UserRegister extends AppCompatActivity {

    // ViewBinding
    private ActivityUserRegisterBinding binding;

    private FirebaseAuth firebaseAuth;

    // Hộp thoại tiến trình (progress dialog)
    private ProgressDialog progressDialog;


    private String email = "", password = "", confirmPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Cấu hình progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.please_wait);
        progressDialog.setMessage("Đang đăng ký tài khoản...");
        progressDialog.setCanceledOnTouchOutside(false); //tắt huỷ chạm ra bên ngoài

        // Ẩn thanh ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Sự kiện OnClickListener khi click vào registerBtn
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData(); // Gọi hàm ràng buộc dữ liệu
            }
        });

        // Khi click vào "Login", gọi sự kiện OnClickListener
        binding.logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserRegister.this, UserLogin.class)); // Chuyển đến UserLogin
            }
        });
    }

    // Hỗ trợ điều hướng
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Trở về trước đó khi nhấp vào nút quay lại của actionbar
        return super.onSupportNavigateUp();
    }

    public void validateData() {
        // Lấy dữ liệu
        email = binding.emailEt.getText().toString().trim();
        password = binding.passwordEt.getText().toString().trim();
        confirmPassword = binding.confirmPasswordEt.getText().toString().trim();

        // Ràng buộc dữ liệu
        if (TextUtils.isEmpty(email)){
            // Nếu để trống email hoặc không nhập email
            binding.emailEt.setError("Hãy nhập tài khoản Email");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Định dạng email không hợp lệ, không cho tiếp tục
            binding.emailEt.setError("Email không hợp lệ");
        }
        else if (TextUtils.isEmpty(password)) {
            // Nếu để trống mật khẩu hoặc không nhập mật khẩu
            binding.passwordEt.setError("Hãy nhập mật khẩu");
        }
        else if (password.length() < 6) {
            // Mật khẩu ít hơn 6 ký tự
            binding.passwordEt.setError("Mật khẩu phải chứa ít nhất là 6 ký tự");
        }
        else if (TextUtils.isEmpty(confirmPassword)){
            // Nếu để trống nhập lại mật khẩu hoặc không nhập nhập lại mật khẩu
            binding.confirmPasswordEt.setError("Hãy nhập nhập lại mật khẩu");
        }
        else if (!confirmPassword.equals(password)) {
            // Nếu nhập mật khẩu không khớp với mật khẩu
            binding.confirmPasswordEt.setError("Mật khẩu và nhập lại mật khẩu không trùng khớp");
        }
        else {
            // Dữ liệu đã hợp lệ, tiến tới đăng ký với firebase
            firebaseRegister();
        }
    }

    // Đăng ký bằng firebase
    public void firebaseRegister() {
        // hiển thị hộp thoại
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // Đăng ký thành công
                progressDialog.dismiss();

                // Lấy thông tin user
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String email = firebaseUser.getEmail();
                Toast.makeText(UserRegister.this, "Tài khoản đã được tạo thành công!\n"+email, Toast.LENGTH_SHORT).show();

                // Mở UserProfile
                startActivity(new Intent(UserRegister.this, BaseHomeActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Đăng ký thất bại
                progressDialog.dismiss();
                Toast.makeText(UserRegister.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}