package timeline.lizimumu.com.screenless.workers;
import android.content.Context;
import androidx.annotation.NonNull;
import timeline.lizimumu.com.screenless.data.DataManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/** import the volleyPost method from the util class */
import timeline.lizimumu.com.screenless.util.VolleyPost;




/** UploadWorker class which calls MyApplication.volleyPost(getData(0,99), getApplicationContext()) every 5 minutes */
public class UploadWorker extends Worker {

    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParameters) {
        super(context, workerParameters);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();

        // print "I'm doing work"
        System.out.println("I'm doing work");

        // call the volleyPost method from the util class
        // VolleyPost.volleyPost(getData(0, 99), context);
        VolleyPost.volleyPost(context);

        return Result.success();
    }
}

