package com.mako.handl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    String strID;
    String strUser;

    EditText editTxtCode;
    EditText editTxtDescription;
    EditText editTxtUnit;
    EditText editTxtList;
    EditText editTxtSelling;
    EditText editTxtWpCash;
    EditText editTxtWpAcc;
    Button btnCancel;
    Button btnUpdate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        strID = getIntent().getStringExtra("strID");
        strUser = getIntent().getStringExtra("strUser");


        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
        editTxtCode = findViewById(R.id.editTxtCode);
        editTxtDescription = findViewById(R.id.editTxtDescription);
        editTxtUnit = findViewById(R.id.editTxtUnit);
        editTxtList = findViewById(R.id.editTxtListPrice);
        editTxtSelling = findViewById(R.id.editTxtSellingPrice);
        editTxtWpCash = findViewById(R.id.editTxtWpCash);
        editTxtWpAcc = findViewById(R.id.editTxtWPAcc);

        intiValues();
        initListeners();


    }

    public void intiValues(){

        FirebaseDatabase mdatabase = FireOfflineClass.getDatabase();
        DatabaseReference mreferrence = mdatabase.getReference().child("priceslist").child(strID);

        mreferrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String strCode = Objects.requireNonNull(dataSnapshot.child("code").getValue()).toString();
                String strDesc = Objects.requireNonNull(dataSnapshot.child("description").getValue()).toString();
                String strUnit = Objects.requireNonNull(dataSnapshot.child("unit").getValue()).toString();
                String strList = Objects.requireNonNull(dataSnapshot.child("list").getValue()).toString();
                String strSelling = Objects.requireNonNull(dataSnapshot.child("selling").getValue()).toString();
                String strCash = Objects.requireNonNull(dataSnapshot.child("cash").getValue()).toString();
                String strAcc = Objects.requireNonNull(dataSnapshot.child("acc").getValue()).toString();
                //String strCat = Objects.requireNonNull(dataSnapshot.child("Category").getValue()).toString();

                editTxtCode.setText(strCode);
                editTxtDescription.setText(strDesc);
                editTxtUnit.setText(strUnit);
                editTxtList.setText(strList);
                editTxtSelling.setText(strSelling);
                editTxtWpCash.setText(strCash);
                editTxtWpAcc.setText(strAcc);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void initListeners(){

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openItemActivity();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strCode = editTxtCode.getText().toString();
                String strDesc = editTxtDescription.getText().toString();
                String strUnit = editTxtUnit.getText().toString();
                String strList = editTxtList.getText().toString();
                String strSelling = editTxtSelling.getText().toString();
                String strCash = editTxtWpCash.getText().toString();
                String strAcc = editTxtWpAcc.getText().toString();

                FirebaseDatabase mdatabase = FireOfflineClass.getDatabase();
                DatabaseReference mreferrence = mdatabase.getReference().child("priceslist").child(strID);

                mreferrence.child("code").setValue(strCode);
                mreferrence.child("description").setValue(strDesc);
                mreferrence.child("unit").setValue(strUnit);
                mreferrence.child("list").setValue(strList);
                mreferrence.child("selling").setValue(strSelling);
                mreferrence.child("cash").setValue(strCash);
                mreferrence.child("acc").setValue(strAcc);

                onBackPressed();

            }
        });
    }

    public void openItemActivity(){
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("USERMail", strUser);
        startActivity(intent);
    }
}