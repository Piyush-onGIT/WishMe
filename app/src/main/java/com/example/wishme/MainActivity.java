package com.example.wishme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Data");
//        TextView txt = findViewById(R.id.txt1);
        Button update = findViewById(R.id.update);
        Calendar calendar;
        SimpleDateFormat dateFormat;
        String date;
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());
//        String url = "https://drive.google.com/uc/export=view&id=";
        String url = "https://drive.google.com/uc?export=view&id=";
        ImageView img = findViewById(R.id.img);

//        update.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view){
//                Picasso.get()
//                        .load(url)
//                        .placeholder(R.drawable.ic_baseline_image_24)
//                        .into(img);
//            }
//        });

        update.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        try{
                            String value = dataSnapshot.child("data").getValue(String.class);
                            String[] arr = value.split("/");
                            String n_url = url + arr[5];
                            Picasso.get()
                                .load(n_url)
                                .placeholder(R.drawable.ic_baseline_image_24)
                                .into(img);
//                            txt.setText(value);
                        }
                        catch(Exception e){
                            String value = "Error";
//                            txt.setText(value);
                        }
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });

    }
}