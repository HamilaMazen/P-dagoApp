package exemple.aaa.projectm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;




public class Page2 extends AppCompatActivity {
            private FirebaseAuth auth;
            private RadioGroup group;
            private RadioButton radio;
            private EditText username,psd;
    private ProgressBar progressBar;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    public Page2() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getSupportActionBar().hide(); //<< this
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_page2);

        Button btn=(Button)findViewById(R.id.button3);
       progressBar = (ProgressBar)findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
         btn.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                username=(EditText)findViewById(R.id.userText);
                psd=(EditText)findViewById(R.id.passwordtext);
                        String user=username.getText().toString();
                final String password=psd.getText().toString();
                group=(RadioGroup)findViewById(R.id.radioGroup);
                final int selectId=group.getCheckedRadioButtonId();
                radio=(RadioButton)findViewById(selectId);

                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(user,password).addOnCompleteListener(Page2.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //========================
                            progressBar.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                // there was an error
                                if (password.length() < 6) {
                                    psd.setError("trés courte");
                                } else {
                                    Toast.makeText(Page2.this, "verifier votre email et mot de passe*", Toast.LENGTH_LONG).show();
                                }
                            }else {

                                final String id = auth.getCurrentUser().getUid().toString();

                          DatabaseReference data= database.getReference().child("Users");
                                data.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String stauts=String.valueOf(dataSnapshot.child(id).child("status").getValue());
                                        String nom=String.valueOf(dataSnapshot.child(id).child("nom").getValue());
                                        if(stauts.equals(radio.getText().toString())){

                                            if(radio.getText().toString().equals("Enseignant")){
                                                Toast.makeText(Page2.this,"Bonjour "+nom,Toast.LENGTH_SHORT).show();
                                                Intent it1= new Intent(Page2.this,MainActivityDraw.class);
                                               it1.putExtra("status","Enseignant");


                                                startActivity(it1);
                                                finish();
                                                return;

                                            }
                                            else{
                                                    //Etudiant
                                                           Toast.makeText(Page2.this,nom,Toast.LENGTH_SHORT).show();
                                                    Intent it2 = new Intent(Page2.this, MainActivityDraw.class);
                                                   it2.putExtra("status","Etudiant");

                                                   startActivity(it2);
                                                    finish();
                                                    return;

                                            }
                                        }else
                                        {


                                            Toast.makeText(Page2.this,"verifier votre email et mot de passe",Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });




                            }
                        }
                    });



            }
        });
    }






}
