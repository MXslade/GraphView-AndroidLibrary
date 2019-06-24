package com.example.graphviewlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.graphviewlibrary2.GraphView.DataManagers.DataPoint;
import com.example.graphviewlibrary2.GraphView.DataManagers.DataSeries;
import com.example.graphviewlibrary2.GraphView.GraphView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
    }

    private void initControls() {
        graphView = (GraphView) findViewById(R.id.graphView);
        DataSeries<Date, Double> dataSeries = new DataSeries<>();
        dataSeries.addDataPoint(new DataPoint<Date, Double>(new Date(946684800), 1.0));
        dataSeries.addDataPoint(new DataPoint<Date, Double>(new Date(1104537600), 2.0));
        dataSeries.addDataPoint(new DataPoint<Date, Double>(new Date(1262304000), 3.0));
        graphView.applySeries(dataSeries);
    }
}
