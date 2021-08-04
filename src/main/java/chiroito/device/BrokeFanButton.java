package chiroito.device;

import java.util.function.Consumer;

public interface BrokeFanButton {
    void setTrigger(Consumer<?> trigger);
}
