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
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class duyuruadapter extends ArrayAdapter<String>
{
    private int layoutResourceId;
    public List<String> contactlist;
    public LayoutInflater layoutInflater;
    public ViewHolder viewHolder;



    public duyuruadapter(Context context, int layoutResourceId, List<String> contactlist)
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
    public String       getItem(int position)
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
        TextView txtNotification;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        viewHolder = null;
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(layoutResourceId, parent,false);

            viewHolder = new ViewHolder();
            viewHolder.txtNotification = (TextView)convertView.findViewById(R.id.txtnotification);
           convertView.setTag(viewHolder);
            Typeface type = Typeface.createFromAsset(getContext().getAssets(),"fonts/Helvetica2.otf");
            viewHolder.txtNotification.setTypeface(type);


        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final String info = contactlist.get(position);


        viewHolder.txtNotification.setText(info);

        final String urlStr = info;






            viewHolder.txtNotification.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    String foundurl = "";

                    Matcher m = Patterns.WEB_URL.matcher(info);
                    while (m.find()) {
                        String url = m.group();
                        Log.d("TAG", "URL extracted: " + url);
                        foundurl=url;
                    }

if(URLUtil.isValidUrl(foundurl)) {

    Uri uriUrl = Uri.parse(foundurl);
    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
    getContext().startActivity(launchBrowser);
}
                }
            });





        return convertView;
    }
}

