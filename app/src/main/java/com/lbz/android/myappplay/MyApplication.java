package com.lbz.android.myappplay;

import android.app.Application;
import android.content.Context;

import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerAppComponent;
import com.lbz.android.myappplay.di.module.AppModule;
import com.lbz.android.myappplay.di.module.HttpModule;
import com.lbz.android.myappplay.ui.typeface.LbzFont;
import com.mikepenz.iconics.Iconics;

/**
 * Created by elitemc on 2017/7/11.
 */
public class MyApplication extends Application {

    private AppComponent mAppComponent;

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static MyApplication getApplication(Context context){
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //only required if you add a custom or generic font on your own
        Iconics.init(getApplicationContext());

        //register custom fonts like this (or also provide a font definition file)
        Iconics.registerFont(new LbzFont());


        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .httpModule(new HttpModule())
                .build();

    }
}
