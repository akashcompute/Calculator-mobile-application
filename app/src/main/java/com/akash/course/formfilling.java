package com.akash.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class formfilling extends AppCompatActivity {
    private EditText name,subject, message;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        name = findViewById(R.id.name);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);
        send = findViewById(R.id.buttonsend);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString();
                String Subject = subject.getText().toString();
                String Message = message.getText().toString();

                Intent email=new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"akashkrcse1@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, Subject);
                email.putExtra(Intent.EXTRA_TEXT,Name + "\n\n" + Message);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email Client :"));
            }
        });
    }
}
