package chanson.androidflux;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by liaoqinsen on 2017/5/12 0012.
 */

public class FluxActivity extends AppCompatActivity {

    private StoreDependencyDelegate storeDependencyDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeDependencyDelegate = new StoreDependencyDelegate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        storeDependencyDelegate.destroy();
    }
}
