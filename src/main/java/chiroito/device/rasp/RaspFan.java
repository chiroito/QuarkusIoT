package chiroito.device.rasp;

import chiroito.device.Fan;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.RaspiPin;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

@Singleton
public class RaspFan implements Fan {

    final GpioController gpio = GpioFactory.getInstance();
    final GpioPinPwmOutput fanPin = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_28);
    private boolean isRunning;

    @PostConstruct
    public void init() {
        fanPin.setShutdownOptions(true);
    }

    @Override
    public void stop() {
        fanPin.setPwm(0);
        this.isRunning = false;
        System.out.println("Fanを停止しました");
    }

    @Override
    public void start() {
        fanPin.setPwm(70);
        this.isRunning = true;
        System.out.println("Fanを動かしました");
    }

    @Gauge(name="FanRunning", unit = MetricUnits.NONE)
    public Boolean isRunning() {
        return this.isRunning;
    }
}
