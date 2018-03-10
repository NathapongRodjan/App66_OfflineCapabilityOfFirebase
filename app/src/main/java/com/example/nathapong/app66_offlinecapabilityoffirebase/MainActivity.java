package com.example.nathapong.app66_offlinecapabilityoffirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText edtData;
    Button btnSendData;
    TextView txtData;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    private String oldTxtData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtData = (EditText)findViewById(R.id.edtData);
        btnSendData = (Button)findViewById(R.id.btnSendData);
        txtData = (TextView)findViewById(R.id.txtData);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        databaseReference.keepSynced(true);


        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child(databaseReference.push().getKey())
                        .setValue(edtData.getText().toString());

                edtData.setText("");

            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                oldTxtData = "";

                for (DataSnapshot objectDB : dataSnapshot.getChildren()){

                    String value = objectDB.getValue(String.class);

                    txtData.setText(oldTxtData + value + "\n\n");

                    oldTxtData = txtData.getText().toString();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
