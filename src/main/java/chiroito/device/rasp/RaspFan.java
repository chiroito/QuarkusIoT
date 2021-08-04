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
    final GpioPinPwmOutput fanPin = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_25);
    private int powerPercentage;

    @PostConstruct
    public void init() {
        fanPin.setShutdownOptions(true);
    }

    @Override
    public void stop() {
        fanPin.setPwm(0);
        this.powerPercentage = 0;
        System.out.println("Fanを停止しました");
    }

    @Override
    public void start() {
        fanPin.setPwm(70);
        this.powerPercentage = 70;
        System.out.println("Fanを動かしました");
    }

    @Gauge(name="FanPowerPercentage", unit = MetricUnits.NONE)
    public Integer getPowerPercentage() {
        return this.powerPercentage;
    }
}
