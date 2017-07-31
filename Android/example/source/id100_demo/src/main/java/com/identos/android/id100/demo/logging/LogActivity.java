package com.identos.android.id100.demo.logging;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.identos.android.id100.demo.R;
import com.identos.android.id100.demo.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This Activity displays the logs from the ID100 Demo app.
 */
public class LogActivity extends AppCompatActivity {
    private File logFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView logTextView = (TextView) findViewById(R.id.log);

        try {
            logFile = Utils.getLogFile(this);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(logFile));

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            logTextView.setText(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_send_log:
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/*");

                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(logFile));
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "ID100 Demo logs");

                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendIntent);
                }

                return true;

            case R.id.action_clear_log:
                if(logFile.delete()) {
                    finish();
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}