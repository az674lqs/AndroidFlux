package chanson.androidflux;

import android.app.Activity;
import android.util.SparseArray;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by liaoqinsen on 2017/5/12 0012.
 */

public class StoreDependencyDelegate {

    private Object dependency;
    private SparseArray<String> methodStore;

    public StoreDependencyDelegate(Object dependency){
        this.dependency = dependency;
        methodStore = new SparseArray();
    }

    public Object getDependency(){
        return dependency;
    }

    public void destroy(){
        this.dependency = null;
    }

    public void storeActionBindedMethod(SparseArray array){
        if(array == null) return;
        Method[] methods = this.dependency.getClass().getDeclaredMethods();
        for(Method method:methods){
            BindAction annotation = method.getAnnotation(BindAction.class);
            if(annotation != null){
                array.put(annotation.value(),method);
                methodStore.put(annotation.value(),method.getName());
            }
        }
    }

    public void invoke(int type,Object ...args){
        if(this.dependency == null) return;
        String methodName = methodStore.get(type).toString();
        if(methodName == null) return;
        try {
            Method method = this.dependency.getClass().getDeclaredMethod(methodName, HashMap.class);
            method.invoke(this.dependency,args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
