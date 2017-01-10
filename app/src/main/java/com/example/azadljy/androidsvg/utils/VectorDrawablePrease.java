package com.example.azadljy.androidsvg.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.support.annotation.XmlRes;
import android.util.Log;
import android.util.Xml;

import com.example.azadljy.androidsvg.R;
import com.example.azadljy.androidsvg.svgmodel.VertorDrawableModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.logo;
import static android.R.attr.strokeColor;

/**
 * 作者：Ljy on 2016/11/15.
 * 邮箱：enjoy_azad@sina.com
 */

public class VectorDrawablePrease {
    public static final String TAG = "VectorDrawablePrease";
    private VertorDrawableModel drawableModel;
    private List<String> pathData;
    private List<Integer> strokeColors;
    private List<Integer> fillColors;
    private Context context;

    public VectorDrawablePrease(Context context) {

        pathData = new ArrayList<>();
        strokeColors = new ArrayList<>();
        fillColors = new ArrayList<>();
        this.context = context;
    }

    public VertorDrawableModel getVertorDrawableModel(InputStream is) {
        drawableModel = new VertorDrawableModel();
        long  startTime=System.currentTimeMillis();
        XmlPullParser xpp = Xml.newPullParser();
        try {
            xpp.setInput(is, "UTF-8");
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    // 判断当前事件是否为文档开始事件
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    // 判断当前事件是否为标签元素开始事件
                    case XmlPullParser.START_TAG:
                        if (xpp.getName().equals("vector")) {
                            String viewportHeight = xpp.getAttributeValue(null, "viewportHeight");
                            String viewportWidth = xpp.getAttributeValue(null, "viewportWidth");
                            drawableModel.setViewportHeight(Float.parseFloat(viewportHeight));
                            drawableModel.setViewportWidth(Float.parseFloat(viewportWidth));
                        }
                        if (xpp.getName().equals("path")) {
                            pathData.add(xpp.getAttributeValue(null, "pathData"));
                            Integer strokeColor = Color.parseColor(xpp.getAttributeValue(null, "strokeColor"));
                            Integer fillColor = Color.parseColor(xpp.getAttributeValue(null, "fillColor"));
                            strokeColors.add(strokeColor);
                            fillColors.add(fillColor);
                        }
                        break;
                    // 判断当前事件是否为标签元素结束事件
                    case XmlPullParser.END_TAG:
                        break;

                }
                // 进入下一个元素并触发相应事件
                eventType = xpp.next();
            }

        } catch (XmlPullParserException e) {
            Log.e("TAG", "getPath1:" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG", "getPath2:" + e.getMessage());
        }
        drawableModel.setPathData(pathData);
        drawableModel.setFillColors(fillColors);
        drawableModel.setStrokeColors(strokeColors);
        long  endTime=System.currentTimeMillis();
        Log.e(TAG, "getVertorDrawableModel:有流花费时间 "+(endTime-startTime) );
        return drawableModel;
    }

    public VertorDrawableModel getVertorDrawableModel(@XmlRes int id) {
        long startTime = System.currentTimeMillis();
        XmlResourceParser xmlResourceParser = context.getResources().getXml(id);
        try {
            int eventType = xmlResourceParser.getEventType();
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                switch (eventType) {
                    // 判断当前事件是否为文档开始事件
                    case XmlResourceParser.START_DOCUMENT:
                        break;
                    // 判断当前事件是否为标签元素开始事件
                    case XmlResourceParser.START_TAG:
                        if (xmlResourceParser.getName().equals("vector")) {
                            String viewportHeight = xmlResourceParser.getAttributeValue(3);
                            String viewportWidth = xmlResourceParser.getAttributeValue(4);
                            drawableModel.setViewportHeight(Float.parseFloat(viewportHeight));
                            drawableModel.setViewportWidth(Float.parseFloat(viewportWidth));
                        }
                        if (xmlResourceParser.getName().equals("path")) {
                            Integer fillColor = Color.parseColor(xmlResourceParser.getAttributeValue(0));
                            pathData.add(xmlResourceParser.getAttributeValue(1));
                            Log.e(TAG, "getVertorDrawableModel: " + xmlResourceParser.getAttributeValue(2));
                            Integer strokeColor;
                            try {
                                strokeColor = Color.parseColor(xmlResourceParser.getAttributeValue(2));
                            } catch (IllegalArgumentException e) {
                                strokeColor = Color.parseColor("#00000000");
                            }
                            strokeColors.add(strokeColor);
                            fillColors.add(fillColor);
                        }
                        break;
                    // 判断当前事件是否为标签元素结束事件
                    case XmlResourceParser.END_TAG:
                        break;

                }
                // 进入下一个元素并触发相应事件
                eventType = xmlResourceParser.next();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG", "getPath2:" + e.getMessage());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        drawableModel.setPathData(pathData);
        drawableModel.setFillColors(fillColors);
        drawableModel.setStrokeColors(strokeColors);
        long ednTime = System.currentTimeMillis();
        Log.e(TAG, "getVertorDrawableModel: 无流花费时间：" + (ednTime - startTime));
        return drawableModel;
    }
}
