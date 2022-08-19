package codenerdteam.codenerdedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;

import codenerdteam.codenerdedu.databinding.ActivityUserLoginBinding;

public class UserLogin extends AppCompatActivity {
    // ViewBinding
    private ActivityUserLoginBinding binding;

    private FirebaseAuth firebaseAuth;

    private MessageHandler messageHandler;

    // Hộp thoại tiến trình (progress dialog)
    private ProgressDialog progressDialog;

    private String email = "", password = "";

    CountDownTimer Timer;
    int attempt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        messageHandler = new MessageHandler();

        // Cấu hình progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Thông báo");
        progressDialog.setMessage("Vui lòng đợi...");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false); //tắt huỷ chạm ra bên ngoài

        // Khởi tạo firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        // Ẩn thanh ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Khi click vào "Register", gọi sự kiện OnClickListener
        binding.regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến UserRegister
                startActivity(new Intent(UserLogin.this, UserRegister.class));
            }
        });

        // Sự kiện OnClickListener khi click vào "->" button
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData(); // Gọi hàm ràng buộc dữ liệu

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // Đăng nhập thành công
                                //Lấy thông tin user
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                Toast.makeText(UserLogin.this, "Đã đăng nhập "+email, Toast.LENGTH_SHORT).show();

                                // Chuyển đến BaseHomeActivity
                                startActivity(new Intent(UserLogin.this, BaseHomeActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Đăng nhập thất bại, hiện thị thông báo lỗi
                                progressDialog.dismiss(); // Tắt hiện thị hộp thoại
                                if (attempt < 4) {
                                    Toast.makeText(UserLogin.this, "Email hoặc mật khẩu không đúng, lần "+attempt, Toast.LENGTH_SHORT).show();
                                } else if (attempt == 4) {
                                    Toast.makeText(getApplicationContext(), "Vượt quá giới hạn đăng nhập", Toast.LENGTH_LONG).show();
                                } else if (attempt > 4) {
                                    startCounter(view);
                                    attempt =0;
                                }
                                attempt++;
                            }
                        });
            }
        });

        // Sự kiện OnClickListener khi click vào "Quên mật khẩu?"
        binding.forgotPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến ResetPasswordActivity
                startActivity(new Intent(UserLogin.this, ResetPasswordActivity.class));
            }
        });
    }

    private void validateData() {
        // Lấy dữ liệu
        email = binding.emailEt.getText().toString().trim();
        password = binding.passwordEt.getText().toString().trim();

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
        else {
            // Dữ liệu đã hợp lệ, tiến tới đăng nhập với firebase
            firebaseLogin();
        }
    }

    // Đăng nhập bằng firebase
    private void firebaseLogin() {

    }

    public void checkUser() {
        // Kiểm tra user đã đăng nhập rồi
        // Nếu đã đăng nhập thì vào thẳng UserProfile

        // Lấy user hiện tại
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            // User đã được đăng nhập vào
            startActivity(new Intent(this, BaseHomeActivity.class));
            finish();
        }
    }

    private void startCounter(View view){
        progressDialog.show();
        Thread thread = new Thread(new Timer());
        thread.start();
    }

    private class Timer implements Runnable{
        @Override
        public void run(){
            for(int i = 20; i >=0; i--){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){

                }
                Bundle bundle = new Bundle();
                bundle.putInt("current count", i );

                Message message = new Message();
                message.setData(bundle);

                messageHandler.sendMessage(message);
            }
            progressDialog.dismiss();
        }
    }

    private class MessageHandler extends Handler {
        public void handleMessage(Message message){
            int currentCount = message.getData().getInt("current count");
            progressDialog.setMessage("Vui lòng đợi trong ... "+ currentCount);
        }
    }

    public void onBackPressed() {
        finish();
    }

}