package g60283.dev.ascii;

import g60283.dev.ascii.controller.AsciiController;
import g60283.dev.ascii.model.AsciiPaint;
import g60283.dev.ascii.view.View;

public class App {
    public static void main(String[] args) {
        AsciiController c = new AsciiController();
        c.start();
    }
}
