package g60283.dev.ascii.view;

import g60283.dev.ascii.model.AsciiPaint;

public class View {
    public void display(AsciiPaint paint) {
        for (int i = 0; i < paint.getWidth(); i++) {
            for (int j = 0; j < paint.getHeight(); j++) {
                System.out.print(paint.getColorAt(i,j));
            }
            System.out.println();
        }
    }
    public void list(AsciiPaint paint) {

    }
}
