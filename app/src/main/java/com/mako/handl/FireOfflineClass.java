package com.mako.handl;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
//import com.google.firebase.storage.FirebaseStorage;

public class FireOfflineClass {

    private static FirebaseDatabase myDatabase;
    //private static FirebaseStorage myStorage;



    public static FirebaseDatabase getDatabase() {
        if (myDatabase == null) {
            myDatabase = FirebaseDatabase.getInstance();
            myDatabase.setPersistenceEnabled(true);
        }
        return myDatabase;
    }

    @NonNull
    public static ArrayList<String> getMyString() throws InterruptedException {

        final Boolean[] done = {false};


        ArrayList<String> ar = new ArrayList<String>();

        DatabaseReference dbStockRef;

        FirebaseDatabase priceDatabase = getDatabase();

        dbStockRef = priceDatabase.getReference().child("priceslist");

        dbStockRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String strItem1 = "test1_test2_test3";
                    String strItem2 = "test4_test5_test6";
                    String strItem3 = "test7_test8_test9";

                    ar.add(strItem1);
                    ar.add(strItem2);
                    ar.add(strItem3);

                }

                done[0] = true;




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Object obj = null;
        synchronized (obj) {
            while(!done[0])
            obj.wait();
        }



        /*
        String strItem1 = "test1_test2_test3";
        String strItem2 = "test4_test5_test6";
        String strItem3 = "test7_test8_test9";

        ar.add(strItem1);
        ar.add(strItem2);
        ar.add(strItem3);

         */

        return ar;


        //return new String[]{"test1_test2_test3","test4_test5_test6","test7_test8_test9"};

    }

    /*
    public static FirebaseStorage getStorage() {

        if (myStorage == null) {
            myStorage = FirebaseStorage.getInstance();
        }
        return myStorage;
    }

     */

}