package com.example.azadljy.androidsvg.svgmodel;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;

import com.example.azadljy.androidsvg.utils.PathParser1;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Ljy on 2016/11/16.
 * 邮箱：enjoy_azad@sina.com
 */

public class VertorDrawableModel {

    private List<String> pathData;
    private List<Path> pathList;
    private float viewportHeight;
    private float viewportWidth;
    private List<Integer> strokeColors;
    private List<Integer> fillColors;
    private List<Float> pathLengths;

    public VertorDrawableModel() {
        pathList = new ArrayList<>();
        pathLengths = new ArrayList<>();
    }

    public void transformPaths(Matrix scaleMatrix) {
        if (pathData != null && pathData.size() != 0) {
            for (String s : pathData) {
                float length = 0;
                Path path = PathParser1.createPathFromPathData(s);
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
