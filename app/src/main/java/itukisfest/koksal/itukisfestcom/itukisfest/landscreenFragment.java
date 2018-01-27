package itukisfest.koksal.itukisfestcom.itukisfest;

/**
 * Created by koksa on 26.10.2016.
 */
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.webkit.WebView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import uk.co.senab.photoview.PhotoViewAttacher;

import static android.R.attr.bitmap;
import static android.R.attr.description;
import static android.R.attr.path;
import static android.view.View.GONE;
import static itukisfest.koksal.itukisfestcom.itukisfest.R.id.backoff;
import static itukisfest.koksal.itukisfestcom.itukisfest.R.id.imageView;


/**
 * A simple {@link Fragment} subclass.
 */




public class landscreenFragment extends Fragment {
    public List<imglr> eventlistt = new ArrayList<imglr>();
    public int eventcount=0;
    public DatabaseReference mDatabase;
    public  ToggleButton toggleButton;
    public int latestposition = 0;
    public landscreenFragment() {
        // Required empty public constructor



        // yazma komutu  mDatabase.child("events").child("123").setValue(user);




    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);
        mDatabase.child("gallery").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventlistt.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()){



                    mDatabase.child("gallery").child(String.valueOf(data.getKey())).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    imglr user = new imglr();
                                    String read;
                                    read = dataSnapshot.getValue(String.class);
                                    user.imageURL=read;

                                    Collections.reverse(eventlistt);
                                    eventlistt.add(new imglr(user.imageURL));
                                    eventcount++;

                                    Collections.reverse(eventlistt);
                                    CustomAdapter adapter = new CustomAdapter(getContext(), R.layout.recylerrow,eventlistt);
                                    ListView lw = (ListView) getView().findViewById(android.R.id.list);
                                    lw.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    lw.invalidateViews();
                                    Log.v("Test", "List Taken");


                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });


                }






            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });





    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);
        // Inflate the layout for this fragment




        View view = inflater.inflate(R.layout.fragment_landscreen, container, false);





        final LinearLayout ll11 = (LinearLayout)view.findViewById(R.id.ll1);
        final LinearLayout ll22 = (LinearLayout)view.findViewById(R.id.ll2);
        ll11.setVisibility(View.VISIBLE);
        ll22.setVisibility(View.GONE);
        toggleButton = (ToggleButton) view.findViewById(R.id.togglerbutton2);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ListView lw2;
                    lw2 = (ListView) getView().findViewById(android.R.id.list);
                    lw2.setVisibility(View.GONE);
                }else{
                    ListView lw2;
                    lw2 = (ListView) getView().findViewById(android.R.id.list);
                    lw2.setVisibility(View.VISIBLE);

                }
            }
        });




        final ListView lw3;
        lw3 = (ListView) view.findViewById(android.R.id.list);
        final ImageView imgbig = (ImageView)view.findViewById(R.id.buyukresim);

        final LinearLayout ll1 = (LinearLayout)view.findViewById(R.id.ll1);
        final LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.ll2);
        final LinearLayout backlayout = (LinearLayout)view.findViewById(R.id.backlayout);

        final LinearLayout swiper = (LinearLayout)view.findViewById(R.id.swiper);

swiper.setOnTouchListener(new View.OnTouchListener() {
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        Log.d("test","LEFT");
                    latestposition = latestposition -1;
                        if(latestposition<0)
                        {latestposition=0;
                            Toast.makeText(getContext(),"Zaten en baştasın :)",Toast.LENGTH_LONG).show();
                        }
                        else {
                            imglr test = (imglr) lw3.getAdapter().getItem(latestposition);


                            Picasso
                                    .with(getContext())
                                    .load(test.imageURL)
                                    .into(imgbig);


                            PhotoViewAttacher mAttacher;
                            mAttacher = new PhotoViewAttacher(imgbig);
                            mAttacher.update();
                        }
                    }

                    // Right to left swipe action
                    else
                    {
                        Log.d("test","RIGHT");

                        latestposition = latestposition +1;
                        if(latestposition>lw3.getAdapter().getCount()-1)
                        {latestposition=lw3.getAdapter().getCount()-1;
                        Toast.makeText(getContext(),"Sona geldin :)",Toast.LENGTH_LONG).show();

                        }
                        else {
                            imglr test = (imglr) lw3.getAdapter().getItem(latestposition);


                            Picasso
                                    .with(getContext())
                                    .load(test.imageURL)
                                    .into(imgbig);

                            PhotoViewAttacher mAttacher;
                            mAttacher = new PhotoViewAttacher(imgbig);
                            mAttacher.update();
                        }

                    }







                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return true;
    }
});






        lw3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                latestposition = position;

                imglr test = (imglr)lw3.getAdapter().getItem(position);


                Picasso
                        .with(getContext())
                        .load(test.imageURL)
                        .into(imgbig);


                PhotoViewAttacher mAttacher;
                mAttacher = new PhotoViewAttacher(imgbig);
                mAttacher.update();

                ll1.setVisibility(View.GONE);
                ll2.setVisibility(View.VISIBLE);
            }
        });

        backlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll2.setVisibility(View.GONE);
                ll1.setVisibility(View.VISIBLE);


                Bitmap finalBitmap = imgbig.getDrawingCache();



            }
        });


        return view;
    }


}


