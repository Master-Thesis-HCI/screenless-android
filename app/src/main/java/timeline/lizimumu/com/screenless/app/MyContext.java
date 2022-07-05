package timeline.lizimumu.com.screenless.app;

import android.content.Context;

public class MyContext extends MyApplication {
    private static MyContext instance;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public static Context getContext() {
        return instance;
        // or return instance.getApplicationContext();
    }
}
