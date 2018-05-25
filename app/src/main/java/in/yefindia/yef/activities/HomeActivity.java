package in.yefindia.yef.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.yefindia.yef.R;
import in.yefindia.yef.utils.Utils;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    FirebaseDatabase database;
    DatabaseReference ref;
    private TextView userName;
    private TextView userEmail;
    private ImageView verIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupupFirebseAuth();
        setupNavigationView();
        getAndSetUserData();
    }

    private void getAndSetUserData() {
        userName=navigationView.getHeaderView(0).findViewById(R.id.userNameNavigationHeader);
        userEmail=navigationView.getHeaderView(0).findViewById(R.id.userEmailNavigationHeader);
        verIcon=navigationView.getHeaderView(0).findViewById(R.id.imageVerifiedUser);
        final FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser!=null){
            if(currentUser.isEmailVerified()){
                database=FirebaseDatabase.getInstance();
                ref=database.getReference(Utils.FIREBASE_USERS_CHILD_NODE);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userName.setText(dataSnapshot.child(currentUser.getUid()).child("fullName").getValue(String.class));
                        userEmail.setText(dataSnapshot.child(currentUser.getUid()).child("email").getValue(String.class));
                        verIcon.setImageResource(R.drawable.ic_check_circle_white_11dp);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

    }
    }


    private void setupNavigationView() {
        drawerLayout=findViewById(R.id.drawerHomeNavigation);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_drawer);


        navigationView=findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                // set item as selected to persist highlight
                item.setChecked(true);
                // close drawer when item is tapped
                drawerLayout.closeDrawers();
                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAuthenticationState();
    }

    public void checkAuthenticationState(){
        Log.d(TAG,"checkAuthenticationState: checking authenticating state");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null)
        {
            Log.d(TAG,"checkAuthenticationState: user is null, navigating back to login screen.");
            Intent intent = new Intent(HomeActivity.this,SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else{
            Log.d(TAG,"checkAuthenticationState: user is Authenticated");
        }
    }

    private void setupupFirebseAuth(){
        Log.d(TAG,"setUpFirebaseAuth : started");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null){
                Log.d(TAG,"checkAuthenticationState: user is null, navigating back to login screen.");
                Intent intent = new Intent(HomeActivity.this,SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                }else{
                    //user is signed out
                    Log.d(TAG,"OnAuthStateChenged:signed_out:");
                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }



}
