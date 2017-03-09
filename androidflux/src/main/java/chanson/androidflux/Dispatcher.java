package chanson.androidflux;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Chanson on 17/3/9.
 */
public class Dispatcher {
    public static void dispatch(Action action){
        EventBus.getDefault().post(action);
    }

    public static void regiter(Store store){
        EventBus.getDefault().register(store);
    }

    public static void unregister(Store store){
        EventBus.getDefault().unregister(store);
    }
}
