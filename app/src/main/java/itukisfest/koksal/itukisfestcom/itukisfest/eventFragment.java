package itukisfest.koksal.itukisfestcom.itukisfest;

/**
 * Created by koksa on 26.10.2016.
 */
import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * A simple {@link Fragment} subclass.
 */




public class eventFragment extends Fragment {
   public events eventlist[]= new events[100];
   public List<events> eventlistt = new ArrayList<events>();
   public int eventcount=0;
    public DatabaseReference mDatabase;
    public  ToggleButton toggleButton;
    public eventFragment() {
        // Required empty public constructor



       // yazma komutu  mDatabase.child("events").child("123").setValue(user);




    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventlistt.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()){



                    mDatabase.child("events").child(String.valueOf(data.getKey())).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    events user = new events();
                                    user = dataSnapshot.getValue(events.class);
                                    eventlist[eventcount]=user;
                                    eventlistt.add(new events(user.date, user.description,user.imageURL,user.location,user.name));
                                    eventcount++;
                                    Collections.sort(eventlistt);
                                    listadapter adapter = new listadapter(getContext(), R.layout.row_events,eventlistt);
                                    ListView lw = (ListView) getView().findViewById(android.R.id.list);
                                    lw.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    lw.invalidateViews();

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                }





                Log.v("Test", "List Taken");



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








        //Berkay




        View view = inflater.inflate(R.layout.fragment_event, container, false);


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

        return view;
    }



}
