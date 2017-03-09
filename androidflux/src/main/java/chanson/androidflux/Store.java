package chanson.androidflux;

import android.os.Bundle;
import android.os.Handler;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by Chanson on 17/3/9.
 */
public abstract class Store {

    public Handler handler;

    public Store(){
        Dispatcher.regiter(this);
        if(Thread.currentThread().getName().equals("main")) {
            handler = new Handler();
        }else{
            throw new RuntimeException("Store must be initialized in main thread!");
        }
    }

    @Subscribe(threadMode=ThreadMode.ASYNC)
    public void doAction(final Action action){
        if(action != null && action.type > -1) {
            final Bundle result = doAction(action.type, action.data);
            ThreadMode threadMode = ThreadMode.MAIN;
            try {
                threadMode = action.getClass()
                        .getDeclaredMethod("done",Integer.class,Bundle.class)
                        .getAnnotation(chanson.androidflux.ThreadMode.class)
                        .value();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            switch (threadMode){
                case ASYNC:
                    if(Thread.currentThread().getName().equals("main")){

                    }else{
                        action.done(action.type,result);
                    }
                    break;
                default:
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            action.done(action.type,result);
                        }
                    });
                    break;
            }
        }
    }

    public abstract Bundle doAction(int type,Bundle data);

    public void destroy(){
        this.handler = null;
        Dispatcher.unregister(this);
    }
}
