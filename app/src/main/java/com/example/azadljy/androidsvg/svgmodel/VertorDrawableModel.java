package com.example.azadljy.androidsvg.svgmodel;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;

import com.example.azadljy.androidsvg.utils.PathParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Ljy on 2016/11/16.
 * 邮箱：enjoy_azad@sina.com
 */

public class VertorDrawableModel {
    //解析xml得到的path的原始数据
    private List<String> pathData;
    //根据view的大小将pathData缩放转换后的适合当前view尺寸的path集合
    private List<Path> pathList;
    //svg数据的所参照的宽高
    private float viewportHeight;
    private float viewportWidth;
    //path本体颜色
    private List<Integer> strokeColors;
    //path填充颜色
    private List<Integer> fillColors;
    //path的长度
    private List<Float> pathLengths;

    public VertorDrawableModel() {
        pathList = new ArrayList<>();
        pathLengths = new ArrayList<>();
        strokeColors = new ArrayList<>();
        fillColors = new ArrayList<>();
    }

    //根据缩放矩阵将pathData转换为适合的path
    public void transformPaths(Matrix scaleMatrix) {
        if (pathData != null && pathData.size() != 0) {
            for (String s : pathData) {
                float length = 0;
                Path path = PathParser.createPathFromPathData(s);
                if (path != null) {
                    path.transform(scaleMatrix);
                    PathMeasure measure = new PathMeasure(path, true);
                    while (true) {
                        length = Math.max(length, measure.getLength());
                        if (!measure.nextContour()) {
                            break;
                        }
                    }
                    pathLengths.add(length);
                    pathList.add(path);
                }
            }
        }

    }

    public List<String> getPathData() {
        return pathData;
    }

    public void setPathData(List<String> pathData) {
        this.pathData = pathData;
    }

    public float getViewportHeight() {
        return viewportHeight;
    }

    public void setViewportHeight(float viewportHeight) {
        this.viewportHeight = viewportHeight;
    }

    public float getViewportWidth() {
        return viewportWidth;
    }

    public void setViewportWidth(float viewportWidth) {
        this.viewportWidth = viewportWidth;
    }

    public List<Integer> getStrokeColors() {
        return strokeColors;
    }

    public void setStrokeColors(List<Integer> strokeColors) {
        this.strokeColors = strokeColors;
    }

    public List<Integer> getFillColors() {
        return fillColors;
    }

    public void setFillColors(List<Integer> fillColors) {
        this.fillColors = fillColors;
    }

    public List<Path> getPathList() {
        return pathList;
    }

    public void setPathList(List<Path> pathList) {
        this.pathList = pathList;
    }

    public List<Float> getPathLengths() {
        return pathLengths;
    }

    public void setPathLengths(List<Float> pathLengths) {
        this.pathLengths = pathLengths;
    }


}
