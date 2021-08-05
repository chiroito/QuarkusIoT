package chiroito;

import chiroito.device.*;
import com.pi4j.io.gpio.*;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.*;

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

    @Inject
    BrokeFanButton brokeFanButton;

    @ConfigProperty(name = "iot.server", defaultValue = "localhost:8080")
    String server;

    @Inject
    @RestClient
    DeviceMonitorService monitorService;

    void onStart(@Observes StartupEvent ev) {

        button.setTrigger(e -> {
            System.out.println("ボタンのトリガーが実行されました");
            fan.start();
            led.run();
        });

        brokeFanButton.setTrigger(e -> {
            System.out.println("ボタンのトリガーが実行されました");
            fan.stop();
            led.error();
        });

        fan.start();
        led.run();

    }

    private boolean isOverThreshold = false;

    @Scheduled(every = "1s")
    private void monitor() {
        try {
            if (temparature.get() >= 28) {
                if (isOverThreshold == false) {
                    System.out.println("定期監視で28℃を超えました");
                    //ここはマイクロサービスに置き換える
                    monitorService.postInfo(temparature.getLastTime(), fan.getPowerPercentage(), server );
//                    fan.stop();
//                    led.warn();
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

    void onStop(@Observes ShutdownEvent ev) {
    }
}
