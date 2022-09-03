package com.seu.myfirebasechat2001.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seu.myfirebasechat2001.R;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;


    EditText name, mail, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("MyUsers");

        name = findViewById(R.id.userName);
        mail = findViewById(R.id.usermail);
        pass = findViewById(R.id.userpass);


    }

    public void register(View view) {
        Toasty.info(getApplicationContext(), "Your Account is Creating....", Toast.LENGTH_LONG, true).show();
        String name_str = name.getText().toString();
        String mail_str = mail.getText().toString();
        String pass_str = pass.getText().toString();

        if (TextUtils.isEmpty(name_str) || TextUtils.isEmpty(mail_str) || TextUtils.isEmpty(pass_str)) {

            name.setError("Required Feild");
            mail.setError("Required Feild");
            pass.setError("Required Feild");


        } else if (pass_str.length() < 6) {

            pass.setError("Must be 6 +");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mail_str).matches()) {

            mail.setError("Input a Valid Email");

        } else {


            Regisetr_User(name_str, mail_str, pass_str);
        }


    }

    private void Regisetr_User(final String name_str, final String mail_str, final String pass_str) {

        firebaseAuth.createUserWithEmailAndPassword(mail_str, pass_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    String userId = firebaseAuth.getUid();

                    HashMap<String, String> mymap = new HashMap<>();

                    mymap.put("id", userId);
                    mymap.put("username", name_str);
                    mymap.put("mail", mail_str);
                    mymap.put("password", pass_str);
                    mymap.put("profile_img", "noimg");

                    databaseReference.child(userId).setValue(mymap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toasty.success(getApplicationContext(), "Success!", Toast.LENGTH_SHORT, true).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();


                            }
                        }
                    });


                } else {

                    Toasty.error(getApplicationContext(), "Register error ", Toast.LENGTH_SHORT, true).show();
                }


            }
        });


    }


}
