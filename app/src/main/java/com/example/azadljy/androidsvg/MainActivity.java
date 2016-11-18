package com.example.azadljy.androidsvg;

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

//        svgView = (AnimatedSvgView) findViewById(R.id.svg);
//        svgView.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                svgView.start();
//            }
//        }, 500);
//
//        svgView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (svgView.getState() == AnimatedSvgView.STATE_FINISHED) {
//                    svgView.start();
//                }
//            }
//        });
        prease = new VectorDrawablePrease();
        inputStream = getResources().openRawResource(R.raw.ic_abu);
    }

//    public void go(View view) {
//        Log.e("TAG", "go:开始");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int[] colors;
//                List<String> paths = prease.getPath1(inputStream);
//                colors = new int[paths.size()];
//                for (int i = 0; i < paths.size(); i++) {
//                    if (i == 0) {
//                        colors[i] = Color.BLACK;
//
//                    } else {
//                        colors[i] = Color.BLACK;
//                    }
//                }
//                final VertorDrawableModel svg = new VertorDrawableModel();
//                String[] path = new String[paths.size()];
//                paths.toArray(path);
//                svg.setPathData(path);
//                svg.setColors(colors);
//                svg.setHeight(600);
//                svg.setWidth(600);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        setSvg(svg);
//                    }
//                });
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    public void go1(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                VertorDrawableModel drawableModel = prease.getVertorDrawableModel(inputStream);
                Log.e("TAG", "run:" + drawableModel.getPathData().size());
                Log.e("TAG", "run:" + drawableModel.getFillColors().size());
                Log.e("TAG", "run:" + drawableModel.getStrokeColors().size());
                svgView1.setDrawableModel(drawableModel);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        svgView1.invalidate();
                    }
                });
            }
        }).start();
    }

    public void duang(View view) {
        svgView1.start();
    }

//    private void setSvg(VertorDrawableModel svg) {
//        svgView.setGlyphStrings(svg.getPathData());
//        svgView.setFillColors(svg.getColors());
//        svgView.setViewportSize(svg.getWidth(), svg.getHeight());
//        svgView.setTraceResidueColor(0x32000000);
//        svgView.setTraceColors(svg.getColors());
//        svgView.rebuildGlyphData();
//        svgView.start();
//    }

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
