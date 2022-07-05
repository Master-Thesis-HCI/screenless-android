package timeline.lizimumu.com.screenless.util;

import timeline.lizimumu.com.screenless.data.DataManager;


import android.provider.Settings;

// import the JSON library
import org.json.JSONObject;
import org.json.JSONException;

// import the VolleyPostRequest library
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;





// the volleyPost class makes a post request with data to the server using the Volley library
// parameters:
// data: the data to send to the server as string
// context: the context of the application
public class VolleyPost {
    // the volleyPost method makes a post request with data to the server using the Volley library
    // parameters:
    // data: the data to send to the server as string
    // context: the context of the application
    public static void volleyPost(Context context) {
        // create a request queue
        RequestQueue queue = Volley.newRequestQueue(context);

//

        String data = DataManager.getInstance().getApps(context.getApplicationContext(), 0, 99).toString();

        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String postUrl = "https://thesis.romanpeters.nl/api/"+android_id+"/";

        // print data
        System.out.println(data);

        // create a JSON object with the key "screentime" and value the return value of DataManager.getData(0,99) as string
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("screentime", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // create a json object request with the url "https://thesis.romanpeters.nl/api/"+android_id+"/" and the json object as body
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                postUrl,
                jsonObject,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // log response
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // log error
                error.printStackTrace();
            }
        });
        // add the request to the queue
        queue.add(jsonObjectRequest);
    }
}

