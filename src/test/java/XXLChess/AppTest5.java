package XXLChess;

import processing.core.PApplet;
import processing.core.PImage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import java.util.*;

import javax.annotation.concurrent.ThreadSafe;

public class AppTest5 {
    @Test
    public void loadApp(){
        App app = new App();
        app.loop();
        app.settings();
        app.setup();
        PApplet.runSketch(new String[] { "App" }, app);
        app.settings();
        app.setup();
        app.delay(300000);


    }
}