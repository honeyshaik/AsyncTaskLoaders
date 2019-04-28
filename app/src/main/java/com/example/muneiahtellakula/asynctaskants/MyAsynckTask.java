package com.example.muneiahtellakula.asynctaskants;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Muneiah Tellakula on 14-05-2018.
 */

public class MyAsynckTask extends AsyncTask<String,Void,String>
{
    TextView title_tv,author_tv,description_tv;
    ImageView iv;
    Context context;
    /*public static final String BASE_URL="https://www.googleapis.com/books/v1/volumes?";
    public static final String QUERY_PARAMETER="q";
    public static final String MAX_RESULT="10";
    public static final String PRINT_TYPE="all";
*/
    public static final String Base_URL="https://www.googleapis.com/books/v1/volumes?";
    public static final String Query_params="q";
    public static final String Max_results="10";
    public static final String Print_type="all";
    public MyAsynckTask(MainActivity mainActivity,
                        TextView title, TextView author,
                        TextView description, ImageView
                                mageView)
    {
        this.context=mainActivity;
        this.title_tv=title;
        this.description_tv=description;
        this.author_tv=author;
        this.iv=mageView;
    }

    @Override
    protected String doInBackground(String... strings)
    {
        Uri uri=Uri.parse(Base_URL).buildUpon()
                .appendQueryParameter(Query_params,strings[0])
                .appendQueryParameter(Max_results,"10")
                .appendQueryParameter(Print_type,"book")
                .build();
        try {
            URL url=new URL(uri.toString());
            HttpURLConnection http= (HttpURLConnection)
                    url.openConnection();
                    http.setRequestMethod("GET");
                    http.connect();
            InputStream is=http.getInputStream();
            BufferedReader br=new BufferedReader(new
                    InputStreamReader(is));
            StringBuffer sb=new StringBuffer();
            String line="";
            while ((line=br.readLine())!=null){
                sb.append(line+"\n");
            }
            String result=sb.toString();
            return  result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        //Toast.makeText(context, s+"", Toast.LENGTH_SHORT).show();
        try {
            JSONObject rootObj=new JSONObject(s);
            JSONArray item=rootObj.getJSONArray("items");
            JSONObject itemObject=item.getJSONObject(0);
            JSONObject volumeinfo=itemObject.getJSONObject("volumeInfo");
            String title=volumeinfo.getString("title");
            title_tv.setText("Title -->"+title);
            JSONArray author=volumeinfo.getJSONArray("authors");
            String result_auth=author.getString(0);
            author_tv.setText("Author -->"+result_auth);
            String des=volumeinfo.getString("description");
            description_tv.setText("Description -->"+des);
            JSONObject image=volumeinfo.getJSONObject("imageLinks");
            String imgLink=image.getString("thumbnail");
            Picasso.with(context).load(imgLink).into(iv);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
