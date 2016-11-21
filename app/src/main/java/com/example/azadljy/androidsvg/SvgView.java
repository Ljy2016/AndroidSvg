package com.example.azadljy.androidsvg;

/**
 * 作者：Ljy on 2016/11/16.
 * 邮箱：enjoy_azad@sina.com
 */

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.azadljy.androidsvg.svgmodel.VertorDrawableModel;

import java.util.ArrayList;
import java.util.List;

public class SvgView extends View {


    private List<Path> pathList = null;
    private boolean isHavePathData;
    private List<Float> pathLength;
    private Matrix scaleMatrix;
    private int width;
    private int height;
    private VertorDrawableModel drawableModel;
    private List<Integer> strokeColors;
    private List<Integer> fillColors;
    private List<GlyphData> glyphDataList;

    public SvgView(Context context) {
        super(context);
        init();
    }

    public SvgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SvgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        pathList = new ArrayList<>();
        pathLength = new ArrayList<>();
        scaleMatrix = new Matrix();
        glyphDataList = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMySize(400, widthMeasureSpec);
        height = getMySize(400, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isHavePathData) {
            for (GlyphData glyphData : glyphDataList) {
                glyphData.paint.setAntiAlias(true);
                canvas.drawPath(glyphData.path, glyphData.paint);
                //痕迹
                // canvas.drawPath(glyphData.path, glyphData.markPaint);
            }
        }
    }

    public void start() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(SvgView.this, "phase", 0.0f, 1.0f);
        animator.setDuration(3000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                for (GlyphData glyphData : glyphDataList) {
                    glyphData.paint.setStyle(Paint.Style.FILL);
                    glyphData.paint.setColor(glyphData.fillColor);
                }
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    public void setPhase(float phase) {

        for (GlyphData glyphData : glyphDataList) {
            glyphData.paint.setColor(glyphData.strokeColor);
            glyphData.paint.setPathEffect(createPathEffect(glyphData.length, phase, 0.0f));
            glyphData.markPaint.setColor(Color.BLACK);
            glyphData.markPaint.setStrokeWidth(3);
            glyphData.markPaint.setPathEffect(new DashPathEffect(new float[]{0, glyphData.length * phase, phase > 0 ? 15 : 0, glyphData.length}, 0));
        }
        invalidate();
    }

    private static PathEffect createPathEffect(float pathlength, float phase, float offset) {
        return new DashPathEffect(new float[]{pathlength, pathlength}, pathlength - phase * pathlength);
    }

//    private void setPaths(List<String> paths) {
//        if (paths != null) {
//            for (String s : paths) {
//                float length = 0;
//                Path path = ExposedPathParser.createPathFromPathData(s);
//                if (path != null) {
//                    path.transform(scaleMatrix);
//                    PathMeasure measure = new PathMeasure(path, true);
//                    while (true) {
//                        length = Math.max(length, measure.getLength());
//                        if (!measure.nextContour()) {
//                            break;
//                        }
//                    }
//                    pathLength.add(length);
//                    pathList.add(path);
//                }
//            }
//        }
//
//    }

    public void setDrawableModel(VertorDrawableModel drawableModel) {
        this.drawableModel = drawableModel;
        transform();
        isHavePathData = true;
//        invalidate();
    }

    private void transform() {
        float scale = Math.min(width / drawableModel.getViewportWidth(), height / drawableModel.getViewportHeight());
        scaleMatrix.setScale(scale, scale);
        drawableModel.transformPaths(scaleMatrix);
        pathList = drawableModel.getPathList();
        pathLength = drawableModel.getPathLengths();
        strokeColors = drawableModel.getStrokeColors();
        fillColors = drawableModel.getFillColors();
        for (int i = 0; i < pathList.size(); i++) {
            GlyphData glyphData = new GlyphData();
            glyphData.paint = new Paint();
            glyphData.markPaint = new Paint();
            glyphData.markPaint.setStyle(Paint.Style.STROKE);
            glyphData.length = pathLength.get(i);
            glyphData.strokeColor = Color.BLACK;
//            glyphData.strokeColor = strokeColors.get(i);
            glyphData.fillColor = fillColors.get(i);
            glyphData.path = pathList.get(i);
            glyphData.paint.setStyle(Paint.Style.STROKE);
            if (i < 72) {
                glyphData.fillColor = Color.parseColor("#FF83FA");
            }else if(i<75){
                glyphData.fillColor = Color.parseColor("#000000");
            }else if(i<77){
                glyphData.fillColor = Color.parseColor("#FF83FA");
            }else if(i<78){
                glyphData.fillColor = Color.parseColor("#000000");
            }else if(i<86){
                glyphData.fillColor = Color.parseColor("#FF83FA");
            }else if(i<92){
                glyphData.fillColor = Color.parseColor("#000000");
            }else {
                glyphData.fillColor = Color.parseColor("#FF83FA");
            }

            glyphData.paint.setColor(glyphData.strokeColor);

            glyphDataList.add(glyphData);
        }
        Log.e("TAG", "transform: "+pathList.size());
    }

    private static class GlyphData {
        Path path;
        Paint paint;
        Paint markPaint;
        float length;
        int strokeColor;
        int fillColor;
    }
}