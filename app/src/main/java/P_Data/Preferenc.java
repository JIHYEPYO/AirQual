package P_Data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 2016-08-08.
 */
public class Preferenc extends Activity {
    SharedPreferences pref;
    Context mContext;
    public Preferenc(Context context) {
        mContext=context;
        pref= PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    // 값 저장하기
    public String getPreferences(){
        pref = getSharedPreferences("pref",mContext.MODE_PRIVATE);
        //pref.getString("hi", "");
        return pref.getString("MAC","");

    }

    // 값 저장하기
    public void savePreferences(String MAC){
        pref = getSharedPreferences("pref",mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("MAC",MAC);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    public  void removePreferences(){
        SharedPreferences pref = getSharedPreferences("pref", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("hi");
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    public void removeAllPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}
