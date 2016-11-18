package com.example.azadljy.androidsvg.utils;

import android.graphics.Color;
import android.util.Log;
import android.util.Xml;

import com.example.azadljy.androidsvg.svgmodel.VertorDrawableModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Ljy on 2016/11/15.
 * 邮箱：enjoy_azad@sina.com
 */

public class VectorDrawablePrease {
    private VertorDrawableModel drawableModel;
    private List<String> pathData;
    private List<Integer> strokeColors;
    private List<Integer> fillColors;

    public VectorDrawablePrease() {
        drawableModel = new VertorDrawableModel();
        pathData = new ArrayList<>();
        strokeColors = new ArrayList<>();
        fillColors = new ArrayList<>();
    }

//    public List<String> getVertorDrawableModel(InputStream is) {
//        Log.e("TAG", "getVertorDrawableModel:开始---");
//        List<String> paths = new ArrayList<>();
//        XmlPullParser xpp = Xml.newPullParser();
//        try {
//            Log.e("TAG", "getVertorDrawableModel:try");
//            xpp.setInput(is, "UTF-8");
//            int eventType = xpp.getEventType();
//
//            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
//                if (xpp.getEventType() == XmlPullParser.START_TAG) {
//                    String tagName = xpp.getName();
//                    if (tagName.equals("path")) {
//                        String pathData = xpp.getAttributeValue(null, "d");
//                        paths.add(pathData);
//                        Log.e("TAG", "getVertorDrawableModel: ");
//                    }
//                }
//                xpp.next();
//            }
//
//        } catch (XmlPullParserException e) {
//            Log.e("TAG", "getPath1:" + e.getMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("TAG", "getPath2:" + e.getMessage());
//        }
//        return paths;
//
//    }

    public VertorDrawableModel getVertorDrawableModel(InputStream is) {

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
//                        if (xpp.getName().equals("beauty")) { // 判断结束标签元素是否是book
//                            pathData.add(beauty); // 将book添加到books集合
//                            beauty = null;
//                        }
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
        return drawableModel;

    }

}
