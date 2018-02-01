package com.aschambers.android_volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button parse;
    private TextView result;
    private RequestQueue rQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parse = findViewById(R.id.button_parse);
        result = findViewById(R.id.tvResult);

        rQueue = Volley.newRequestQueue(this);
        parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });

    }

    private void jsonParse() {

        String url = "https://api.myjson.com/bins/wnkid";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("topping");
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject topping = jsonArray.getJSONObject(i);
                            int userId = topping.getInt("id");
                            String type = topping.getString("type");
                            result.append(userId + ", " + String.valueOf(type) + "\n\n");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }
        );

        rQueue.add(request);
    }
}
