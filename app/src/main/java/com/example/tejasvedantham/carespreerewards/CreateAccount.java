package com.example.tejasvedantham.carespreerewards;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {

    private String firstName;
    private String lastName;
    private String gender;
    private String height;
    private int weight;
    private String email;
    private String password;
    private int age;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ImageView myImageView = (ImageView) findViewById(R.id.userpng);
        Bitmap myBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.user);
        myImageView.setImageBitmap(myBitmap);


        mAuth = FirebaseAuth.getInstance();
    }

    public void createAccountAndPush(View v) {

        //Error handling

        firstName = ((EditText) findViewById(R.id.firstName)).getText().toString();
        lastName = ((EditText) findViewById(R.id.lastName)).getText().toString();
        gender = ((Spinner) findViewById(R.id.gender)).getSelectedItem().toString();
        height = ((Spinner) findViewById(R.id.height)).getSelectedItem().toString();
        weight = Integer.parseInt( ((EditText) findViewById(R.id.weight)).getText().toString());
        email = ((EditText) findViewById(R.id.email)).getText().toString();
        password = ((EditText) findViewById(R.id.password)).getText().toString();
        age = Integer.parseInt(((EditText) findViewById(R.id.age)).getText().toString());

        final Person person = new Person(firstName, lastName, age, gender, height, weight, email, password);
        FirebaseUser user;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            final FirebaseUser user = task.getResult().getUser();
                            final String uid = user.getUid();
                            FirebaseDatabase.getInstance().getReference(uid).setValue(person);
                        }
                        else {
                            final FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });

        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(intent);
    }
}
