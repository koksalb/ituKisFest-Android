package itukisfest.koksal.itukisfestcom.itukisfest;


        import android.content.ActivityNotFoundException;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.drawable.BitmapDrawable;
        import android.graphics.drawable.Drawable;
        import android.hardware.display.VirtualDisplay;
        import android.net.Uri;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v4.app.FragmentTransaction;
        import android.text.TextUtils;
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

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.ValueEventListener;
        import com.squareup.picasso.Callback;

        import android.view.ViewGroup;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;

        import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;
        import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;
        import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;
        import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
        import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter;
        import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.squareup.picasso.Picasso;
        import android.support.v7.graphics.Palette;
        import android.widget.ListView;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Collections;

        import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
        import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

        import static android.R.attr.fragment;
        import static java.sql.Types.NULL;


public class MainActivity extends AppCompatActivity implements ImageGalleryAdapter.ImageThumbnailLoader, FullScreenImageGalleryAdapter.FullScreenImageLoader, NavigationView.OnNavigationItemSelectedListener {

    private PaletteColorType paletteColorType;
    ArrayList<String> images = new ArrayList<String>();
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

        ImageGalleryActivity.setImageThumbnailLoader(this);
        ImageGalleryFragment.setImageThumbnailLoader(this);
        FullScreenImageGalleryActivity.setFullScreenImageLoader(this);

        // optionally set background color using Palette for full screen images
        paletteColorType = PaletteColorType.VIBRANT;


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);
        mDatabase.child("gallery").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                images.clear();
                Log.v("Test", "OnDataChange");
                for (DataSnapshot data : dataSnapshot.getChildren()){

                    imglr user = new imglr();
                    user.imageURL=data.getValue(String.class);
                    images.add(user.imageURL);


                    Log.v("Test", images.size() + "Images Taken");





                }
                Collections.reverse(images);

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });


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


            Intent intent = new Intent(MainActivity.this, ImageGalleryActivity.class);

            //String[] images = getResources().getStringArray(R.array.unsplash_images);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, new ArrayList<>(images));
            bundle.putString(ImageGalleryActivity.KEY_TITLE, "ITUKIS* FEST GALERI");
            intent.putExtras(bundle);

            startActivity(intent);
            /*
            fragment = new landscreenFragment();
            FragmentTransaction    ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_new, fragment);
            ft.commit();
            */
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    // region ImageGalleryAdapter.ImageThumbnailLoader Methods
    @Override
    public void loadImageThumbnail(final ImageView iv, String imageUrl, int dimension) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv.getContext())
                    .load(imageUrl)
                    .resize(dimension, dimension)
                    .centerCrop()
                    .into(iv);
        } else {
            iv.setImageDrawable(null);
        }
    }
    // endregion

    // region FullScreenImageGalleryAdapter.FullScreenImageLoader
    @Override
    public void loadFullScreenImage(final ImageView iv, String imageUrl, int width, final LinearLayout bgLinearLayout) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv.getContext())
                    .load(imageUrl)
                    .resize(width, 0)
                    .into(iv, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    applyPalette(palette, bgLinearLayout);
                                }
                            });
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else {
            iv.setImageDrawable(null);
        }
    }
    // endregion

    // region Helper Methods
    private void applyPalette(Palette palette, ViewGroup viewGroup){
        int bgColor = getBackgroundColor(palette);
        if (bgColor != -1)
            viewGroup.setBackgroundColor(bgColor);
    }

    private void applyPalette(Palette palette, View view){
        int bgColor = getBackgroundColor(palette);
        if (bgColor != -1)
            view.setBackgroundColor(bgColor);
    }

    private int getBackgroundColor(Palette palette) {
        int bgColor = -1;

        int vibrantColor = palette.getVibrantColor(0x000000);
        int lightVibrantColor = palette.getLightVibrantColor(0x000000);
        int darkVibrantColor = palette.getDarkVibrantColor(0x000000);

        int mutedColor = palette.getMutedColor(0x000000);
        int lightMutedColor = palette.getLightMutedColor(0x000000);
        int darkMutedColor = palette.getDarkMutedColor(0x000000);

        if (paletteColorType != null) {
            switch (paletteColorType) {
                case VIBRANT:
                    if (vibrantColor != 0) { // primary option
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) { // fallback options
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case LIGHT_VIBRANT:
                    if (lightVibrantColor != 0) { // primary option
                        bgColor = lightVibrantColor;
                    } else if (vibrantColor != 0) { // fallback options
                        bgColor = vibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case DARK_VIBRANT:
                    if (darkVibrantColor != 0) { // primary option
                        bgColor = darkVibrantColor;
                    } else if (vibrantColor != 0) { // fallback options
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case MUTED:
                    if (mutedColor != 0) { // primary option
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) { // fallback options
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                case LIGHT_MUTED:
                    if (lightMutedColor != 0) { // primary option
                        bgColor = lightMutedColor;
                    } else if (mutedColor != 0) { // fallback options
                        bgColor = mutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                case DARK_MUTED:
                    if (darkMutedColor != 0) { // primary option
                        bgColor = darkMutedColor;
                    } else if (mutedColor != 0) { // fallback options
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                default:
                    break;
            }
        }

        return bgColor;
    }
    // endregion


}