package com.example.kingsrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {


    TextInputLayout name,username,email,phoneNo,password;
    Button signup_button;
    TextView signup_belowButton;

    //database variables initialisation
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=findViewById(R.id.signup_name);
        username=findViewById(R.id.signup_username);
        email=findViewById(R.id.signup_email);
        phoneNo=findViewById(R.id.signup_phoneNo);
        password=findViewById(R.id.signup_pass);
        signup_button=findViewById(R.id.signp_button);
        signup_belowButton=findViewById(R.id.signup_belowButton);

        auth=FirebaseAuth.getInstance();

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_name=name.getEditText().getText().toString();
                String txt_username=username.getEditText().getText().toString();
                String txt_email=email.getEditText().getText().toString();
                String txt_phoneNo=phoneNo.getEditText().getText().toString();
                String txt_password=password.getEditText().getText().toString();

                if (TextUtils.isEmpty(txt_username)||TextUtils.isEmpty(txt_name)||TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_phoneNo)||TextUtils.isEmpty(txt_password)){

                    Toast.makeText(SignUp.this,"All Fields Are Required",Toast.LENGTH_SHORT).show();

                }else if (txt_password.length()<6){

                    Toast.makeText(SignUp.this,"Password Must Be Atleast 6 Character",Toast.LENGTH_SHORT).show();
                }else {

                    register(txt_username,txt_email,txt_password,txt_name,txt_phoneNo);
                }


            }
        });


    }

    private void register(final String username, final String email, String password, final String name, final String phoneNo) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser=auth.getCurrentUser();

                            assert firebaseUser != null;
                            String userid=firebaseUser.getUid();

                            reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username);
                            hashMap.put("name",name);
                            hashMap.put("email",email);
                            hashMap.put("phoneNo",phoneNo);
                            //hashMap.put("imageURL","default");
                            //hashMap.put("status","offline");
                            //hashMap.put("search",username.toUpperCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        Intent intent=new Intent(SignUp.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });

                        }else {
                            Toast.makeText(SignUp.this,"YOU CAN NOT REGISTER WITH THIS EMAIL AND PASSWORD",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}
