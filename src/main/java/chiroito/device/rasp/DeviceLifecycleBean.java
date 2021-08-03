package chiroito.device.rasp;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class DeviceLifecycleBean {

    final GpioController gpio = GpioFactory.getInstance();


    void onStart(@Observes StartupEvent ev) {
    }

    void onStop(@Observes ShutdownEvent ev) {
        gpio.shutdown();
    }
}
