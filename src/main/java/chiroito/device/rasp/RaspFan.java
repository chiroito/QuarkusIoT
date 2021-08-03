package chiroito.device.rasp;

import chiroito.device.Fan;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.RaspiPin;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

@Singleton
public class RaspFan implements Fan {

    final GpioController gpio = GpioFactory.getInstance();
    final GpioPinPwmOutput fanPin = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_28);

    @PostConstruct
    public void init() {
        fanPin.setShutdownOptions(true);
    }

    @Override
    public void stop() {
        fanPin.setPwmRange(0);
    }

    @Override
    public void start() {
        fanPin.setPwmRange(70);
    }
}
