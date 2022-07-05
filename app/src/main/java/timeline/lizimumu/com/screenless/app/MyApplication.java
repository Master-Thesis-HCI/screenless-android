package timeline.lizimumu.com.screenless.app;

import android.app.Application;
import android.content.Intent;

import androidx.work.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import timeline.lizimumu.com.screenless.AppConst;
import nl.romanpeters.screenless.BuildConfig;
import timeline.lizimumu.com.screenless.data.AppItem;
import timeline.lizimumu.com.screenless.data.DataManager;
import timeline.lizimumu.com.screenless.db.DbHistoryExecutor;
import timeline.lizimumu.com.screenless.db.DbIgnoreExecutor;
import timeline.lizimumu.com.screenless.service.AppService;
import timeline.lizimumu.com.screenless.workers.UploadWorker;
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

        /** Create a background task which calls MyApplication.volleyPost(getData(0,99), getApplicationContext()) every 5 minutes */
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest uploadWorkRequest = new PeriodicWorkRequest.Builder(UploadWorker.class, 1, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(uploadWorkRequest);




//        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(uploadWorkRequest.getId()).observe((LifecycleOwner) this, workInfo -> {
//            if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
//                // print the result
//                System.out.println("Work finished");
//            }
//        });


//
//        Timer timerObj = new Timer();
//
//
//
//        TimerTask timerTaskObj = new TimerTask() {
//            public void run() {
//                System.out.println("triggered from TimerTask.run()");
//                VolleyPost.volleyPost(DataManager.getData(0, 99), getApplicationContext());
//            }
//        };
//
//        timerObj.schedule(timerTaskObj, 0, 30000); // each 5 minutes

//        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(uploadWorkRequest.getId()).observe((LifecycleOwner) this, workInfo -> {
//            if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
//                // print the result
//                System.out.println("Work finished");
//            }
//        });


//        WorkManager.getInstance(...)
//    .beginWith(Arrays.asList(workA, workB))
//                .then(workC)
//                .enqueue();


        if (AppConst.CRASH_TO_FILE) CrashHandler.getInstance().init();
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
}


