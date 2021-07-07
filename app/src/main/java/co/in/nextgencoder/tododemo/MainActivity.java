package co.in.nextgencoder.tododemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.in.nextgencoder.tododemo.util.Todo;
import co.in.nextgencoder.tododemo.util.Validation;

public class MainActivity extends AppCompatActivity {
        private EditText taskET;
        private FirebaseAuth firebaseAuth;
        private DatabaseReference databaseReference;
        private final Validation validation = new Validation();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskET = findViewById(R.id.task);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    public void addDataToTodo(View view) {
        String task = taskET.getText().toString().trim();
        DatabaseReference toDoReference = databaseReference.child("User").child(firebaseAuth.getUid()).child("Task");
        String id = firebaseAuth.getUid();

        Todo todo = new Todo(id,false,task);
        toDoReference.child(id).setValue(todo);
    }
}