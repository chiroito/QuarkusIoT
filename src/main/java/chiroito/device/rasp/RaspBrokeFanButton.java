package chiroito.device.rasp;

import chiroito.device.BrokeFanButton;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;

public class RaspBrokeFanButton implements BrokeFanButton {

    final GpioController gpio = GpioFactory.getInstance();
    final GpioPinDigitalInput buttonPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_28, PinPullResistance.PULL_DOWN);

    @PostConstruct
    public void init(){
        buttonPin.setShutdownOptions(true);
    }

    @Override
    public void setTrigger(Consumer<?> trigger) {
        GpioPinListener myCallback = new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event){
                if(event.getState().isHigh()){
                    System.out.println("回路が壊れました");
                    trigger.accept(null);
                }
            }
        };
        buttonPin.addListener(myCallback);
    }
}
