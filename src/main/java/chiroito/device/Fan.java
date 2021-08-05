package chiroito.device;

public interface Fan {
    public void stop();
    public void start();
    public Integer getPowerPercentage();
}
