package com.example.apimovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<MovieModel> arrayList;
    String url = "https://mocki.io/v1/cd7dc21f-e01e-4469-b58b-01e4eb3a4ba8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //fetch all the data from api and collect it into arrayList
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //fetch data from the API
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        double rating = jsonObject.getDouble("rating");
                        String title = jsonObject.getString("title");
                        String poster = jsonObject.getString("poster");
                        String overView = jsonObject.getString("overview");

                        arrayList.add(new MovieModel(rating, title, poster, overView));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //add the request to the queue
        requestQueue.add(jsonArrayRequest);

        //give all the collect data of ArrayList to the adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, arrayList);
        recyclerView.setAdapter(customAdapter);
    }
}