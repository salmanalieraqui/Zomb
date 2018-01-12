package com.appmilitia.zomb.zomb;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LoginButton loginButton;
    CallbackManager callbackManager;
    LocationManager locationManager;
    LocationListener locationListener;
    static  final int REQUEST_LOCATION=1;
    String first_name, last_name, email, id, birthday, gender;
    double latti,longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // LOCATION CODE
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();


        //facebook login code.......................
        //........................................
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                String userId = loginResult.getAccessToken().getUserId();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        displayUserInfo(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name, last_name, email, id, birthday, gender");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

     void getLocation() {
         if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION ) !=
                 PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                 != PackageManager.PERMISSION_GRANTED){
             ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

         } else {
             Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
             if(location !=null) {
                 latti=location.getLatitude();
                 longi=location.getLongitude();

             }
             else
             {
                 latti=0.0;
                 longi=0.0;
             }
         }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }

    public void displayUserInfo(JSONObject object) {

        first_name = "";
        last_name = "";
        email = "";
        id = "";
        birthday= "";
        gender= "";
        try {


            if(object.has("first_name"))
                first_name = object.getString("first_name");
            if(object.has("last_name"))
                last_name = object.getString("last_name");
            if (object.has("email"))
                email = object.getString("email");
            if (object.has("id"))
                id=object.getString("id");
            if (object.has("birthday"))
                birthday = object.getString("birthday");
            if (object.has("gender"))
                gender = object.getString("gender");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //new code for navigation.........
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView profilePictureView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        TextView tv_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.TV_name);
        TextView tv_email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.TV_email);
        TextView tv_id = (TextView) navigationView.getHeaderView(0).findViewById(R.id.TV_id);
        TextView tv_birthday = (TextView) navigationView.getHeaderView(0).findViewById(R.id.TV_birthday);
        TextView tv_gender = (TextView) navigationView.getHeaderView(0).findViewById(R.id.TV_gender);
        Picasso.with(this).load( "https://graph.facebook.com/"+id+"/picture?width=250&height=200").into(profilePictureView);
        tv_name.setText("Name: " + first_name + " " + last_name);
        tv_email.setText("Email: " + email);
        tv_id.setText("Id: " + id);
        tv_birthday.setText("Birthday: "+birthday);
        tv_gender.setText("Gender: "+gender);
        TextView loca_tv=(TextView)findViewById(R.id.TV_location);
        loca_tv.setText("Lattitue: "+latti+" Longitute: "+longi);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
