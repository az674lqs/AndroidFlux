package chanson.androidflux;

import android.os.Bundle;

/**
 * Created by Chanson on 17/3/9.
 */
public abstract class Action {

    public int type;
    public Bundle data;

    public Action(int type){
        this.type = type;
    }

    @ThreadMode
    public void done(int actionType,Bundle result){

    }

    public void emmit(Bundle data){
        this.data = data;
        Dispatcher.dispatch(this);
    }
}
