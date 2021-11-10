package com.kazimasum.qrdemofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class qrscanner extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView scannerView;
    DatabaseReference pay,user;
    String Username,Email;
    long Money;
    long count_id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);
        user= FirebaseDatabase.getInstance().getReference("Users");

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                    }
                }).check();


    }

    @Override
    public void handleResult(Result rawResult) {
        final Date date = new Date();
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String UserId=rawResult.getText().toString();
        pay= FirebaseDatabase.getInstance().getReference("pay/"+UserId);
        pay.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    count_id= snapshot.getChildrenCount();

                }
                count_id++;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        user= FirebaseDatabase.getInstance().getReference("Users/"+UserId);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Money = (snapshot.getValue(Userinfo.class).getMoney());
                Username = (snapshot.getValue(Userinfo.class).getName());
                user.child("money").setValue(Money-20);
                pay.child(String.valueOf(count_id)).child("name").setValue(Username);
                pay.child(String.valueOf(count_id)).child("money").setValue(20);
                pay.child(String.valueOf(count_id)).child("time").setValue(formatter.format(date));
                Toast.makeText(qrscanner.this,"Scan Success",Toast.LENGTH_LONG).show();
//                MainActivity.qrtext.setText("Username");
                onBackPressed();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(qrscanner.this,"Unknow User",Toast.LENGTH_LONG).show();
//                MainActivity.qrtext.setText("Unknow User");
                onBackPressed();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}