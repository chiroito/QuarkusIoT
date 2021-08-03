package chiroito.device;

import java.util.function.Consumer;

public interface Button {

    void setTrigger(Consumer<?> trigger);

}
