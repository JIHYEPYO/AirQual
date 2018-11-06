package P_Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by user on 2016-08-08.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="Air data";
    private static final int DATABASE_VERSION=2;

    public DBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Air_data");
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Gps_data");
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Hr_data");
        sqLiteDatabase.execSQL( "CREATE TABLE IF NOT EXISTS Air_data ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "regdate INTEGER, "
                + "CO Double,"
                + "SO2 Double,"
                + "NO2 Double,"
                + "O3 Double,"
                + "PM Double,"
                + "Lat Double,"
                + "Lon Double"
                + " );");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Gps_data("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"regdate long,"
                +"Lat Double,"
                +"Lon Double"+");");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Hr_data("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"regdate long,"
                +"HR INTEGER,"
                +"Lat Double,"
                +"Lon Double);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Air_data");
        onCreate(sqLiteDatabase);
    }
    String data;
    Cursor cursor;
    public void insert_ar()
    {
        ContentValues values = new ContentValues();

    }

    public String Today_max_val()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        cursor=db.rawQuery("SELECT MAX(CO) FROM Air_data",null);

        Date date=new Date(System.currentTimeMillis());
        cursor.moveToNext();
        int data=cursor.getInt(0);
        /*while(cursor.moveToNext())
        {
            int data=cursor.getInt(0);

        }*/


        return String.valueOf(data);
    }
    public long startOfDay() {

        Date d = new Date();
        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);

        //d.setTime(0);
        return d.getTime();
    }

    public String Today_min_val()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        cursor.moveToNext();
        int data=cursor.getInt(0);
        /*while(cursor.moveToNext())
        {

            int data=cursor.getInt(0);

        }*/
        return String.valueOf(data);
    }
    public String Today_avg_val()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        cursor=db.rawQuery("SELECT AVG(CO) FROM Air_data",null);
        cursor.moveToNext();
        int data=cursor.getInt(0);
        /*while(cursor.moveToNext())
        {

            int data=cursor.getInt(0);

        }*/
        return String.valueOf(data);
    }
    public void Get_All_data()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        cursor=db.rawQuery("SELECT * FROM Air_data",null);

    }
    public LatLng Get_Gps_data()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        cursor=db.rawQuery("SELECT * FROM Gps_data",null);
        /*while(cursor.moveToNext())
        {

           long unixTime = 932545204L * 1000;

            Date date = new Date(unixTime);

            System.out.println(date.toString());

            int data=cursor.getInt(0);
        }*/

        cursor.move(cursor.getCount()-1);
        return new LatLng(cursor.getDouble(2),cursor.getDouble(3));

    }

}
