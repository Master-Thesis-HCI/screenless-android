package timeline.lizimumu.com.screenless.ui;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Locale;

import nl.romanpeters.screenless.BuildConfig;
import nl.romanpeters.screenless.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.about);
        }

        ((TextView) findViewById(R.id.version)).setText(
                String.format(Locale.getDefault(),
                        getResources().getString(R.string.version), BuildConfig.VERSION_NAME));
    }
}
