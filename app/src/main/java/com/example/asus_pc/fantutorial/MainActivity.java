package com.example.asus_pc.fantutorial;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetJSONPlaceHolder getJSONPlaceHolder = new GetJSONPlaceHolder();
        getJSONPlaceHolder.execute("https://jsonplaceholder.typicode.com/posts");
    }

    private class  GetJSONPlaceHolder extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("onPreExecute");
        }

        @Override
        protected String doInBackground(String... strings) {
            System.out.println("doInBackground");
            return LinkKit.GET(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            System.out.println("onPostExcute");
            System.out.println(s);

            try{

                JSONArray jsonArray = new JSONArray(s);
                int jsonArrayLength = jsonArray.length();

                List<Response> responseList = new ArrayList<>();

                for (int i = 0; i < jsonArrayLength; i++ )
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Response response = new Response(
                            jsonObject.getInt("id"),
                            jsonObject.getString("title"),
                            jsonObject.getString("body"),
                            jsonObject.getInt("userId")
                    );
                    responseList.add(response);
                }

                recyclerView.setAdapter(new RecyclerAdapeter(responseList,
                        ))
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
