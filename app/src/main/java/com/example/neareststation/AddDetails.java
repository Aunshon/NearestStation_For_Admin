package com.example.neareststation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDetails extends AppCompatActivity {

    EditText ve,time,des;
    TextView addressof;
    DatabaseReference mdatabaseRef;
    StationDetails stationDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        ve=findViewById(R.id.v);
        time=findViewById(R.id.t);
        des=findViewById(R.id.d);
        addressof=findViewById(R.id.addressof);

        Intent intent=getIntent();
        addressof.setText(intent.getExtras().getString("address"));
        mdatabaseRef= FirebaseDatabase.getInstance().getReference().child("vehicles").child(intent.getExtras().getString("key"));
    }

    public void Addbtn(View view) {
        String uploadId=mdatabaseRef.push().getKey();
        if (ve.getText().toString().isEmpty()){
            ve.setError("This field is Empty");
        }
        if (time.getText().toString().isEmpty()){
            time.setError("This field is Empty");
        }
        if (des.getText().toString().isEmpty()){
            des.setError("This field is Empty");
        }
        if (ve.getText().toString()!=null && time.getText().toString()!=null && des.getText().toString()!=null){
            stationDetails=new StationDetails(ve.getText().toString(),time.getText().toString(),des.getText().toString());
            mdatabaseRef.child(uploadId).setValue(stationDetails).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(AddDetails.this, "Added", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }
    }
}
