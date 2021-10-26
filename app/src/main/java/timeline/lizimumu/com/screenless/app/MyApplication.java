package timeline.lizimumu.com.screenless.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import timeline.lizimumu.com.screenless.AppConst;
import nl.romanpeters.screenless.BuildConfig;
import timeline.lizimumu.com.screenless.data.AppItem;
import timeline.lizimumu.com.screenless.data.DataManager;
import timeline.lizimumu.com.screenless.db.DbHistoryExecutor;
import timeline.lizimumu.com.screenless.db.DbIgnoreExecutor;
import timeline.lizimumu.com.screenless.service.AppService;
import timeline.lizimumu.com.screenless.util.CrashHandler;
import timeline.lizimumu.com.screenless.util.PreferenceManager;

/**
 * My Application
 * Created by zb on 18/12/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.init(this);
        getApplicationContext().startService(new Intent(getApplicationContext(), AppService.class));
        DbIgnoreExecutor.init(getApplicationContext());
        DbHistoryExecutor.init(getApplicationContext());
        DataManager.init();
        addDefaultIgnoreAppsToDB();

        Timer timerObj = new Timer();



        TimerTask timerTaskObj = new TimerTask() {
            public void run() {
                MyApplication.volleyPost(getData(0, 99), getApplicationContext());
            }
        };

        timerObj.schedule(timerTaskObj, 0, 300000); // each 5 minutes

        if (AppConst.CRASH_TO_FILE) CrashHandler.getInstance().init();
    }

    protected String getData(Integer... integers) {
        return DataManager.getInstance().getApps(getApplicationContext(), integers[0], integers[1]).toString();
    }

    private void addDefaultIgnoreAppsToDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> mDefaults = new ArrayList<>();
                mDefaults.add("com.android.settings");
                mDefaults.add(BuildConfig.APPLICATION_ID);
                for (String packageName : mDefaults) {
                    AppItem item = new AppItem();
                    item.mPackageName = packageName;
                    item.mEventTime = System.currentTimeMillis();
                    DbIgnoreExecutor.getInstance().insertItem(item);
                }
            }
        }).run();
    }

    public static void volleyPost(String screentime, Context context){
        String postUrl = "https://thesis.romanpeters.nl/api/id1234/";  //TODO
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JSONObject postData = new JSONObject();
        try {
            postData.put("screentime", screentime);  //TODO

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}

