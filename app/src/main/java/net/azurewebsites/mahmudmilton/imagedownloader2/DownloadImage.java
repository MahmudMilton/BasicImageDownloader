package net.azurewebsites.mahmudmilton.imagedownloader2;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class DownloadImage extends AppCompatActivity {

    public static final String KEY_SELECTED_BTN = "selected_button";

    private String name;
    private ImageView imgDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String url = "https://upload.wikimedia.org/wikipedia/commons/4/45/A_small_cup_of_coffee.JPG";
        switch (getIntent().getIntExtra(KEY_SELECTED_BTN, 0)) {
            case R.id.btnDownload:
                setContentView(R.layout.download_image);
                imgDisplay = (ImageView) findViewById(R.id.imgResult);
                final TextView tvPercent = (TextView) findViewById(R.id.tvPercent);
                final ProgressBar pbLoading = (ProgressBar) findViewById(R.id.pbImageLoading);
                final BasicImageDownloader downloader = new BasicImageDownloader(new BasicImageDownloader.OnImageLoaderListener() {
                    @Override
                    public void onError(BasicImageDownloader.ImageError error) {
                        Toast.makeText(DownloadImage.this, "Error code " + error.getErrorCode() + ": " +
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        tvPercent.setVisibility(View.GONE);
                        pbLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onProgressChange(int percent) {
                        pbLoading.setProgress(percent);
                        tvPercent.setText(percent + "%");
                    }

                    @Override
                    public void onComplete(Bitmap result) {
                        final Bitmap.CompressFormat mFormat = Bitmap.CompressFormat.JPEG;
                        final File myImageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                                File.separator + "image_test" + File.separator + name + "." + mFormat.name().toLowerCase());
                        BasicImageDownloader.writeToDisk(myImageFile, result, new BasicImageDownloader.OnBitmapSaveListener() {
                            @Override
                            public void onBitmapSaved() {
                                Toast.makeText(DownloadImage.this, "Image saved as: " + myImageFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onBitmapSaveError(BasicImageDownloader.ImageError error) {
                                Toast.makeText(DownloadImage.this, "Error code " + error.getErrorCode() + ": " +
                                        error.getMessage(), Toast.LENGTH_LONG).show();
                                error.printStackTrace();
                            }
                        }, mFormat, false);

                        tvPercent.setVisibility(View.GONE);
                        pbLoading.setVisibility(View.GONE);
                        imgDisplay.setImageBitmap(result);
                        imgDisplay.startAnimation(AnimationUtils.loadAnimation(DownloadImage.this, android.R.anim.fade_in));
                    }
                });
                downloader.download(url, true);
            }

    }
}
