package com.example.azadljy.androidsvg;

import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.azadljy.androidsvg.svgmodel.VertorDrawableModel;
import com.example.azadljy.androidsvg.utils.VectorDrawablePrease;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    InputStream inputStream;
    VectorDrawablePrease prease;
    private SvgView svgView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        svgView1 = (SvgView) findViewById(R.id.svg);
        prease = new VectorDrawablePrease(this);
        inputStream = getResources().openRawResource(R.raw.ic_paojie);
    }

    public void go1(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                VertorDrawableModel drawableModel = prease.getVertorDrawableModel(R.xml.ic_abu);
                VertorDrawableModel drawableModel = prease.getVertorDrawableModel(inputStream);
                Log.e("TAG", "run:" + drawableModel.getPathData().size());
                Log.e("TAG", "run:" + drawableModel.getFillColors().size());
                Log.e("TAG", "run:" + drawableModel.getStrokeColors().size());
                svgView1.setDrawableModel(drawableModel);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        svgView1.start();
                    }
                });
            }
        }).start();
    }

    public void duang(View view) {
        svgView1.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
