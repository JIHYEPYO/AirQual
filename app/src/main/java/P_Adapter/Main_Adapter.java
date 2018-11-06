package P_Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pyojihye.airpollution.AQI_calculate;
import com.example.pyojihye.airpollution.R;

import P_Data.DBHelper;
import P_Data.Util_STATUS;

import static com.example.pyojihye.airpollution.AQI_calculate.*;

/**
 * Created by PYOJIHYE on 2016-08-12.
 */
public class Main_Adapter extends PagerAdapter {

    LayoutInflater inflater;
    Context context;

    DBHelper helper;
    SQLiteDatabase db;
    final int SIZE = 20;

    public Main_Adapter(LayoutInflater inflater, Context context) {
        this.inflater=inflater;
        this.context=context;
        helper=new DBHelper(context);
        db=helper.getWritableDatabase();

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=null;

        Cursor cursor=db.rawQuery("SELECT * FROM Air_data ",null);
        cursor.moveToNext();

        if(cursor.getCount()==0){
            AQI_calculate.setAirdata(-1,-1,-1,-1,-1);
        }else{
            AQI_calculate.setAirdata(cursor.getDouble(2),cursor.getDouble(3) , cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6));
        }

        switch(position) {
            case 0: { //CO

                view=inflater.inflate(R.layout.fr_main,null);
                ImageView imageView = (ImageView) view.findViewById(R.id.faceimage);

                Util_STATUS.AQI_CO=CO_AQI_Cal();

                switch (AQI(Util_STATUS.AQI_CO)){
                    case 1:
                        imageView.setImageResource(R.drawable.mood02);
                        ((TextView)view.findViewById(R.id.facetext)).setText("CO AQI is Good!");
                        break;

                    case 2:
                        imageView.setImageResource(R.drawable.mood03);
                        ((TextView)view.findViewById(R.id.facetext)).setText("CO AQI is Moderate!");
                        break;

                    case 3:
                        imageView.setImageResource(R.drawable.mood04);
                        ((TextView)view.findViewById(R.id.facetext)).setText("CO AQI is Unhealthy for Sensitive Groups!");
                        ((TextView)view.findViewById(R.id.facetext)).setTextSize(SIZE);
                        break;

                    case 4:
                        imageView.setImageResource(R.drawable.mood05);
                        ((TextView)view.findViewById(R.id.facetext)).setText("CO AQI is Unhealthy!");
                        break;

                    case 5:
                        imageView.setImageResource(R.drawable.mood06);
                        ((TextView)view.findViewById(R.id.facetext)).setText("CO AQI is Very Unhealthy!");
                        break;

                    case 6:
                        imageView.setImageResource(R.drawable.mood07);
                        ((TextView)view.findViewById(R.id.facetext)).setText("CO AQI is Hazardous!");
                        break;
                    default:
                        imageView.setImageResource(R.drawable.mood01);
                        ((TextView)view.findViewById(R.id.facetext)).setText("Not connected!");

                }

                container.addView(view);
                break;
            }
            case 1: //SO2
            {
                view=inflater.inflate(R.layout.fr_main,null);
                ImageView imageView = (ImageView) view.findViewById(R.id.faceimage);
                Util_STATUS.AQI_SO2=SO2_AQI_Cal();

                switch (AQI(Util_STATUS.AQI_SO2)){
                    case 1:
                        imageView.setImageResource(R.drawable.mood02);
                        ((TextView)view.findViewById(R.id.facetext)).setText("SO2 AQI is Good!");
                        break;

                    case 2:
                        imageView.setImageResource(R.drawable.mood03);
                        ((TextView)view.findViewById(R.id.facetext)).setText("SO2 AQI is Moderate!");
                        break;

                    case 3:
                        imageView.setImageResource(R.drawable.mood04);
                        ((TextView)view.findViewById(R.id.facetext)).setText("SO2 AQI is Unhealthy for Sensitive Groups!");
                        ((TextView)view.findViewById(R.id.facetext)).setTextSize(SIZE);
                        break;

                    case 4:
                        imageView.setImageResource(R.drawable.mood05);
                        ((TextView)view.findViewById(R.id.facetext)).setText("SO2 AQI is Unhealthy!");
                        break;

                    case 5:
                        imageView.setImageResource(R.drawable.mood06);
                        ((TextView)view.findViewById(R.id.facetext)).setText("SO2 AQI is Very Unhealthy!");
                        break;

                    case 6:
                        imageView.setImageResource(R.drawable.mood07);
                        ((TextView)view.findViewById(R.id.facetext)).setText("SO2 AQI is Hazardous!");
                        break;

                    default:
                        imageView.setImageResource(R.drawable.mood01);
                        ((TextView)view.findViewById(R.id.facetext)).setText("Not connected!");

                }
                container.addView(view);

                break;
            }
            case 2: //NO2
            {
                view=inflater.inflate(R.layout.fr_main,null);
                ImageView imageView = (ImageView) view.findViewById(R.id.faceimage);
                Util_STATUS.AQI_NO2=NO2_AQI_Cal();

                switch (AQI(Util_STATUS.AQI_CO)){
                    case 1:
                        imageView.setImageResource(R.drawable.mood02);
                        ((TextView)view.findViewById(R.id.facetext)).setText("NO2 AQI is Good!");
                        break;

                    case 2:
                        imageView.setImageResource(R.drawable.mood03);
                        ((TextView)view.findViewById(R.id.facetext)).setText("NO2 AQI is Moderate!");
                        break;

                    case 3:
                        imageView.setImageResource(R.drawable.mood04);
                        ((TextView)view.findViewById(R.id.facetext)).setText("NO2 AQI is Unhealthy for Sensitive Groups!");
                        ((TextView)view.findViewById(R.id.facetext)).setTextSize(SIZE);
                        break;

                    case 4:
                        imageView.setImageResource(R.drawable.mood05);
                        ((TextView)view.findViewById(R.id.facetext)).setText("NO2 AQI is Unhealthy!");
                        break;

                    case 5:
                        imageView.setImageResource(R.drawable.mood06);
                        ((TextView)view.findViewById(R.id.facetext)).setText("NO2 AQI is Very Unhealthy!");
                        break;

                    case 6:
                        imageView.setImageResource(R.drawable.mood07);
                        ((TextView)view.findViewById(R.id.facetext)).setText("NO2 AQI is Hazardous!");
                        break;

                    default:
                        imageView.setImageResource(R.drawable.mood01);
                        ((TextView)view.findViewById(R.id.facetext)).setText("Not connected!");

                }
                container.addView(view);
                break;
            }
            case 3: //O3
            {
                view=inflater.inflate(R.layout.fr_main,null);
                ImageView imageView = (ImageView) view.findViewById(R.id.faceimage);
                Util_STATUS.AQI_O3 =O3_EIGHT_AQI_Cal();


                switch (AQI(Util_STATUS.AQI_CO)){
                    case 1:
                        imageView.setImageResource(R.drawable.mood02);
                        ((TextView)view.findViewById(R.id.facetext)).setText("O3 AQI is Good!");
                        break;

                    case 2:
                        imageView.setImageResource(R.drawable.mood03);
                        ((TextView)view.findViewById(R.id.facetext)).setText("O3 AQI is Moderate!");
                        break;

                    case 3:
                        imageView.setImageResource(R.drawable.mood04);
                        ((TextView)view.findViewById(R.id.facetext)).setText("O3 AQI is Unhealthy for Sensitive Groups!");
                        ((TextView)view.findViewById(R.id.facetext)).setTextSize(SIZE);
                        break;

                    case 4:
                        imageView.setImageResource(R.drawable.mood05);
                        ((TextView)view.findViewById(R.id.facetext)).setText("O3 AQI is Unhealthy!");
                        break;

                    case 5:
                        imageView.setImageResource(R.drawable.mood06);
                        ((TextView)view.findViewById(R.id.facetext)).setText("O3 AQI is Very Unhealthy!");
                        break;

                    case 6:
                        imageView.setImageResource(R.drawable.mood07);
                        ((TextView)view.findViewById(R.id.facetext)).setText("O3 AQI is Hazardous!");
                        break;

                    default:
                        imageView.setImageResource(R.drawable.mood01);
                        ((TextView)view.findViewById(R.id.facetext)).setText("Not connected!");

                }
                container.addView(view);
                break;
            }
            case 4: //PM 2.5
            {
                view=inflater.inflate(R.layout.fr_main,null);
                ImageView imageView = (ImageView) view.findViewById(R.id.faceimage);
                Util_STATUS.AQI_PM=PM_AQI_Cal();

                switch (AQI(Util_STATUS.AQI_CO)){
                    case 1:
                        imageView.setImageResource(R.drawable.mood02);
                        ((TextView)view.findViewById(R.id.facetext)).setText("PM 2.5 AQI is Good!");
                        break;

                    case 2:
                        imageView.setImageResource(R.drawable.mood03);
                        ((TextView)view.findViewById(R.id.facetext)).setText("PM 2.5 AQI is Moderate!");
                        break;

                    case 3:
                        imageView.setImageResource(R.drawable.mood04);
                        ((TextView)view.findViewById(R.id.facetext)).setText("PM 2.5 AQI is Unhealthy for Sensitive Groups!");
                        ((TextView)view.findViewById(R.id.facetext)).setTextSize(SIZE);

                        break;

                    case 4:
                        imageView.setImageResource(R.drawable.mood05);
                        ((TextView)view.findViewById(R.id.facetext)).setText("PM 2.5 AQI is Unhealthy!");
                        break;

                    case 5:
                        imageView.setImageResource(R.drawable.mood06);
                        ((TextView)view.findViewById(R.id.facetext)).setText("PM 2.5 AQI is Very Unhealthy!");
                        break;

                    case 6:
                        imageView.setImageResource(R.drawable.mood07);
                        ((TextView)view.findViewById(R.id.facetext)).setText("PM 2.5 AQI is Hazardous!");
                        break;

                    default:
                        imageView.setImageResource(R.drawable.mood01);
                        ((TextView)view.findViewById(R.id.facetext)).setText("Not connected!");

                }
                container.addView(view);
                break;
            }
        }
        return view;
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
