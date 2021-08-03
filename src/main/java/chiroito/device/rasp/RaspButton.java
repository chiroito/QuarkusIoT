package chiroito.device.rasp;

import chiroito.device.Button;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.util.function.Consumer;

@Singleton
public class RaspButton implements Button {

    final GpioController gpio = GpioFactory.getInstance();
    final GpioPinDigitalInput buttonPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_29, PinPullResistance.PULL_DOWN);

    @PostConstruct
    public void init(){
        buttonPin.setShutdownOptions(true);
    }

    @Override
    public void setTrigger(Consumer<?> trigger) {
        GpioPinListener myCallback = new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event){
                if(event.getState().isHigh()){
                    trigger.accept(null);
                    System.out.println("Buttonのトリガーでコールバックを実行した");
                } else {
                    System.out.println("Buttonのトリガーで何もしない");
                }
            }
        };
        buttonPin.addListener(myCallback);
    }
}
