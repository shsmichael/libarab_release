package com.example.michaelg.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.michaelg.myapplication.Fragments.AboutFragment;
import com.example.michaelg.myapplication.Fragments.FavoritesFragment;
import com.example.michaelg.myapplication.Fragments.SearchTabHostFragment;
import com.example.michaelg.myapplication.Fragments.MenuFragment;
import com.example.michaelg.myapplication.Fragments.PreferenceFragment;
import com.example.michaelg.myapplication.Fragments.SettingsFragment;
import com.example.michaelg.myapplication.Fragments.TriviaFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    int userType;
    User myuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set hebrew language
        /*
        Locale locale = new Locale("iw");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //
        */
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        Intent intent = getIntent();
        //userType = intent.getIntExtra("Type",0);
        myuser =(User) intent.getSerializableExtra("user");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //set main frag
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        MenuFragment menufragment = new MenuFragment();
        FragmentManager manager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        //bundle.put("Type", userType);
        bundle.putSerializable("user",myuser);
        menufragment.setArguments(bundle);
        manager.beginTransaction().replace(R.id.fragment_main,
                menufragment,
                menufragment.getTag()
        ).commit();
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
            //set settings frag
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSettings)));
            SettingsFragment settingsfragment = new SettingsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_main,
                    settingsfragment,
                    settingsfragment.getTag()
            ).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
            MenuFragment menufragment = new MenuFragment();
            FragmentManager manager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putInt("Type", userType);
            menufragment.setArguments(bundle);
            manager.beginTransaction().replace(R.id.fragment_main,
                    menufragment,
                    menufragment.getTag()
            ).commit();


        } else if (id == R.id.nav_search) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSearch)));
            SearchTabHostFragment mainfragmenttest = new SearchTabHostFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_main,
                    mainfragmenttest,
                    mainfragmenttest.getTag()
            ).commit();

        } else if (id == R.id.nav_favorites) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorFav)));
            FavoritesFragment favoritesfragment = new FavoritesFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_main,
                    favoritesfragment,
                    favoritesfragment.getTag()
            ).commit();

        } else if (id == R.id.nav_trivia) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorTrivia)));
            TriviaFragment triviafragment = new TriviaFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_main,
                    triviafragment,
                    triviafragment.getTag()
            ).commit();
        } else if (id == R.id.nav_settings) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSettings)));
            PreferenceFragment settingsfragment = new PreferenceFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_main,
                    settingsfragment,
                    settingsfragment.getTag()
            ).commit();
        } else if (id == R.id.nav_about) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAbout)));
            AboutFragment aboutfragment = new AboutFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_main,
                    aboutfragment,
                    aboutfragment.getTag()
            ).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}