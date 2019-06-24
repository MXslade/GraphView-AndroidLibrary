package com.example.graphviewlibrary2.GraphView.DataManagers;

import java.util.Date;

public class DataPoint<TypeX extends Comparable<TypeX>, TypeY extends Comparable<TypeY>> {

    private TypeX xValue;
    private TypeY yValue;

    public DataPoint(TypeX xValue, TypeY yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public TypeX getXValue() {
        return xValue;
    }

    public TypeY getYValue() {
        return yValue;
    }

    public double getXAsDouble() { return (Double) xValue; } //use that only when you are 100% sure that TypeX is double

    public double getYAsDouble() { return (Double) yValue; } //use that only when you are 100% sure that TypeY is double

    public Date getXAsDate() { return (Date) xValue; } //use that only when you are 100% sure that TypeX is Date

}
