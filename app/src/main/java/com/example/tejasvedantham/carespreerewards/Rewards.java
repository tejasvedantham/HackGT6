package com.example.tejasvedantham.carespreerewards;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Rewards extends Fragment {

    private TextView headerText;
    private TextView availablePoints;
    private FirebaseAuth mAuth;
    public ListView listView;
    public Cursor cursor;

    public Rewards() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_rewards, container, false);
        headerText = v.findViewById(R.id.headerText);
        availablePoints = v.findViewById(R.id.currentBalanceText);
        listView = v.findViewById(R.id.listView);
        List<String> listItems = new ArrayList<>();
        listItems.add("$25 Starbucks™ Giftcard  -  20 Points");
        listItems.add("$40 VISA™ Giftcard  -  30 Points");
        listItems.add("Free GreatClips™ Haircut  -  50 Points");
        listItems.add("50% Coupon for Walmart™  -  40 Points");
        listItems.add("Buy 1 Get 1 Free Bounty™ Paper Towels - 30 points");

        TextView listHeader = new TextView(getContext());
        listHeader.setText("  Available Rewards");
        listHeader.setTextSize(20);
        listHeader.setTextColor(Color.BLACK);
        listView.addHeaderView(listHeader, null, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

        FirebaseUser user1 = mAuth.getCurrentUser();
        String uid1 = user1.getUid();
        FirebaseDatabase.getInstance().getReference(uid1).child("firstName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String currValue = (String) dataSnapshot.getValue();
                headerText.append(currValue + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null && getActivity().getIntent().hasExtra("EXTRA_INFO")) {
            String placeName = extras.get("EXTRA_INFO").toString();
            headerText.setText("Thank you for visiting: " + placeName + ", ");

            if (placeName.equals("Lifetime Fitness")) {
                FirebaseUser user = mAuth.getCurrentUser();
                final String uid = user.getUid();

                FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long currValue = (Long) dataSnapshot.getValue();
                        FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").setValue((15 + currValue));
                        availablePoints.setText("Current Balance: " + (15 + currValue));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            } else if (placeName.equals("LA Fitness")) {
                FirebaseUser user = mAuth.getCurrentUser();
                final String uid = user.getUid();

                FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long currValue = (Long) dataSnapshot.getValue();
                        FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").setValue((20 + currValue));
                        availablePoints.setText("Current Balance: " + (20 + currValue));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

        if (extras != null && getActivity().getIntent().hasExtra("Fruit")) {
            String key = "Fruit";
            float value = extras.getFloat("Fruit");
            headerText.setText("Thank you for eating a fruit, ");
            Toast.makeText(getContext(), key + ": " + (value * 100) + "% chance", Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), "Here's 5 points! ", Toast.LENGTH_LONG).show();

            FirebaseUser user = mAuth.getCurrentUser();
            final String uid = user.getUid();

            FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long currValue = (Long) dataSnapshot.getValue();
                    FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").setValue((5 + currValue));
                    availablePoints.setText("Current Balance: " + (5 + currValue));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if (extras != null && getActivity().getIntent().hasExtra("Vegetable")) {
            String key = "Vegetable";
            float value = extras.getFloat("Vegetable");
            headerText.setText("Thank you for eating a vegetable, ");
            Toast.makeText(getContext(), key + ": " + (value * 100) + "% chance", Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), "Here's 5 points! ", Toast.LENGTH_LONG).show();

            FirebaseUser user = mAuth.getCurrentUser();
            final String uid = user.getUid();

            FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long currValue = (Long) dataSnapshot.getValue();
                    FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").setValue((5 + currValue));
                    availablePoints.setText("Current Balance: " + (5 + currValue));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirebaseUser user = mAuth.getCurrentUser();
                final String uid = user.getUid();

                if (id == 0) {
                    FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long currValue = (Long) dataSnapshot.getValue();
                            FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").setValue((currValue - 20));
                            availablePoints.setText("Current Balance: " + (currValue - 20));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else if (id == 1) {
                    FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long currValue = (Long) dataSnapshot.getValue();
                            FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").setValue((currValue - 30));
                            availablePoints.setText("Current Balance: " + (currValue - 30));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else if (id == 2) {
                    FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long currValue = (Long) dataSnapshot.getValue();
                            FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").setValue((currValue - 50));
                            availablePoints.setText("Current Balance: " + (currValue - 50));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else if (id == 3) {
                    FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long currValue = (Long) dataSnapshot.getValue();
                            FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").setValue((currValue - 40));
                            availablePoints.setText("Current Balance: " + (currValue - 40));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else if (id == 4) {
                    FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long currValue = (Long) dataSnapshot.getValue();
                            FirebaseDatabase.getInstance().getReference(uid).child("pointsBalance").setValue((currValue - 30));
                            availablePoints.setText("Current Balance: " + (currValue - 30));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

        return v;
    }
}
