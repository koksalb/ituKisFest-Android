package itukisfest.koksal.itukisfestcom.itukisfest;


        import android.content.ActivityNotFoundException;
        import android.content.Intent;
        import android.net.Uri;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v4.app.FragmentTransaction;
        import android.util.Log;
        import android.view.View;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;

        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.Button;
        import android.widget.LinearLayout;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
        import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

        import static android.R.attr.fragment;
        import static java.sql.Types.NULL;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Burasi henuz yapilmadi", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);



        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment;
        fragment = new eventFragment();
        FragmentTransaction    ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_new, fragment);
        ft.commit();






    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        LinearLayout test = null;

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        else {
            test = (LinearLayout) findViewById(R.id.ll2);
           // super.onBackPressed();
        }

       if(test!=null && test.getVisibility()==View.VISIBLE)
        {
            LinearLayout backbutton = (LinearLayout) findViewById(R.id.backlayout);
            backbutton.callOnClick();
            return;
        }
        else
       {
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

            Uri uriUrl = Uri.parse("http://berkaykoksal.com/");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {



        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;


        if (id == R.id.nav_etkinlikler) {
            fragment = new eventFragment();
            FragmentTransaction   ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_new,fragment);
            ft.commit();


        } else if (id == R.id.nav_duyurular) {
            fragment = new duyurularFragment();
            FragmentTransaction    ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_new, fragment);
            ft.commit();
        }
        else if (id == R.id.nav_instagram) {
            Uri uriUrl = Uri.parse("https://www.instagram.com/_u/itukisfest/");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            try {
                startActivity(launchBrowser);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/itukisfest/")));
            }


        } else if (id == R.id.nav_facebook) {

            Uri uriUrl = Uri.parse("https://www.facebook.com/itukisfest/");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        startActivity(launchBrowser);

        }
        else if (id == R.id.nav_twitter) {
            Uri uriUrl = Uri.parse("https://twitter.com/itukisfest/");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        }
        else if (id == R.id.nav_galeri)
        {
            fragment = new landscreenFragment();
            FragmentTransaction    ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_new, fragment);
            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}