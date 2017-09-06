package com.ucsc.alina.postandget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "PostAndGet";

    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
    }

    public void getRequest(View V){

        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_get";
        String my_url = url + "?token=" + "abracadabra";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, my_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "Received: " + response.toString());
                        printResult(response.optString("result"));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(LOG_TAG, error.toString());
                    }
                });

        // We don't want to cache the request.
        jsObjRequest.setShouldCache(false);

        queue.add(jsObjRequest);

    }

    public void postRequest(View V) {
        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_post";

        StringRequest sr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(LOG_TAG, "Got:" + response);
                        try {
                            JSONObject jsonresponse = new JSONObject(response);
                            String result = jsonresponse.optString("result");
                            printResult(result);
                        } catch (JSONException exception) {
                            Log.d(LOG_TAG, "JSON exception" + exception);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, "Error POSTing");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "abracadabra");
                return params;
            }
        };
        queue.add(sr);
    }


    public void printResult(String result) {
        TextView tv = (TextView) findViewById(R.id.status_text);
        tv.setText(result);
    }

}
