package P_Data;

import android.widget.LinearLayout;

/**
 * Created by user on 2016-07-13.
 */
public class ListViewItem {
    //private Drawable iconDrawable ;
    private int Color;
    private String titleStr ;
    private String descStr ;
    private LinearLayout linearLayout;
    /*public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }*/
    public void setColor(int color){this.Color=color;}
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    /*public Drawable getIcon() {
        return this.iconDrawable ;
    }*/
    public int getColor() {return this.Color;}
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public static boolean login_check=false;
}
