package com.example.kingsrental;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.MediaRouteButton;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    //variables
    ImageView login_logo;
    TextView login_slogan, login_dialog, login_nw, login_fp;
    TextInputLayout email, pass;
    Button login_button;
    //ProgressBar progressBar;


    //database Variables
    FirebaseAuth auth;

    //FirebaseDatabase rootNode;
    //DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAGS_CHANGED, WindowManager.LayoutParams.FLAGS_CHANGED);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();


        //hooks
        login_logo = findViewById(R.id.login_logo);
        login_slogan = findViewById(R.id.login_slogan);
        login_dialog = findViewById(R.id.login_dialog);
        email = findViewById(R.id.login_user);
        pass = findViewById(R.id.login_pass);
        login_fp = findViewById(R.id.login_fp);
        login_button = findViewById(R.id.login_button);
        login_nw = findViewById(R.id.login_new);
        //progressBar = findViewById(R.id.logi_progressBar);
/*
        login_nw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });
*/
        //rootNode=FirebaseDatabase.getInstance();
        //reference=rootNode.getReference("users");

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_email=email.getEditText().getText().toString();
                String txt_password=pass.getEditText().getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){

                    Toast.makeText(Login.this,"All Fields Are Required",Toast.LENGTH_SHORT).show();

                }else {

                    auth.signInWithEmailAndPassword(txt_email,txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){

                                        Intent intent=new Intent(Login.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }else {

                                        Toast.makeText(Login.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

        login_fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ForgetPassword.class ));
            }

        });

        login_nw.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                //startActivity(intent);
                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View, String>(login_logo, "logo_image");
                pairs[1] = new Pair<View, String>(login_slogan, "logo_text");
                pairs[2] = new Pair<View, String>(login_dialog, "login_desc");
                pairs[3] = new Pair<View, String>(email, "login_username");
                pairs[4] = new Pair<View, String>(pass, "login_password");
                pairs[5] = new Pair<View, String>(login_button, "login_go");
                pairs[6] = new Pair<View, String>(login_nw, "login_nw");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle()); //to carry transactions or animations
                finish();

            }
        });
    }


}
