package com.example.muneiahtellakula.asynctaskants;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    EditText b_name;
    TextView title1,author1,description1;
    ImageView imageView;
    MyAsynckTask asynckTask;
    AsynckTaskLoaders taskLoader;
    LoaderManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_name=findViewById(R.id.book_name);
        title1=findViewById(R.id.title_book);
        author1=findViewById(R.id.author_book);
        description1=findViewById(R.id.discription_book);
        imageView=findViewById(R.id.image_book);
       /* asynckTask=new MyAsynckTask(this,
                title1,author,description,imageView);*/
      //  taskLoader=new AsynckTaskLoaders(this,b_name);
        lm=getLoaderManager();
        if (lm.getLoader(0)!=null){
            lm.initLoader(0,null,this);
        }
    }

    public void search_book(View view)
    {
        String name=b_name.getText().toString();
        Bundle bundle=new Bundle();
        bundle.putString("queryBook",name);
        lm.initLoader(0,bundle,this);
             // asynckTask.execute(name);

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new  AsynckTaskLoaders(this,
                args.getString("queryBook"));
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        try {
            JSONObject rootObj=new JSONObject((String) data);
            JSONArray item=rootObj.getJSONArray("items");
            JSONObject itemObject=item.getJSONObject(0);
            JSONObject volumeinfo=itemObject.getJSONObject("volumeInfo");
            String title=volumeinfo.getString("title");
            title1.setText("Title -->"+title);
            JSONArray author=volumeinfo.getJSONArray("authors");
            String result_auth=author.getString(0);
            author1.setText("Author -->"+result_auth);
            String des=volumeinfo.getString("description");
            description1.setText("Description -->"+des);
            JSONObject image=volumeinfo.getJSONObject("imageLinks");
            String imgLink=image.getString("thumbnail");
            Picasso.with(this).load(imgLink).into(imageView);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
