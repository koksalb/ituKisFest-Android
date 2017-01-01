package itukisfest.koksal.itukisfestcom.itukisfest;

/**
 * Created by koksa on 1.11.2016.
 */
/**
 * Created by berkay.koksal on 30.6.2016.
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class CustomAdapter extends ArrayAdapter<imglr>
{
    private int layoutResourceId;
    public List<imglr> contactlist;
    public LayoutInflater layoutInflater;
    public ViewHolder viewHolder;



    public CustomAdapter(Context context, int layoutResourceId, List<imglr> contactlist)
    {

        super(context, layoutResourceId,  contactlist);

        this.layoutResourceId = layoutResourceId;
        this.contactlist = contactlist;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount()
    {
        if (contactlist != null)
            return contactlist.size();
        else
            return 0;
    }


    @Override
    public imglr       getItem(int position)
    {
        return contactlist.get(position);
    }



    


    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public static class ViewHolder
    {
        TextView txtDate, txtDescription, txtLocation, txtName;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        viewHolder = null;
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(layoutResourceId, parent,false);

            viewHolder = new ViewHolder();


            viewHolder.img = (ImageView)convertView.findViewById(R.id.imageView2);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final imglr info = contactlist.get(position);




        Picasso
                .with(getContext())
                .load(info.imageURL)
                .into(viewHolder.img);




        return convertView;
    }
}

