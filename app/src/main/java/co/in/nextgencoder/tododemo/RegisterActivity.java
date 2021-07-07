package co.in.nextgencoder.tododemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.in.nextgencoder.tododemo.util.User;
import co.in.nextgencoder.tododemo.util.Validation;


public class RegisterActivity extends AppCompatActivity {
    private EditText nameRegisterET;
    private EditText mailRegisterET;
    private EditText passRegisterET;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private Validation validation = new Validation();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mailRegisterET = findViewById(R.id.mailRegisterET);
        passRegisterET = findViewById(R.id.passRegisterET);
        nameRegisterET = findViewById(R.id.nameRegisterET);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    public void registerUser(View view) {
            String mail = mailRegisterET.getText().toString().trim();
            String password = passRegisterET.getText().toString().trim();
            String name = nameRegisterET.getText().toString().trim();

            String validationMessage = validation.validateUser(mail, password);

            if (validationMessage.equals("successful")) {
                firebaseAuth.createUserWithEmailAndPassword(mail, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    DatabaseReference userReference = databaseReference.child("User");
                                    String id = firebaseAuth.getUid();
                                    User user = new User(id,name,mail);
                                    userReference.child(id).setValue(user);

                                    Toast.makeText(RegisterActivity.this,  validationMessage, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, validationMessage, Toast.LENGTH_SHORT).show();
            }


    }

    public void goToLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}




