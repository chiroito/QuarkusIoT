package chiroito.device.rasp;

import chiroito.device.Led;
import com.pi4j.io.gpio.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

@Singleton
public class RaspLed implements Led {

    final GpioController gpio = GpioFactory.getInstance();

    final GpioPinPwmOutput redPin = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_04);
    final GpioPinPwmOutput greenPin = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_06);
    final GpioPinPwmOutput bluePin = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_05);

    @PostConstruct
    public void init() {
        redPin.setShutdownOptions(true);
        greenPin.setShutdownOptions(true);
        bluePin.setShutdownOptions(true);
    }

    @Override
    public void run() {
        redPin.setPwm(0);
        greenPin.setPwm(100);
        bluePin.setPwm(0);
    }

    @Override
    public void warn() {
        redPin.setPwm(70);
        greenPin.setPwm(70);
        bluePin.setPwm(0);
    }

    @Override
    public void error() {
        redPin.setPwm(100);
        greenPin.setPwm(0);
        bluePin.setPwm(0);
    }
}
