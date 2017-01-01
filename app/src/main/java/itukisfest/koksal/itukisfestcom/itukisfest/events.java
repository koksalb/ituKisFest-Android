package itukisfest.koksal.itukisfestcom.itukisfest;

import android.content.Context;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by koksa on 28.10.2016.
 */

public class events implements Comparable<events> {

    public String date;
    public String description;
    public String imageURL;
    public String location;
    public String name;

    public events() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public events(String date, String description,String imageURL,String location,String name) {
        this.date =date ;
        this.description= description;
        this.imageURL=imageURL ;
        this.location =location ;
        this.name = name;
    }




    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("description", description);
        result.put("imageURL", imageURL);
        result.put("location", location);
        result.put("name", name);

        return result;
    }


    @Override
    public int compareTo(events o2) {


        String[] separated1 = date.split(" ");
        String[] separated2 = o2.date.split(" ");



        StringBuffer tc1 = new StringBuffer("");
        tc1.append((separated1[1].charAt(6)));
        tc1.append((separated1[1].charAt(7)));
        tc1.append((separated1[1].charAt(8)));
        tc1.append((separated1[1].charAt(9)));

        tc1.append((separated1[1].charAt(3)));
        tc1.append((separated1[1].charAt(4)));

        tc1.append((separated1[1].charAt(0)));
        tc1.append((separated1[1].charAt(1)));

        tc1.append((separated1[0].charAt(0)));
        tc1.append((separated1[0].charAt(1)));
        tc1.append((separated1[0].charAt(3)));
        tc1.append((separated1[0].charAt(4)));


        StringBuffer tc2= new StringBuffer("");

        tc2.append((separated2[1].charAt(6)));
        tc2.append((separated2[1].charAt(7)));
        tc2.append((separated2[1].charAt(8)));
        tc2.append((separated2[1].charAt(9)));

        tc2.append((separated2[1].charAt(3)));
        tc2.append((separated2[1].charAt(4)));

        tc2.append((separated2[1].charAt(0)));
        tc2.append((separated2[1].charAt(1)));

        tc2.append((separated2[0].charAt(0)));
        tc2.append((separated2[0].charAt(1)));
        tc2.append((separated2[0].charAt(3)));
        tc2.append((separated2[0].charAt(4)));




        return tc2.toString().compareTo(tc1.toString());

    }




}

