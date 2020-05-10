package com.example.healthapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthapp.navigationdata.DataModel;
import com.example.healthapp.navigationdata.DrawerItemCustomAdapter;
import com.example.healthapp.util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DashboardClean extends AppCompatActivity implements View.OnClickListener {
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;
    ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private TextView mTitle_toolbar;
    private LinearLayout layoutDashboard, layoutDoctorSource, layoutMedicalId;
    private ImageView dashboardImg, doctorImg, medicalImg;
    private TextView dashboardTxt, doctorTxt, medicalTxt;
    private String userID,fname,lname;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userRef,doctorApprovalRef;
    DataSnapshot dataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_clean);
        mTitle = getTitle();
        firebaseAuth=FirebaseAuth.getInstance();
        userID=firebaseAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("User").child(userID);
        doctorApprovalRef = FirebaseDatabase.getInstance().getReference().child("DoctorApprovalRequests").child(userID);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fname=dataSnapshot.child("First Name").getValue().toString();
                lname=dataSnapshot.child("Last Name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        setupToolbar();
        DataModel[] drawerItem = new DataModel[10];
        drawerItem[0] = new DataModel(R.drawable.connect, "Connect");
        drawerItem[1] = new DataModel(R.drawable.ic_person_black, "My Profile");
        drawerItem[2] = new DataModel(R.drawable.ic_chat_black, "Messages");
        drawerItem[3] = new DataModel(R.drawable.ic_online_black, "Online Consultation");
        drawerItem[4] = new DataModel(R.drawable.ic_subscriptions_black, "Subscription");
        drawerItem[5] = new DataModel(R.drawable.ic_contacts_black, "Contacts");
        drawerItem[6] = new DataModel(R.drawable.ic_account_box_black, "Accounts");
        drawerItem[7] = new DataModel(R.drawable.ic_doctor_approval, "Request Approval");
        drawerItem[8] = new DataModel(R.drawable.ic_settings_black, "Settings");
        drawerItem[9] = new DataModel(R.drawable.ic_logout_black, "Logout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();

        layoutDashboard = findViewById(R.id.layoutDashboard);
        layoutDoctorSource = findViewById(R.id.layoutDoctorSource);
        layoutMedicalId = findViewById(R.id.layoutMedicalId);

        dashboardImg = findViewById(R.id.dashboardImg);
        dashboardTxt = findViewById(R.id.dashboardTxt);
        doctorImg = findViewById(R.id.doctorImg);
        doctorTxt = findViewById(R.id.doctorTxt);
        medicalImg = findViewById(R.id.medicalImg);
        medicalTxt = findViewById(R.id.medicalTxt);

        layoutDashboard.setOnClickListener(this);
        layoutDoctorSource.setOnClickListener(this);
        layoutMedicalId.setOnClickListener(this);

        setTitle("Dashboard");
        replaceFragment(new MapAndDoctorFragment());
        setSelected(0);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()){
            case R.id.layoutDashboard:
                fragment = new MapAndDoctorFragment();
                setTitle("Dashboard");
                replaceFragment(fragment);
                setSelected(0);
                break;
            case R.id.layoutDoctorSource:
                fragment = new DoctorListFragment();
                setTitle("Doctor Source");
                replaceFragment(fragment);
                setSelected(1);
                break;
            case R.id.layoutMedicalId:
                fragment = new MedicalIDFragment();
                setTitle("Medical ID");
                replaceFragment(fragment);
                setSelected(2);
                break;
        }
    }

    private void setSelected(int tab) {
        Utils.changeImageViewColor(this, dashboardImg, R.color.light_gray);
        dashboardTxt.setTextColor(getResources().getColor(R.color.light_gray));

        Utils.changeImageViewColor(this, doctorImg, R.color.light_gray);
        doctorTxt.setTextColor(getResources().getColor(R.color.light_gray));

        Utils.changeImageViewColor(this, medicalImg, R.color.light_gray);
        medicalTxt.setTextColor(getResources().getColor(R.color.light_gray));

        if(tab == 0){
            Utils.changeImageViewColor(this, dashboardImg, R.color.white);
            dashboardTxt.setTextColor(getResources().getColor(R.color.white));
        }
        else if(tab == 1){
            Utils.changeImageViewColor(this, doctorImg, R.color.white);
            doctorTxt.setTextColor(getResources().getColor(R.color.white));
        }
        else if(tab == 2){
            Utils.changeImageViewColor(this, medicalImg, R.color.white);
            medicalTxt.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("position ","position "+position);
            if (position > 0)
                selectItem(position-1);
        }

    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
            case 3:
            case 4:
            case 5:
            case 6: requestApproval();
            case 7:
//                mDrawerList.setItemChecked(position, true);
//                mDrawerList.setSelection(position);
//                setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
//                fragment = new ConnectFragment();
                break;
            case 1:
//                mDrawerList.setItemChecked(position, true);
//                mDrawerList.setSelection(position);
//                setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                startActivity(new Intent(this, ChatActivity.class));
//                fragment = new FixturesFragment();
                break;
            case 2:
                mDrawerLayout.closeDrawer(mDrawerList);
                startActivity(new Intent(this, DoctorListActivity.class));
                break;
            case 8:
                mDrawerLayout.closeDrawer(mDrawerList);
                Intent intent = new Intent(this,
                        MainActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               firebaseAuth.signOut();
                startActivity(intent);
                finishAffinity();
                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                setTitle(mNavigationDrawerItemTitles[position]);
               mDrawerLayout.closeDrawer(mDrawerList);
           //    fragment = new TableFragment();
               break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }



    void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            ;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment, fragment.toString());
//        fragmentTransaction.addToBackStack(fragment.toString());
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        mTitle_toolbar.setText(mTitle);
//        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        mDrawerToggle.syncState();
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void setupDrawerToggle(){
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

    private void requestApproval() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardClean.this);
        builder.setMessage("Are you a doctor and want to get approval?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendRequestApproval();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog Builder = builder.create();
        Builder.setTitle("Please confirm");
        Builder.show();
        Builder.getWindow().setBackgroundDrawableResource(R.color.white);
        Builder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#000000"));
        Builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));

    }

    public void sendRequestApproval(){
       // Toast.makeText(DashboardClean.this, fname, Toast.LENGTH_SHORT).show();

        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(DashboardClean.this);
        progressDialog.setMessage("Sending request...");
        progressDialog.show();

            HashMap<String, Object> map = new HashMap<>();
            map.put("FirstName", fname);
            map.put("LastName", lname);
            map.put("RequestStatus", "Sent");
            doctorApprovalRef.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            AlertDialog.Builder reqSent = new AlertDialog.Builder(DashboardClean.this);
                            reqSent.setMessage("Your request to be approved as a doctor has been sent. Thank you");
                            reqSent.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();;
                                }
                            });
                            AlertDialog Builder = reqSent.create();
                            Builder.show();
                            Builder.getWindow().setBackgroundDrawableResource(R.color.white);
                            Builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
                        }

                    }
                });

    }

}
