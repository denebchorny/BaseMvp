package com.denebasecas.basearquitecture.di.module;

import android.content.Context;

import com.denebasecas.basearquitecture.BaseApplication;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Deneb Chorny (denebchorny@gmail.com).
 */

@Module
public class BaseApplicationModule {

    BaseApplication mApplication;

    public BaseApplicationModule(BaseApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    BaseApplication providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    @Named("AppContext")
    public Context appContext() {
        return mApplication;
    }
}
