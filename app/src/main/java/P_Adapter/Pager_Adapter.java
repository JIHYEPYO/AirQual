package P_Adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.pyojihye.airpollution.R;

import P_Manager.Realchart_Manager;
import P_Manager.Realtime_Manager;


/**
 * Created by user on 2016-08-02.
 */
public class Pager_Adapter extends PagerAdapter {

    LayoutInflater inflater;
    public static Context context;
    Handler pHandler;
    View realtime_view;
    View realchart_view;
    public static Realtime_Manager rm;
    public static Realchart_Manager rcm;
    public Pager_Adapter(LayoutInflater inflater, Context context)
    {
        this.inflater=inflater;
        this.context=context;
        //this.pHandler=adapter_handler;

    }

    @Override
    public int getCount() {
        return 3;
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


                    }
                });
                break;
            }
            case 1:
            {

                view=inflater.inflate(R.layout.air_realtime,null);
                realtime_view=view;
                rm=new Realtime_Manager(view);
                //popupMenu=new PopupMenu(context,view);
                //FrameLayout fr=(FrameLayout)view.findViewById(R.id.)
                LinearLayout ll=(LinearLayout)view.findViewById(R.id.all_layout);
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        //popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                        // here you set what you want to do when user clicks your button,
                        // e.g. launch a new activity
                        //Toast.makeText(context.getApplicationContext(),String.valueOf(v.getId()),Toast.LENGTH_SHORT).show();
                    }
                });
                container.addView(view);
                break;
            }
            case 2:
            {
                view=inflater.inflate(R.layout.air_realchart,null);
                rcm=new Realchart_Manager(view);
                realchart_view=view;

                container.addView(view);

                ListView ll=(ListView)view.findViewById(R.id.list_container);
                ListView_Adapter adapter=new ListView_Adapter();
                ll.setAdapter(adapter);
                adapter.addItem("CO2","","#FFAF0A");
                adapter.addItem("SO2","","#CD3C3C");
                adapter.addItem("NO2","","#FF02E402");
                adapter.addItem("O3","","#FFFF7F02");
                adapter.addItem("TEMP","","#FF904098");
                adapter.addItem("PM","","#FF904098");
                adapter.notifyDataSetChanged();
                container.addView(view);
                break;
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
