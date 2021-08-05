package chiroito.device.rasp;

import chiroito.DeviceException;
import chiroito.device.Temparature;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class RaspTemparature implements Temparature {

    private I2CDevice device;
    private static final byte address_adt7410 = 0x48;
    private static final byte register_adt7410 = 0x00;
    private double lastTimeTemparature;

    private double readAdt7410() throws IOException {

        int word_data = device.read(register_adt7410);
        int data = ((word_data & 0xff00) >> 8) | ((word_data & 0xff) << 8);
        data = data >> 3;
        double temperature = 0.0f;
        if ((data & 0x1000) == 0) {
            temperature = ((double) data) * 0.0625f;
        } else {
            temperature = ((double) ((~data & 0x1fff) + 1)) * -0.0625f;
        }
        return temperature;
    }

    @PostConstruct
    public void init() throws DeviceException {
        try {
            I2CBus i2cBus = I2CFactory.getInstance(I2CBus.BUS_1);
            this.device = i2cBus.getDevice(address_adt7410);
        } catch (I2CFactory.UnsupportedBusNumberException | IOException e) {
            DeviceException de = new DeviceException("デバイスの初期化に失敗しました");
            de.addSuppressed(e);
            throw de;
        }
    }

    @Override
    public double get() throws DeviceException {
        try {
            double currentTemparature = readAdt7410();
            if (currentTemparature != lastTimeTemparature) {
                System.out.println("温度は" + currentTemparature + "℃に変りました");
            }
            lastTimeTemparature = currentTemparature;
            return currentTemparature;
        } catch (IOException e) {
            DeviceException de = new DeviceException("デバイスの情報取得に失敗しました");
            de.addSuppressed(e);
            throw de;
        }
    }

    @Override
    public double getLastTime() {
        return this.lastTimeTemparature;
    }

    @Gauge(name = "LastTimeTemparature", unit = MetricUnits.NONE)
    public double getLastTimeTemparature() {
        return lastTimeTemparature;
    }
}
