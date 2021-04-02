package com.example.kingsrental;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kingsrental.Login;
import com.example.kingsrental.R;

public class Splash extends AppCompatActivity {

    private static int SPLASH_SCREEN=3000;  //to set time for splash screen

    //variables
    Animation topAnim, bottomAnim;
    ImageView logo;
    TextView name,slogan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //Animations

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //hooks

        logo=findViewById(R.id.logo);
        name=findViewById(R.id.name);
        slogan=findViewById(R.id.slogan);


        //set animations for image and text
        logo.setAnimation(topAnim);
        name.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash.this, Login.class); // to shift to another activity after splash
                /*startActivity(intent);
                finish(); //to finish splash activity*/
                //for animation to go on login screen

                Pair[] pairs=new Pair[2];
                pairs[0]=new Pair<View,String>(logo,"logo_image");
                pairs[1]=new Pair<View,String>(name,"logo_text");

                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Splash.this,pairs);

                startActivity(intent,options.toBundle());  //option.tobundle will carry out aniamtions

                finish();

            }
        },SPLASH_SCREEN);



    }
}
