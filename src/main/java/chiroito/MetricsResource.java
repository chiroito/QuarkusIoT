package chiroito;

import chiroito.device.Button;
import chiroito.device.Fan;
import chiroito.device.Led;
import chiroito.device.Temparature;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.inject.Inject;

public class MetricsResource {


    @Inject
    Button button;

    @Inject
    Fan fan;

    @Inject
    Led led;

    @Inject
    Temparature temparature;

    @Gauge(name = "Temparature", unit = MetricUnits.NONE)
    public Double getTemparature(){
        try {
            return temparature.get();
        } catch (DeviceException e) {
            return 0.0;
        }
    }
}
