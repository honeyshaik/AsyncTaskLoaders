package com.example.muneiahtellakula.asynctaskants;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Muneiah Tellakula on 16-05-2018.
 */

public class AsynckTaskLoaders extends AsyncTaskLoader<String>
{
    public static final String Base_URL="https://www.googleapis.com/books/v1/volumes?";
    public static final String Query_params="q";
    public static final String Max_results="10";
    public static final String Print_type="all";
    String book_name;

    public AsynckTaskLoaders(MainActivity mainActivity,
                             String queryBook) {
        super(mainActivity);
        book_name=queryBook;
    }

    @Override
    public String loadInBackground() {
        Uri uri=Uri.parse(Base_URL).buildUpon()
                .appendQueryParameter(Query_params,
                        String.valueOf(book_name))
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
    protected void onStartLoading() {
        //super.onStartLoading();
        forceLoad();
    }
}
