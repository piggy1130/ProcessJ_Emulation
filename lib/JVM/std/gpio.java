package std;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;

import com.pi4j.io.gpio.digital.*;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.gpio.digital.PullResistance;

import java.util.HashMap;
import java.util.Map;

public class gpio {

    private static volatile boolean taskRunning = false; // global flag
    private static Context pi4j = Pi4J.newAutoContext();

    // Store created DigitalOutput instances by pin number
    private static Map<Integer, DigitalOutput> ledMap = new HashMap<>();
    private static Map<Integer, DigitalInput> buttonMap = new HashMap<>();

    private static DigitalOutput getLedOutput(int pinNumber) {
        if (ledMap.containsKey(pinNumber)) {
            return ledMap.get(pinNumber);
        }

        DigitalOutputConfig config = DigitalOutput.newConfigBuilder(pi4j)
                .id("led-" + pinNumber)
                .name("LED Pin " + pinNumber)
                .address(pinNumber)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("gpiod-digital-output")
                .build();

        DigitalOutput led = pi4j.create(config);
        ledMap.put(pinNumber, led);  // Save it for reuse
        return led;
    }

    private static DigitalInput getButtonInput(int pinNumber) {
        if (buttonMap.containsKey(pinNumber)) {
            return buttonMap.get(pinNumber);
        }

        DigitalInputConfig config = DigitalInput.newConfigBuilder(pi4j)
                .id("button-" + pinNumber)
                .name("Button Pin " + pinNumber)
                .address(pinNumber)
                // keeps the pin HIGH when the button is not pressed
                // change to LOW when button pressed
                .pull(PullResistance.PULL_UP) // Use internal pull-up
                .debounce(3000L)               // Debounce time in microseconds
                .provider("gpiod-digital-input")
                .build();

        DigitalInput button = pi4j.create(config);
        buttonMap.put(pinNumber, button);  // Save it for reuse
        return button;
    }

    // pinNumber is GPIO# - number after GPIO
    public static void turnOnLed(int pinNumber) {
        DigitalOutput led = getLedOutput(pinNumber);
        System.out.println("LED high");
        led.high();
    }

    public static void turnOffLed(int pinNumber) {
        DigitalOutput led = getLedOutput(pinNumber);
        System.out.println("LED low");
        led.low();
    }

    public static void listenToButton(int pinNumber) {
        DigitalInput button = getButtonInput(pinNumber);

        button.addListener(event -> {
            if (event.state() == DigitalState.LOW) { // button pressed
                System.out.println("Button PRESSED on pin " + pinNumber);
                taskRunning = !taskRunning;
                System.out.println("Task " + (taskRunning ? "STARTED" : "STOPPED"));
            } 
        });

        
    }


    public static void shutdown() {
        pi4j.shutdown();
    }
}
