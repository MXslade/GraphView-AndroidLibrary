package com.example.graphviewlibrary2.GraphView.DataManagers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataSeries<TypeX extends Comparable<TypeX>, TypeY extends Comparable<TypeY>> {

    private List<DataPoint<TypeX, TypeY>> dataPoints;

    public DataSeries() {
        dataPoints = new ArrayList<>();
    }

    public DataSeries(ArrayList<DataPoint<TypeX, TypeY>> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public List<DataPoint<TypeX, TypeY>> getDataPoints() {
        dataPoints.sort(new Comparator<DataPoint<TypeX, TypeY>>() {
            @Override
            public int compare(DataPoint<TypeX, TypeY> o1, DataPoint<TypeX, TypeY> o2) {
                return o1.getXValue().compareTo(o2.getXValue());
            }
        });
        return dataPoints;
    }

    public void addDataPoint(DataPoint<TypeX, TypeY> dataPoint) {
        dataPoints.add(dataPoint);
    }

    public void addDataPoint(DataPoint<TypeX, TypeY> dataPoint, int where) {

    }

    public DataPoint<TypeX, TypeY> findMinX() {
        DataPoint<TypeX, TypeY> min;
        if (dataPoints.isEmpty()) {
            return null;
        } else {
            min = dataPoints.get(0);
        }
        for (DataPoint<TypeX, TypeY> dataPoint : dataPoints) {
            min = min.getXValue().compareTo(dataPoint.getXValue()) > 0 ? dataPoint : min;
        }
        return min;
    }

    public DataPoint<TypeX, TypeY> findMinY() {
        DataPoint<TypeX, TypeY> min;
        if (dataPoints.isEmpty()) {
            return null;
        } else {
            min = dataPoints.get(0);
        }
        for (DataPoint<TypeX, TypeY> dataPoint : dataPoints) {
            min = min.getYValue().compareTo(dataPoint.getYValue()) >= 0 ? dataPoint : min;
        }
        return min;
    }

    public DataPoint<TypeX, TypeY> findMaxX() {
        DataPoint<TypeX, TypeY> max;
        if (dataPoints.isEmpty()) {
            return null;
        } else {
            max = dataPoints.get(0);
        }
        for (DataPoint<TypeX, TypeY> dataPoint : dataPoints) {
            max = max.getXValue().compareTo(dataPoint.getXValue()) <= 0 ? dataPoint : max;
        }
        return max;
    }

    public DataPoint<TypeX, TypeY> findMaxY() {
        DataPoint<TypeX, TypeY> max;
        if (dataPoints.isEmpty()) {
            return null;
        } else {
            max = dataPoints.get(0);
        }
        for (DataPoint<TypeX, TypeY> dataPoint : dataPoints) {
            max = max.getYValue().compareTo(dataPoint.getYValue()) <= 0 ? dataPoint : max;
    }
        return max;
    }

}
