package com.irpansyam.cataloguemovieuiux.activity;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.irpansyam.cataloguemovieuiux.R;
import com.irpansyam.cataloguemovieuiux.fragment.HomeFragment;
import com.irpansyam.cataloguemovieuiux.fragment.NowPlayingFragment;
import com.irpansyam.cataloguemovieuiux.fragment.SearchBarFragment;
import com.irpansyam.cataloguemovieuiux.fragment.SearchMovieFragment;
import com.irpansyam.cataloguemovieuiux.fragment.UpComingFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Nav View
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Search Instan", Snackbar.LENGTH_LONG)
                        .setAction("Go", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "Melakukan pencarian cepat....", Toast.LENGTH_SHORT).show();
                                SearchMovieFragment mSearchMovieFragment = new SearchMovieFragment();
                                FragmentManager mFragmentManager = getSupportFragmentManager();
                                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                                mFragmentTransaction.replace(R.id.fragment_page,mSearchMovieFragment, SearchMovieFragment.class.getSimpleName());
                                mFragmentTransaction.addToBackStack(null);
                                mFragmentTransaction.commit();
                            }
                        }).show();

            }
        });

        HomeFragment mHomeFragment = new HomeFragment();
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_page,mHomeFragment, HomeFragment.class.getSimpleName());
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    protected void onPause() {
        super.onPause();
        drawer.removeDrawerListener(toggle);
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
        getMenuInflater().inflate(R.menu.options_menu, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.masukkan_kata));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Mencari... " +query, Toast.LENGTH_SHORT ).show();

                SearchBarFragment mSearchBarFragment = new SearchBarFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString(SearchBarFragment.EXTRA_QUERY, "query");
                String resultQuery = query;
                mSearchBarFragment.setArguments(mBundle);
                mSearchBarFragment.setJudulFilm(resultQuery);
                FragmentManager mFragmentManager = getSupportFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_page, mSearchBarFragment, SearchBarFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu2) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String title;
        if (id == R.id.nav_home){
            title ="Home";
            getSupportActionBar().setTitle(title);
            HomeFragment mmHomeFragment = new HomeFragment();
            FragmentManager mmFragmentManager = getSupportFragmentManager();
            FragmentTransaction mmFragmentTransaction = mmFragmentManager.beginTransaction();
            mmFragmentTransaction.replace(R.id.fragment_page,mmHomeFragment, HomeFragment.class.getSimpleName());
            mmFragmentTransaction.addToBackStack(null);
            mmFragmentTransaction.commit();

        }else if (id == R.id.nav_now_playing) {
            title ="Now Playing";
            getSupportActionBar().setTitle(title);
            NowPlayingFragment mNowPlayingFragment = new NowPlayingFragment();
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.fragment_page,mNowPlayingFragment, NowPlayingFragment.class.getSimpleName());
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();

        } else if (id == R.id.nav_coming_soon) {
            title ="Up Coming";
            getSupportActionBar().setTitle(title);
            UpComingFragment mUpComingFragment = new UpComingFragment();
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.fragment_page,mUpComingFragment, UpComingFragment.class.getSimpleName());
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();

        } else if (id == R.id.nav_search) {
            title = "Search";
            getSupportActionBar().setTitle(title);
            SearchMovieFragment mSearchMovieFragment = new SearchMovieFragment();
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.fragment_page,mSearchMovieFragment, SearchMovieFragment.class.getSimpleName());
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
