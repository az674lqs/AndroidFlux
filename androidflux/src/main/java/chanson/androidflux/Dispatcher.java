package chanson.androidflux;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chanson on 17/3/9.
 */
public class Dispatcher {

    private static LinkedHashMap<Store,Action> stores = new LinkedHashMap<>();
    private static LinkedList<Action> actions = new LinkedList<>();

    private static Object lock = new Object();

    private static Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true){
                synchronized (lock){
                    if(stores.size() < 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Store store = stores.keySet().iterator().next();
                    Action action = stores.get(store);
                    if(store != null && action != null) store.doAction(action);
                    if(store != null) {
                        stores.remove(store);
                    }
                }
            }
        }
    });

    static {
        thread.start();
    }

    public static void dispatch(Action action,Store store){
        synchronized (lock){
            stores.put(store,action);
            lock.notify();
        }
    }

}
