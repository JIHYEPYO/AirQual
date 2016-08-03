package com.example.pyojihye.airpollution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by PYOJIHYE on 2016-07-20.
 */
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            EditText editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
            EditText editTextLastName = (EditText) findViewById(R.id.editTextLastName);
            EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
            EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);

            @Override
            public void onClick(View view) {
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(firstName.equals("") || lastName.equals("") || email.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill in all information", Toast.LENGTH_LONG).show();
                    return;
                }else{
                    Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(signInIntent);
                }
            }
        });
    }
}
