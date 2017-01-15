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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class listadapter extends ArrayAdapter<events>
{
    private int layoutResourceId;
    public List<events> contactlist;
    public LayoutInflater layoutInflater;
    public ViewHolder viewHolder;



    public listadapter(Context context, int layoutResourceId, List<events> contactlist)
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
    public events       getItem(int position)
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
        TextView txtDate,txtDate2, txtDescription, txtLocation, txtName;
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

            viewHolder.txtName = (TextView)convertView.findViewById(R.id.txtname);

            viewHolder.txtDate = (TextView)convertView.findViewById(R.id.txtdate);

            viewHolder.txtDate2 = (TextView)convertView.findViewById(R.id.txtdate2);

            viewHolder.txtLocation = (TextView)convertView.findViewById(R.id.txtlocation);

            viewHolder.txtDescription = (TextView)convertView.findViewById(R.id.txtdescription);

            viewHolder.img = (ImageView)convertView.findViewById(R.id.imageView);

            Typeface type = Typeface.createFromAsset(getContext().getAssets(),"fonts/Helvetica2.otf");
            viewHolder.txtDescription.setTypeface(type);
            viewHolder.txtLocation.setTypeface(type);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }


       final events info = contactlist.get(position);


        viewHolder.txtName.setText(info.name);


        String[] separated = info.date.split(" ");


        viewHolder.txtDate.setText(separated[0]);

        viewHolder.txtDate2.setText(separated[1]);

        String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());


        StringBuffer tc1 = new StringBuffer("");
        tc1.append((separated[1].charAt(6)));
        tc1.append((separated[1].charAt(7)));
        tc1.append((separated[1].charAt(8)));
        tc1.append((separated[1].charAt(9)));

        tc1.append((separated[1].charAt(3)));
        tc1.append((separated[1].charAt(4)));

        tc1.append((separated[1].charAt(0)));
        tc1.append((separated[1].charAt(1)));

        tc1.append((separated[0].charAt(0)));
        tc1.append((separated[0].charAt(1)));
        tc1.append((separated[0].charAt(3)));
        tc1.append((separated[0].charAt(4)));

        if(date.compareTo(tc1.toString())>0)
        {
            viewHolder.txtDate.setTextColor(Color.parseColor("#DF151A"));
            viewHolder.txtDate2.setTextColor(Color.parseColor("#DF151A"));

        }
        else
        {
            viewHolder.txtDate.setTextColor(Color.parseColor("#00DA3C"));
            viewHolder.txtDate2.setTextColor(Color.parseColor("#00DA3C"));

        }



        viewHolder.txtDescription.setText(info.description);





        viewHolder.txtLocation.setText(info.location);


        Picasso
                .with(getContext())
                .load(info.imageURL)
                .into(viewHolder.img);



        return convertView;
    }
}
