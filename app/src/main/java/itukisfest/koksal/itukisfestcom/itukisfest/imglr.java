package itukisfest.koksal.itukisfestcom.itukisfest;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by koksa on 28.10.2016.
 */
@IgnoreExtraProperties
public class imglr {


    public String imageURL;


    public imglr() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public imglr(String imageURL) {

        this.imageURL=imageURL ;

    }

    public String getImageURL()
    {
        return this.imageURL;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("imageURL", imageURL);

        return result;
    }



}
