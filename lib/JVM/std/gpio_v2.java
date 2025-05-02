import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.SimpleLed;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.Gpio;

public class gpio_v2{

    private static Context pi4j = Pi4J.newAutoContext();

    public static void turnOnLed_v2(int pinNumber) {
        Pin pin = Gpio.pinByAddress(pinNumber);
        SimpleLed led = new SimpleLed(pi4j, pin);
        System.out.println("LED ON");
        led.on();
    }
}
