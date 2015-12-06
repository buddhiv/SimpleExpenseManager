package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.app.Application;
import android.content.Context;

/**
 * Created by Yasiru on 12/6/2015.
 */
public class ApplicationContext extends Application{
    private static Context context;

    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
