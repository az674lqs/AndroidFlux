package chanson.androidflux;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;


import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


/**
 * Created by Chanson on 17/3/9.
 */
public abstract class Store{

    public Handler handler;
    private StoreDependencyDelegate delegate;
    private SparseArray<Method> methodSparseArray;

    public Store(StoreDependencyDelegate delegate){
        this.delegate = delegate;
        methodSparseArray = new SparseArray<>();
        this.delegate.storeActionBindedMethod(methodSparseArray);
        if(Thread.currentThread().getName().equals("main")) {
            handler = new Handler();
        }else{
            throw new RuntimeException("Store must be initialized in main thread!");
        }
    }

    public void doAction(final Action action){
        if(action != null && action.type > -1) {
            doAction(action.type, action, new StoreResultCallBack() {
                @Override
                public void onResult(final int type, final JSONObject data) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                methodSparseArray.get(type).invoke(delegate.getDependency(),data);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });

        }
    }

    public abstract void doAction(int type,HashMap<String,Object> data,StoreResultCallBack callBack);

    public void destroy(){
        this.handler = null;
    }

    public interface StoreResultCallBack{
        public void onResult(int type,JSONObject data);
    }
}
