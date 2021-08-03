package chiroito;

import chiroito.device.Button;
import chiroito.device.Fan;
import chiroito.device.Led;
import chiroito.device.Temparature;
import com.pi4j.io.gpio.*;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.QuarkusMain;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class QuarkusIoTDemo {

    @Inject
    Button button;

    @Inject
    Fan fan;

    @Inject
    Led led;

    @Inject
    Temparature temparature;

    final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    void onStart(@Observes StartupEvent ev) {

        button.setTrigger(e -> {
            fan.start();
            led.run();
            System.out.println("ボタンのトリガーが実行されました");
        });

        Runnable monitorTask = new Runnable() {
            private boolean isOverThreshold = false;

            public void run() {
                try {
                    if (temparature.get() >= 28) {
                        fan.stop();
                        led.error();
                        if (isOverThreshold == false) {
                            System.out.println("定期監視で28℃を超えました");
                        }
                        isOverThreshold = true;
                    } else {
                        if (isOverThreshold == true) {
                            System.out.println("定期監視で温度が達しませんでした");
                        }
                        isOverThreshold = false;
                    }
                } catch (DeviceException e) {
                    Quarkus.blockingExit();
                }
            }
        };

        fan.start();
        led.run();

        scheduledExecutorService.scheduleAtFixedRate(monitorTask, 1, 1, TimeUnit.SECONDS);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduledExecutorService.shutdownNow();
    }
}
