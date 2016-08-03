package P_Adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pyojihye.airpollution.R;


/**
 * Created by user on 2016-08-02.
 */
public class Pager_Adapter extends PagerAdapter {

    LayoutInflater inflater;
    public static Context context;
    Handler pHandler;


    public Pager_Adapter(LayoutInflater inflater, Context context)
    {
        this.inflater=inflater;
        this.context=context;
        //this.pHandler=adapter_handler;

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view=null;

        switch(position) {
            case 0: {
                view=inflater.inflate(R.layout.air_status,null);
                container.addView(view);
                final Button mButton = (Button) view.findViewById(R.id.status_btn);
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // here you set what you want to do when user clicks your button,
                        // e.g. launch a new activity
                        /*
                        if(Air_Data_Service.RECEIVE_DATA_STATUS)
                        {
                            mButton.setText("START");
                            Air_Data_Service.RECEIVE_DATA_STATUS=false;
                            Toast.makeText(context.getApplicationContext(),"stop", Toast.LENGTH_SHORT).show();


                        }
                        else
                        {
                            mButton.setText("STOP");
                            Air_Data_Service.RECEIVE_DATA_STATUS=true;
                            Toast.makeText(context.getApplicationContext(),"start", Toast.LENGTH_SHORT).show();

                        }*/
                    }
                });
            }
            case 1:
            {
                //view=inflater.inflate(R.layout.fragment_map,null);
                //view=inflater.inflate(R.layout.fragment_map,null);
               // view=inflater.inflate(R.layout.fragment_map,null);
                //container.addView(view);
            }
        }



        return view;
        //return super.instantiateItem(container, position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return  view==object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
