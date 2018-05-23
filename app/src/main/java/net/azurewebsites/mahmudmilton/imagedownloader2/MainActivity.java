package net.azurewebsites.mahmudmilton.imagedownloader2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //public static final String EXTRA_MESSAGE = "net.azurewebsites.mahmudmilton.imagedownloader2.MESSAGE";
    private Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnDownload).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, DownloadImage.class);
        i.putExtra(DownloadImage.KEY_SELECTED_BTN, v.getId());
        startActivity(i);
    }
}
