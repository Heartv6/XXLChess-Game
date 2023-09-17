package XXLChess;


import processing.core.PApplet;
import processing.event.MouseEvent;
import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.checkerframework.checker.units.qual.A;

public class SampleTest {

    @Test
    public void simpleTest() {
        App app = new App();
        app.test = true;
        app.loop();
        app.loop();
        app.settings();
        app.setup();
        PApplet.runSketch(new String[] { "App" }, app);
        PApplet.runSketch(new String[] { "App" }, app);
        PApplet.runSketch(new String[] { "App" }, app);
        app.settings();
        app.setup();
        app.delay(3000000);
        PApplet.runSketch(new String[] { "App" }, app);
        PApplet.runSketch(new String[] { "App" }, app);
        app.delay(3000000);

    }

   

}
