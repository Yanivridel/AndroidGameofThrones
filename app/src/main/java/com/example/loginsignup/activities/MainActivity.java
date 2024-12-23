package com.example.loginsignup.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.CustomeAdapter;
import com.example.loginsignup.DataModel;
import com.example.loginsignup.R;
import com.example.loginsignup.models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    // Firebase DataBase
    private FirebaseAuth mAuth;
    // main firebase route
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    // Register
    public void register() {
        String email = ((EditText)findViewById(R.id.email_input_reg)).getText().toString();
        String phone = ((EditText)findViewById(R.id.phone_input_reg)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_input_reg)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.confirm_password_input_reg)).getText().toString();


        if (!validateEmail(email)) {
            Toast.makeText(MainActivity.this, "Invalid email format", Toast.LENGTH_LONG).show();
            return;
        } else if (!validatePassword(password) || !validatePassword(confirmPassword)) {
            Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_LONG).show();
            return;
        } else if (!validateSamePasswords(password,confirmPassword)) {
            Toast.makeText(MainActivity.this, "Passwords are not equal", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(MainActivity.this , "Register Success", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(MainActivity.this, R.id.fragmentContainerView).navigate(R.id.action_fragment_register_to_fragment_menu);
                    // getStudent(phone);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(MainActivity.this , "Register Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    // Login
    public void login() {

        String email = ((EditText)findViewById(R.id.email_input_log)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_input_log)).getText().toString();

        if (!validateEmail(email)) {
            Toast.makeText(MainActivity.this, "Invalid email format", Toast.LENGTH_LONG).show();
            return;
        } else if (!validatePassword(password)) {
            Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(MainActivity.this , "Login success", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(MainActivity.this, R.id.fragmentContainerView).navigate(R.id.action_fragment_menu_to_fragment_app);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(MainActivity.this , "Login Failed", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    public boolean validateEmail(String email) {
        return !email.isEmpty() && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    public boolean validatePassword(String password) {
        return !password.isEmpty();
    }
    public boolean validateSamePasswords(String password1, String password2) {
        return Objects.equals(password1, password2);
    }

    public void addDataToDB() {
        // get data from layout
        String phone = ((EditText)findViewById(R.id.phone_input_reg)).getText().toString();
        String email = ((EditText)findViewById(R.id.email_input_reg)).getText().toString();

        // route inside the database
        DatabaseReference usersRef = database.getReference("users").child(phone);

        Student s = new Student(email, phone);

        usersRef.setValue(s);
    }

    public void getStudent(String phone) {
        // route inside the database
        DatabaseReference usersRef = database.getReference("users").child(phone);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Student value = dataSnapshot.getValue(Student.class);
                Toast.makeText(MainActivity.this, value.getEmail(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //test
            }
        });
    }
}