package chiroito.device.rasp;

import chiroito.device.Led;
import com.pi4j.io.gpio.*;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

@Singleton
public class RaspLed implements Led {

    final GpioController gpio = GpioFactory.getInstance();

    final GpioPinPwmOutput redPin = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_04);
    final GpioPinPwmOutput greenPin = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_06);
    final GpioPinPwmOutput bluePin = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_05);

    private int green;
    private int blue;
    private int red;

    @PostConstruct
    public void init() {
        redPin.setShutdownOptions(true);
        greenPin.setShutdownOptions(true);
        bluePin.setShutdownOptions(true);
    }

    @Override
    public void run() {
        apply(0, 0, 100);
        System.out.println("LEDはRunになりました");
    }

    @Override
    public void warn() {
        apply(100, 50, 0);
        System.out.println("LEDはWarnになりました");
    }

    @Override
    public void error() {
        apply(100, 0, 0);
        System.out.println("LEDはErrorになりました");
    }

    private void apply(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        redPin.setPwm(red);
        greenPin.setPwm(green);
        bluePin.setPwm(blue);
    }

    @Gauge(name = "LedRed", unit = MetricUnits.NONE)
    public int getRed() {
        return red;
    }

    @Gauge(name = "LedGreen", unit = MetricUnits.NONE)
    public int getGreen() {
        return green;
    }

    @Gauge(name = "LedBlue", unit = MetricUnits.NONE)
    public int getBlue() {
        return blue;
    }
}
