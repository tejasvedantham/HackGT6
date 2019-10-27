package com.example.tejasvedantham.carespreerewards;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import androidx.fragment.app.Fragment;

public class AccountInfo extends Fragment {

    private Spinner height;
    private EditText weight;
    private String firstName;
    private FirebaseAuth mAuth;

    public AccountInfo() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_account_info, container, false);

        ImageView myImageView = (ImageView) v.findViewById(R.id.informationpng);
        Bitmap myBitmap = BitmapFactory.decodeResource(
                getContext().getResources(),
                R.drawable.information);
        myImageView.setImageBitmap(myBitmap);

        weight = v.findViewById(R.id.updateWeight);
        height = v.findViewById(R.id.updateHeight);

        Button button = v.findViewById(R.id.saveInfoButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();

                if (user != null) {
                    Toast.makeText(getContext(), "Current user UID: " + uid, Toast.LENGTH_LONG).show();

                    //height = ((Spinner) v.findViewById(R.id.updateHeight)).getSelectedItem().toString();
                    int fWeight = Integer.parseInt(weight.getText().toString());
                    String fHeight = height.getSelectedItem().toString();

                    FirebaseDatabase.getInstance().getReference(uid).child("height").setValue(fHeight);
                    FirebaseDatabase.getInstance().getReference(uid).child("weight").setValue(fWeight);
                }
            }
        });

        return v;
    }
}
