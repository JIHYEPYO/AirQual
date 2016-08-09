package com.example.pyojihye.airpollution.P_Manager;

import android.graphics.Color;
import android.view.View;

import com.example.pyojihye.airpollution.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import com.example.pyojihye.airpollution.P_Data.Air_Data;

/**
 * Created by user on 2016-07-25.
 */
public class Realchart_Manager {
    View realtime_view;
    private LineChart mChart;
    private PieChart pChart;
    public Realchart_Manager(View view) {

        /* Linechart init*/
        mChart = (LineChart)view.findViewById(R.id.all_chart);
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        mChart.setTouchEnabled(true);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);


        mChart.setPinchZoom(true);
        mChart.setVisibleXRangeMaximum(500);
        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        mChart.setData(data);

        /*piechart init*/
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

       // PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        final ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");

        PieDataSet dataset = new PieDataSet(entries, "# of Calls");
        PieData data2 = new PieData(labels, dataset);
        pChart=(PieChart)view.findViewById(R.id.pie_chart);

        pChart.setData(data2);
        pChart.setClickable(true);
        pChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            //3이 주황색
            //1이 초록색
            //
            //4가 보라색
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
               switch (e.getXIndex())
               {
                   case 1:
                   {
                       mChart.getData().getDataSets().get(1).setVisible(false);
                       mChart.getData().getDataSets().get(2).setVisible(false);
                       mChart.getData().getDataSets().get(3).setVisible(false);
                       mChart.getData().getDataSets().get(4).setVisible(false);
                       mChart.getData().getDataSets().get(5).setVisible(false);
                       mChart.getData().getDataSets().get(0).setVisible(true);
                       break;
                   }
                   case 2:
                   {
                       mChart.getData().getDataSets().get(0).setVisible(false);
                       mChart.getData().getDataSets().get(2).setVisible(false);
                       mChart.getData().getDataSets().get(3).setVisible(false);
                       mChart.getData().getDataSets().get(4).setVisible(false);
                       mChart.getData().getDataSets().get(5).setVisible(false);
                       mChart.getData().getDataSets().get(1).setVisible(true);
                       break;
                   }

                   case 3:
                   {
                       mChart.getData().getDataSets().get(0).setVisible(false);
                       mChart.getData().getDataSets().get(1).setVisible(false);
                       mChart.getData().getDataSets().get(3).setVisible(false);
                       mChart.getData().getDataSets().get(4).setVisible(false);
                       mChart.getData().getDataSets().get(5).setVisible(false);
                       mChart.getData().getDataSets().get(2).setVisible(true);
                       break;
                   }

                   case 4:
                   {
                       mChart.getData().getDataSets().get(0).setVisible(false);
                       mChart.getData().getDataSets().get(1).setVisible(false);
                       mChart.getData().getDataSets().get(2).setVisible(false);
                       mChart.getData().getDataSets().get(4).setVisible(false);
                       mChart.getData().getDataSets().get(5).setVisible(false);
                       mChart.getData().getDataSets().get(3).setVisible(true);
                       break;
                   }

                   case 5:
                   {
                       mChart.getData().getDataSets().get(0).setVisible(false);
                       mChart.getData().getDataSets().get(1).setVisible(false);
                       mChart.getData().getDataSets().get(2).setVisible(false);
                       mChart.getData().getDataSets().get(3).setVisible(false);
                       mChart.getData().getDataSets().get(5).setVisible(false);
                       mChart.getData().getDataSets().get(4).setVisible(true);
                       break;

                   }

                   case 6:
                   {
                       mChart.getData().getDataSets().get(0).setVisible(false);
                       mChart.getData().getDataSets().get(1).setVisible(false);
                       mChart.getData().getDataSets().get(2).setVisible(false);
                       mChart.getData().getDataSets().get(3).setVisible(false);
                       mChart.getData().getDataSets().get(4).setVisible(false);
                       mChart.getData().getDataSets().get(5).setVisible(true);
                       break;
                   }


               }

                //1
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }
    int count=1;
    public void Set_empty()
    {
        PieData data=pChart.getData();
        data.clearValues();
        //data.getDataSet().clear();
        //data.getDataSet().setVisible(false);
        LineData data2=mChart.getData();
        data2.clearValues();
        /*for(int i=0;i<data2.getDataSetCount();i++)
        {
            data2.getDataSetByIndex(i).setVisible(false);
        }*/
    }
    public void Set_Realpie(Air_Data air) throws InterruptedException {
        ArrayList<Integer > air_array=new ArrayList<>();
        //ArrayList<HashMap<String,Integer>>air_array=new ArrayList<>();
        air_array.add(air.co);
        air_array.add(air.so2);
        air_array.add(air.no2);
        air_array.add(air.o3);
        air_array.add(air.time);
        air_array.add(air.pm2_5);

        PieData data=pChart.getData();
        PieDataSet set=null;
        if(data!=null)
        {
            data.removeDataSet(0);
            set=data.getDataSetByIndex(1);
            /*if(set==null)
            {
                for(int i=1;i<6;i++)
                {
                    set=createPieSet(i);
                    data.addDataSet(set);
                }
            }*/
            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            for (int j = 0; j < 6; j++)
            {
                yVals1.add(new Entry(air_array.get(j), j)); //yvals 은 수치값
            }
            ArrayList<String> xVals = new ArrayList<String>();
            /*for(int j = 0; j < 6;j++)
            {
                xVals.add(String.valueOf(j)); //xvals은 이름
            }*/
            xVals.add("CO2");
            xVals.add("SO2");
            xVals.add("NO2");
            xVals.add("O3");
            xVals.add("TEMP");
            xVals.add("PM");

            PieDataSet dataSet = new PieDataSet(yVals1,null);
            dataSet.setSliceSpace(3);
            dataSet.setSelectionShift(5);

            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.parseColor("#5EC75E"));
            colors.add(Color.parseColor("#FFAF0A"));
            colors.add(Color.parseColor("#CD3C3C"));
            colors.add(Color.parseColor("#FF02E402"));
            colors.add(Color.parseColor("#FFFF7F02"));
            colors.add(Color.parseColor( "#FF904098"));

            dataSet.setColors(colors);


            //instantiate pie data object now

            PieData data2 = new PieData(xVals,dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.GRAY);


            pChart.setData(data2);

            //undo all highlight

            pChart.highlightValue(null);

            //update pie chart

        }
        pChart.notifyDataSetChanged();
        pChart.invalidate();
    }
    public void Set_Realchart2(Air_Data air)
    {
        LineData data=mChart.getData();
        LineDataSet set=null;
        if(data!=null)
        {
            set=data.getDataSetByIndex(0);
            if(set==null) //이곳에서 6개 만들어줌
            {
                for(int i=0;i<6;i++)
                {
                    set=createSet(i);
                    data.addDataSet(set);
                }

            }

            for(int i=0;i<6;i++)
            {
                set=(LineDataSet)data.getDataSetByIndex(i);
                if (data.getXValCount() > 20) {
                    set.removeEntry(0);

                    switch (i)
                    {
                        case 0:
                        {
                            data.addEntry(new Entry((float)air.co ,20), i);
                            break;
                        }

                        case 1:
                        {
                            data.addEntry(new Entry((float)air.so2 ,20), i);
                            break;
                        }

                        case 2:
                        {
                            data.addEntry(new Entry((float)air.no2 ,20), i);
                            break;
                        }

                        case 3:
                        {
                            data.addEntry(new Entry((float)air.o3 ,20), i);
                            break;
                        }

                        case 4:
                        {
                            data.addEntry(new Entry((float)air.time,20), i);
                            break;
                        }

                        case 5:
                        {
                            data.addEntry(new Entry((float)air.pm2_5 ,20), i);
                            break;
                        }

                    }
                    for (int z=0; z < set.getEntryCount(); z++) {
                        Entry e = set.getEntryForXIndex(z);
                        if (e==null) continue;
                        e.setXIndex(e.getXIndex() - 1);
                    }

                }
                else{
                    switch (i)
                    {
                        case 0:
                        {
                            data.addEntry(new Entry((float)air.co ,set.getEntryCount()), i);
                            break;
                        }

                        case 1:
                        {
                            data.addEntry(new Entry((float)air.so2 ,set.getEntryCount()), i);
                            break;
                        }

                        case 2:
                        {
                            data.addEntry(new Entry((float)air.no2 ,set.getEntryCount()), i);
                            break;
                        }

                        case 3:
                        {
                            data.addEntry(new Entry((float)air.o3 ,set.getEntryCount()), i);
                            break;
                        }

                        case 4:
                        {
                            data.addEntry(new Entry((float)air.time,set.getEntryCount()), i);
                            break;
                        }

                        case 5:
                        {
                            data.addEntry(new Entry((float)air.pm2_5 ,set.getEntryCount()), i);
                            break;
                        }

                    }
                }

            }
            if (data.getXValCount() > 20)
            {
                data.getXVals().remove(0);
                data.getXVals().add(String.valueOf(count));
            }
            else
            {
                data.getXVals().add(String.valueOf(count));
            }
            count++;
            mChart.notifyDataSetChanged();
            mChart.invalidate();

        }
    }


    public void Set_Realchart(Air_Data air) //차트 1개띄우는거 완성된거
    {
        //String entry_date_time = new SimpleDateFormat("MMM d - HH:mm:ss").format(new Date());
        LineData data=mChart.getData();
        LineDataSet set=null;
        if (data != null) {
            set=data.getDataSetByIndex(0);
            if(set==null)
            {
                set= new LineDataSet(null, "CO2");
                set.setDrawCubic(true);
                set.setColor(Color.parseColor("#5EC75E"));
                set.setCircleColor(Color.parseColor("#5EC75E"));
                set.setLineWidth(2);
                data.addDataSet(set);
            }
            if (data.getXValCount() > 20) {

                data.getXVals().remove(0);
                set.removeEntry(0);
                data.getXVals().add(String.valueOf(count++));
                data.addEntry(new Entry((float)air.co ,20), 0);
                for (int i=0; i < set.getEntryCount(); i++) {
                    Entry e = set.getEntryForXIndex(i);
                    if (e==null) continue;
                    e.setXIndex(e.getXIndex() - 1);
                }
                //set.setVisible(false); 이걸로 나타남 사라짐 나타낼거
                //data.setValueTextSize(0);
            }
            else{
                data.getXVals().add(String.valueOf(count++));
                data.addEntry(new Entry((float)air.co ,set.getEntryCount()), 0);
            }

            mChart.notifyDataSetChanged();
            mChart.invalidate();

        }


    }
    private LineDataSet createSet(int count) {

        LineDataSet set=new LineDataSet(null,null);

        switch (count)
        {
            case 0:
            {
                set= new LineDataSet(null, "CO2");
                set.setColor(Color.parseColor("#5EC75E"));
                set.setCircleColor(Color.parseColor("#5EC75E"));
                set.setLineWidth(3);
                set.setDrawCircles(true);
                set.setDrawCubic(true);

                //set.setDrawFilled(true);
                break;
            }
            case 1:
            {
                set= new LineDataSet(null, "SO2");
                set.setColor(Color.parseColor("#FFAF0A"));
                set.setCircleColor(Color.parseColor("#FFAF0A"));
                set.setLineWidth(3);
                set.setDrawCircles(true);

                break;
            }
            case 2:
            {
                set= new LineDataSet(null, "NO2");

                set.setColor(Color.parseColor("#CD3C3C"));
                set.setCircleColor(Color.parseColor("#CD3C3C"));
                set.setLineWidth(3);
                break;
            }
            case 3:
            {
                set= new LineDataSet(null, "O3");
                set.setColor(Color.parseColor("#FF02E402"));
                set.setCircleColor(Color.parseColor("#FF02E402"));
                set.setLineWidth(3);
                break;
            }
            case 4:
            {
                set= new LineDataSet(null, "TEMP");
                set.setColor(Color.parseColor("#FFFF7F02"));
                set.setCircleColor(Color.parseColor("#FFFF7F02"));
                set.setLineWidth(3);
                break;
            }
            case 5:
            {
                set= new LineDataSet(null, "PM");
                set.setColor(Color.parseColor("#FF904098"));
                set.setCircleColor(Color.parseColor("#FF904098"));
                set.setLineWidth(3);

                break;
            }

        }
        set.setDrawCubic(true);
        return set;
    }
    private PieDataSet createPieSet(int count) {

        PieDataSet set=new PieDataSet(null,null);
        switch (count)
        {
            case 0:
            {
                set=new PieDataSet(null,"CO2");

                set.setColor(Color.parseColor("#5EC75E"));

                break;
            }
            case 1:
            {
                set= new PieDataSet(null, "SO2");
                set.setColor(Color.parseColor("#FFAF0A"));


                break;
            }
            case 2:
            {
                set= new PieDataSet(null, "NO2");

                set.setColor(Color.parseColor("#CD3C3C"));

                break;
            }
            case 3:
            {
                set= new PieDataSet(null, "O3");
                set.setColor(Color.parseColor("#FF02E402"));

                break;
            }
            case 4:
            {
                set= new PieDataSet(null, "TEMP");
                set.setColor(Color.parseColor("#FFFF7F02"));

                break;
            }
            case 5:
            {
                set= new PieDataSet(null, "PM");
                set.setColor(Color.parseColor("#FF904098"));

                break;
            }

        }

        return set;
    }
}
