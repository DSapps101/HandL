package com.mako.handl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class ItemActivity extends AppCompatActivity {

    TableLayout myTableLayout;
    TextView txtTotal;
    TextView txtCount;
    Spinner spinnerCat;
    ArrayList<String> ar;
    ArrayList<String> arSub;
    ArrayList<String> arPowerUsers;
    String strCatChosen;
    String strUser;
    Button btnSearch;
    Button btnShow;
    Button btnRefresh;
    EditText editTxtSearch;
    TextView txtShowing;
    String strSearch;
    Boolean bAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        strUser = getIntent().getStringExtra("strUser");

        ar = new ArrayList<>();
        arSub = new ArrayList<>();
        arPowerUsers = new ArrayList<>();
        strCatChosen = "tools";
        strSearch = "";
        bAll = false;


        myTableLayout = findViewById(R.id.tblLayout);
        //txtTotal = findViewById(R.id.txtTotal);
        //txtCount = findViewById(R.id.txtCount);
        spinnerCat = findViewById(R.id.spinner);
        btnSearch = findViewById(R.id.btnSearch);
        btnShow = findViewById(R.id.btnAll);
        btnRefresh = findViewById(R.id.btnRefresh);
        editTxtSearch = findViewById(R.id.editTxtSearch);
        txtShowing = findViewById(R.id.txtShowing);

        //initCats();
        //addUsers();
        //Toast.makeText(this, "the current user is" + strUser, Toast.LENGTH_LONG).show();
        getPowerusers();
        initTable();
        initListeners();
        initSpinner();

    }

    public void initListeners(){

        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                //Toast.makeText(ItemActivity.this,"you selected" + parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG ).show();
                strCatChosen = parentView.getItemAtPosition(position).toString();
                bAll = false;
                updateTable();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strSearch = editTxtSearch.getText().toString();
                updateTable();

            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bAll = !bAll;
                updateTable();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTable();
                updateTable();
            }
        });



    }

    public void initTable(){



        DatabaseReference dbStockRef;

        FirebaseDatabase priceDatabase = FireOfflineClass.getDatabase();

        dbStockRef = priceDatabase.getReference().child("priceslist");

        //dbStockRef.addListenerForSingleValueEvent(new ValueEventListener()

        dbStockRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /*
                long longTotal = snapshot.getChildrenCount();
                String strTotal = Long.toString(longTotal);
                txtTotal.setText(strTotal);

                int k = 0;

                 */

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    String strCode = dataSnapshot.child("code").getValue().toString();
                    String strDesc = dataSnapshot.child("description").getValue().toString();
                    String strUnit = dataSnapshot.child("unit").getValue().toString();
                    String strList = dataSnapshot.child("list").getValue().toString();
                    String strSelling = dataSnapshot.child("selling").getValue().toString();
                    String strCash = dataSnapshot.child("cash").getValue().toString();
                    String strAcc = dataSnapshot.child("acc").getValue().toString();
                    String strCat = dataSnapshot.child("Category").getValue().toString();
                    String strID = dataSnapshot.getKey();

                    String strCombined;

                    strCombined = strCode + "_" + strDesc + "_" + strUnit + "_" + strList + "_" + strSelling + "_" + strCash + "_" + strAcc + "_" + strCat + "_" + strID;

                    ar.add(strCombined);

                    /*
                    k++;
                    String strK = Integer.toString(k);
                    txtCount.setText(strK);

                     */

                }

                myTableLayout.removeAllViews();

                /*
                ArrayList<String> rows = ar;

                for(int i=0;i<rows.size();i++) {
                    Log.d("Rows", rows.get(i));


                    TableRow tableRow = new TableRow(getApplicationContext());
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    myTableLayout.addView(tableRow);


                    //String st = "test1_test2_test3";
                    String s = rows.get(i);
                    String[] cols = s.split("_");


                    for (final String col : cols) {
                        TextView columsView = new TextView(getApplicationContext());
                        columsView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                        columsView.setTextColor(getResources().getColor(R.color.black));
                        columsView.setText(String.format("%7s", col));
                        Log.d("Cols", String.format("%7s", col));
                        tableRow.addView(columsView);
                    }
                }
                 */
                Toast.makeText(ItemActivity.this, "All items loaded. Please choose a Category", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void initCats(){

        FirebaseDatabase catDatabase = FireOfflineClass.getDatabase();

        DatabaseReference catDatabaseRef;

        catDatabaseRef = catDatabase.getReference();

        String[] catArray = {"abrasives","bronze","engineering","fasteners","hardware","irrigation","protective","tools","welding","pl-gut-wo-filt","bf-rivets","drills","fast-temp-xox"};

        for(final String cat : catArray){

            catDatabaseRef.child("categories").child(cat).setValue("0");

        }





    }

    public void initSpinner(){



        FirebaseDatabase catDatabase = FireOfflineClass.getDatabase();

        DatabaseReference catDatabaseRef;

        catDatabaseRef = catDatabase.getReference().child("categories");

        catDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<String> arCat =  new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String strCat = dataSnapshot.getKey();
                    arCat.add(strCat);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ItemActivity.this, android.R.layout.simple_spinner_item, arCat);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCat.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void updateTable(){

        if(ar != null) {
            arSub.clear();
            arSub.add("Code_Description_Unit_List price  _Selling price cash_Wp cash_Wp acc");
            int j=0;
            for (final String strSubRow : ar) {

                if(!strSearch.isEmpty()){

                    if (strSubRow.contains(strCatChosen) && strSubRow.toLowerCase(Locale.ROOT).contains(strSearch.toLowerCase(Locale.ROOT))) {
                        arSub.add(strSubRow);
                        j++;
                    }

                }
                else {

                    if (strSubRow.contains(strCatChosen)) {
                        arSub.add(strSubRow);
                        j++;
                    }
                }

                if (j == 50) {
                    if (bAll){
                        j = 51;
                    }
                    else {
                        txtShowing.setText("Showing top 50");
                        break;
                    }
                }


            }


            myTableLayout.removeAllViews();

            ArrayList<String> rows = arSub;

            for (int i = 0; i < rows.size(); i++) {
                Log.d("Rows", rows.get(i));


                TableRow tableRow = new TableRow(getApplicationContext());
                tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));


                if(i % 2 == 0){
                    tableRow.setBackgroundColor(getResources().getColor(R.color.clrOdd));
                }
                else {
                    tableRow.setBackgroundColor(getResources().getColor(R.color.clrEven));
                }

                myTableLayout.addView(tableRow);


                //String st = "test1_test2_test3";
                String s = rows.get(i);
                String[] cols = s.split("_");

                if (arPowerUsers.contains(strUser)) {
                    tableRow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openEdit(cols[8]);
                            //Toast.makeText(ItemActivity.this,"Click event worked" + cols[8], Toast.LENGTH_SHORT).show();
                        }
                    });
                }


                int l = 0;


                for (final String col : cols) {
                    l++;
                    if (!(l==8)) {
                        TextView columsView = new TextView(getApplicationContext());
                        columsView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                        columsView.setTextColor(getResources().getColor(R.color.black));


                        switch (l) {

                            case 1:
                                columsView.setText(String.format("%40s", col));
                                break;
                            case 2:
                                columsView.setText(String.format("%120s", col));
                                break;
                            case 3:
                                columsView.setText(String.format("%15s", col));
                                break;
                            case 4:
                                columsView.setText(String.format("%15s", col));
                                break;
                            case 5:
                                columsView.setText(String.format("%15s", col));
                                break;
                            case 6:
                                columsView.setText(String.format("%15s", col));
                                break;
                            case 7:
                                columsView.setText(String.format("%15s", col));
                                break;
                            default:

                                //
                        }

                        //columsView.setText(String.format("%15s", col));
                        //Log.d("Cols", String.format("%15s", col));
                        tableRow.addView(columsView);
                    }
                }
            }
            if (j != 50){
                txtShowing.setText("Showing all");
            }
        }
    }

    public void openEdit(String strID){

        Toast.makeText(ItemActivity.this,"Click event worked" + strID, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("strID", strID);
        intent.putExtra("strUser", strUser);
        startActivity(intent);


    }

    public void getPowerusers(){

        FirebaseDatabase mDatabase = FireOfflineClass.getDatabase();
        DatabaseReference mDatabaseRef =  mDatabase.getReference().child("powerUsers");

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    arPowerUsers.add(dataSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void addUsers(){

        FirebaseDatabase mDatabase = FireOfflineClass.getDatabase();

        DatabaseReference mDatabaseRef;

        mDatabaseRef = mDatabase.getReference();

        mDatabaseRef.child("powerUsers").child("deonsteenberg").setValue("1");
        mDatabaseRef.child("powerUsers").child("a").setValue("1");



    }

}