package exemple.aaa.projectm;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class splashpage extends AppCompatActivity {
    public static int splash_time_out = 6000;
    private ImageView logo;
    private ImageView wel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashpage);
        logo = (ImageView) findViewById(R.id.logo);
        wel = (ImageView) findViewById(R.id.wel);
        Animation myanim1 = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        logo.startAnimation(myanim1);
        wel.startAnimation(myanim1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(splashpage.this, Page2.class);
                startActivity(homeIntent);
                finish();
            }
        }, splash_time_out);

        //Animation myanim2 = AnimationUtils.loadAnimation(this,R.anim.mytranslation);
        // logo.startAnimation(myanim2);
        // wel.startAnimation(myanim2);

    }
}

