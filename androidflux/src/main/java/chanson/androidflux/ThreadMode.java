package chanson.androidflux;

/**
 * Created by Chanson on 17/3/9.
 */
public @interface ThreadMode {
    org.greenrobot.eventbus.ThreadMode value() default org.greenrobot.eventbus.ThreadMode.MAIN;
}
