package itukisfest.koksal.itukisfestcom.itukisfest;

/**
 * Created by koksa on 26.10.2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class duyurularFragment extends Fragment {
    public String eventlist[]= new String[100];
    public List<String> eventlistt = new ArrayList<String>();
    public int eventcount=0;
    public DatabaseReference mDatabase;


    public duyurularFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventlistt.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){



                    mDatabase.child("notifications").child(String.valueOf(data.getKey())).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    String user;
                                    user = dataSnapshot.getValue(String.class);
                                    eventlist[eventcount]=user;
                                    eventlistt.add(user);
                                    eventcount++;

                                    ArrayAdapter<String> adapter = new  ArrayAdapter<String>(getContext(), R.layout.mytextview,eventlistt);
                                    ListView lw = (ListView) getView().findViewById(R.id.list);
                                    lw.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    lw.invalidateViews();


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
        return inflater.inflate(R.layout.fragment_duyurular, container, false);
    }

}
