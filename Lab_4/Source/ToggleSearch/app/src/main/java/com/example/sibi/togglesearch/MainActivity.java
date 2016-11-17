package com.example.sibi.togglesearch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sibi on 9/18/2016.
 */
public class MainActivity extends AppCompatActivity {

    private TextView tv;
    String sourcetext;
    TextView outputTextView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.buttonhit);
        tv = (TextView)findViewById(R.id.res_article);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TextView texttext=(TextView)findViewById(R.id.search);
                sourcetext=texttext.getText().toString();
                new JSONTask().execute("https://kgsearch.googleapis.com/v1/entities:search?query=" +sourcetext+ "&key=AIzaSyCxmnnpz7-qk5BZzKMyssCdPZ1D6uYi7I0&limit=1&indent=True" );
            }
        });
        outputTextView = (TextView) findViewById(R.id.res_article);

    }
    public class JSONTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url=new URL(params[0]);
                connection=(HttpURLConnection)url.openConnection();
                connection.connect();

                InputStream stream=connection.getInputStream();
                reader=new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer=new StringBuffer();
                String line="";
                while ((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                String finaljson=buffer.toString();

                JSONObject parent=new JSONObject(finaljson);
                JSONArray parenta=parent.getJSONArray("itemListElement");

                JSONObject finalj=parenta.getJSONObject(0);
                JSONObject final1=finalj.getJSONObject("result");
                JSONObject final6=final1.getJSONObject("detailedDescription");
                JSONObject final8=final1.getJSONObject("image");

                String name=final1.getString("name");
                String desc= final1.getString("description");
                String type=final1.getString("@type");
                String article=final6.getString("articleBody");
                //String image=final8.getString("contentUrl");

                return "NAME : "+name+"\n"+"TYPE : "+type+"\n"+"DESCRIPTION : "+desc+"\n"+"CONTENT : "+article;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection!=null)
                {
                    connection.disconnect();}
                try {
                    if (reader!=null)
                    {
                        reader.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            tv.setText(s);


        }

    }

}

