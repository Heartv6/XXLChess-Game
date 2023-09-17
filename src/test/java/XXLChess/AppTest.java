package XXLChess;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import java.util.*;

import javax.annotation.concurrent.ThreadSafe;

public class AppTest {
    @Test
    public void loadApp(){
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.settings();
        app.setup();
        app.keyPressed();
        app.keyReleased();
        app.delay(3000000);
        app.keyPressed();
        app.keyReleased();
        app.delay(300000);


        
    }
}