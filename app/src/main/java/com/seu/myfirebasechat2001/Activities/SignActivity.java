package com.seu.myfirebasechat2001.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.seu.myfirebasechat2001.R;

import es.dmoral.toasty.Toasty;

public class SignActivity extends AppCompatActivity {

    EditText mail, pass;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        firebaseAuth = FirebaseAuth.getInstance();
        mail = findViewById(R.id.email);
        pass = findViewById(R.id.pass);

    }

    public void sign(View view) {
        Toasty.info(getApplicationContext(), "Validating Your User Credential...", Toast.LENGTH_LONG, true).show();

        String mail_str = mail.getText().toString();

        String pass_str = pass.getText().toString();

        Sign_In(mail_str, pass_str);


    }

    private void Sign_In(String mail_str, String pass_str) {

        firebaseAuth.signInWithEmailAndPassword(mail_str, pass_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toasty.success(getApplicationContext(), "Success!", Toast.LENGTH_SHORT, true).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();


                }
            }
        });

    }
}
