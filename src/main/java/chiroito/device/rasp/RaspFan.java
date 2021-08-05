package chiroito.device.rasp;

import chiroito.device.Fan;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.RaspiPin;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

@Singleton
public class RaspFan implements Fan {

    final GpioController gpio = GpioFactory.getInstance();
    final GpioPinPwmOutput p0 = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_02);
    final GpioPinPwmOutput p1 = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_03);
    private int powerPercentage;

    @PostConstruct
    public void init() {
        p0.setShutdownOptions(true);
        p1.setShutdownOptions(true);
        p1.setPwm(0);
    }

    @Override
    public void stop() {
        this.setRotationSpeed(0);
        System.out.println("Fanを停止しました");
    }

    @Override
    public void start() {
        this.setRotationSpeed(10);
        System.out.println("Fanを動かしました");
    }

    private void setRotationSpeed(int percentage){
        p0.setPwm(percentage);
        this.powerPercentage = percentage;
    }

    @Gauge(name="FanPowerPercentage", unit = MetricUnits.NONE)
    public Integer getPowerPercentage() {
        return this.powerPercentage;
    }
}
