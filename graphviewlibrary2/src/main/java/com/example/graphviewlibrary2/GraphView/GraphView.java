package com.example.graphviewlibrary2.GraphView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


import com.example.graphviewlibrary2.GraphView.DataManagers.DataPoint;
import com.example.graphviewlibrary2.GraphView.DataManagers.DataSeries;
import com.example.graphviewlibrary2.GraphView.DateHelper.DateHelper;
import com.example.graphviewlibrary2.GraphView.DateHelper.DateLabel;

import java.text.DecimalFormat;
import java.util.Date;

public class GraphView extends View {

    private final int MAX_X_LABELS = 5;
    private final int MAX_Y_LABELS = 5;

    private DataSeries dataSeries;
    private DataPoint minX;
    private DataPoint minY;
    private DataPoint maxX;
    private DataPoint maxY;
    private Paint labelPaint;
    private Paint axisPaint;
    private int width;
    private int height;
    private int xOffSet;
    private int yOffSet;
    private int xAlign;
    private int yAlign;
    private double rangeX;
    private double rangeY;
    private double stepX;
    private double stepY;
    private double x, dx;
    private double y, dy;

    private DecimalFormat decimalFormat;

    private DateLabel chosenDateLabel = DateLabel.PURE_DATE;

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        labelPaint = new Paint();
        axisPaint = new Paint();
        decimalFormat = new DecimalFormat("#.##");
    }

    public void applySeries(DataSeries dataSeries) {
        this.dataSeries = dataSeries;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // manipulations with DataSeries and drawing graph(line one)
        if (dataSeries == null) {
            return;
        }
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        initPaints();
        drawAxis(canvas);
        drawPoints(canvas);
    }

    private void drawAxis(Canvas canvas) {
        xOffSet = width / 7;
        yOffSet = height / 7;
        xAlign = 10;
        yAlign = 20;
        canvas.drawLine(xOffSet, yOffSet, xOffSet, height - yOffSet, axisPaint);
        canvas.drawLine(xOffSet, height - yOffSet, width - xOffSet, height - yOffSet, axisPaint);
    }

    private void drawPoints(Canvas canvas) {
        minY = dataSeries.findMinY();
        minX = dataSeries.findMinX();
        maxY = dataSeries.findMaxY();
        maxX = dataSeries.findMaxX();
        axisPaint.setColor(Color.RED);
        if (minX.getXValue().getClass() == Double.class) {
            drawXAsDouble(canvas);
        } else if  (minX.getXValue().getClass() == Date.class) {
            drawXAsDate(canvas);
        }
    }

    private void drawXAsDouble(Canvas canvas) {
        double[] xs = new double[dataSeries.getDataPoints().size()];
        double[] ys = new double[dataSeries.getDataPoints().size()];
        DataPoint dataPoint;
        rangeX = maxX.getXAsDouble() - minX.getXAsDouble();
        rangeY = maxY.getYAsDouble() - minY.getYAsDouble();
        for (int i = 0; i < dataSeries.getDataPoints().size(); ++i) {
            dataPoint = (DataPoint) dataSeries.getDataPoints().get(i);
            double x = (dataPoint.getXAsDouble() - minX.getXAsDouble()) / rangeX;
            x = x * (width -  2.0 * xOffSet) + xOffSet + xAlign;
            double y = 1.0 - ((Double) dataPoint.getYValue() - (Double)minY.getYValue()) / rangeY;
            y = y * (height - 2.0 * yOffSet) + yOffSet;
            xs[i] = x;
            ys[i] = y;
        }
        drawLines(canvas, xs, ys);
        drawLabelsAsDouble(canvas);
    }

    private void drawLabelsAsDouble(Canvas canvas) {
        initCoordinatesAndDeltas();
        String text;
        for (int i = 0; i < MAX_X_LABELS; ++i) {
            text = decimalFormat.format(minX.getXAsDouble() + stepX * i);
            canvas.drawText(text, (float)(x + dx * i), height - yOffSet + 40, labelPaint);
        }
        changePaints();
        for (int i = 0; i < MAX_Y_LABELS; ++i) {
            text = decimalFormat.format(maxY.getYAsDouble() - stepY * i);
            canvas.drawText(text, xOffSet -20, (float)(y + dy * i), labelPaint);
            canvas.drawLine(xOffSet, (float)dy * i + yOffSet, width - xOffSet, (float)dy * i + yOffSet, axisPaint);
        }
    }

    private void drawXAsDate(Canvas canvas) {
        double[] xs = new double[dataSeries.getDataPoints().size()];
        double[] ys = new double[dataSeries.getDataPoints().size()];
        DataPoint dataPoint;
        rangeX = maxX.getXAsDate().getTime() - minX.getXAsDate().getTime();
        rangeY = maxY.getYAsDouble() - minY.getYAsDouble();
        for (int i = 0; i < dataSeries.getDataPoints().size(); ++i) {
            dataPoint = (DataPoint) dataSeries.getDataPoints().get(i);
            double x = (dataPoint.getXAsDate().getTime() - minX.getXAsDate().getTime()) / rangeX;
            x = x * (width -  2.0 * xOffSet) + xOffSet + xAlign;
            double y = 1.0 - ((Double) dataPoint.getYValue() - (Double)minY.getYValue()) / rangeY;
            y = y * (height - 2.0 * yOffSet) + yOffSet;
            xs[i] = x;
            ys[i] = y;
        }
        drawLines(canvas, xs, ys);
        drawLabelsAsDate(canvas);
    }

    private void drawLabelsAsDate(Canvas canvas) {
        initCoordinatesAndDeltas();
        String text;
        for (int i = 0; i < MAX_X_LABELS; ++i) {
            text = DateHelper.longToDate(minX.getXAsDate().getTime() + (long)stepX * i, chosenDateLabel);
            canvas.drawText(text, (float)(x + dx * i), height - yOffSet + 40, labelPaint);
        }
        changePaints();
        for (int i = 0; i < MAX_Y_LABELS; ++i) {
            text = decimalFormat.format(maxY.getYAsDouble() - stepY * i);
            canvas.drawText(text, xOffSet -20, (float)(y + dy * i), labelPaint);
            canvas.drawLine(xOffSet, (float)dy * i + yOffSet, width - xOffSet, (float)dy * i + yOffSet, axisPaint);
        }
    }

    private void initPaints() {
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStrokeWidth(5);
        axisPaint.setStyle(Paint.Style.FILL);
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(40);
    }

    private void drawLines(Canvas canvas, double[] xs, double[] ys) {
        for (int i = 1; i < xs.length; ++i) {
            canvas.drawLine((float)xs[i - 1], (float)ys[i - 1], (float)xs[i], (float)ys[i], axisPaint);
        }
    }

    private void initCoordinatesAndDeltas() {
        stepX = rangeX / MAX_X_LABELS;
        stepY = rangeY / MAX_Y_LABELS;
        x = xOffSet + xAlign;
        dx = (width - 2.0 * xOffSet) / MAX_X_LABELS;
        y = yOffSet + yAlign;
        dy = (height - 2.0 * yOffSet) / MAX_Y_LABELS;
    }

    private void changePaints() {
        labelPaint.setTextAlign(Paint.Align.RIGHT);
        axisPaint.setAlpha(50);
        axisPaint.setColor(Color.BLACK);
    }

}
