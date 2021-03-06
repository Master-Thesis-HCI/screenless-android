package timeline.lizimumu.com.screenless.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import timeline.lizimumu.com.screenless.service.AlarmService;

/**
 * Alarm receiver
 * Created by zb on 02/01/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, AlarmService.class));
    }
}
