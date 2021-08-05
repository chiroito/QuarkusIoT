package chiroito;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceMonitorInfo {
    public double temparature;
    public int fanSpeed;
    public String deviceName;
}
