package com.capstone.paypal.common;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.Date;
import java.util.Random;

public class Simulator {

    public final Robot robot;
    public final Clipboard clipboard;

    static {
        System.setProperty("java.awt.headless", "false");
    }

    public Simulator() throws AWTException {
        this.robot = new Robot();
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public class PixelColor {
        public int id;
        public int xFrom;
        public int yFrom;
        public int xTo;
        public int yTo;
        public int redMin;
        public int redMax;
        public int greenMin;
        public int greenMax;
        public int blueMin;
        public int blueMax;

        public PixelColor(int id, int xFrom, int yFrom, int xTo, int yTo, int color, int delta) {
            this.id = id;
            this.xFrom = xFrom;
            this.yFrom = yFrom;
            this.xTo = xTo;
            this.yTo = yTo;
            Color colorObj = new Color(color);
            int red = (color >> 16) & 0xFF;
            int green = (color >> 8) & 0xFF;
            int blue = (color >> 0) & 0xFF;
            this.redMin = Math.max(0, red - delta);
            this.redMax = Math.min(0xFF, red + delta);
            this.greenMin = Math.max(0, green - delta);
            this.greenMax = Math.min(0xFF, green + delta);
            this.blueMin = Math.max(0, blue - delta);
            this.blueMax = Math.min(0xFF, blue + delta);
        }
    }


    public PixelColor createPixelColor(int id, int xFrom, int yFrom, int xTo, int yTo, int color, int delta) {
        return new PixelColor(id, xFrom, yFrom, xTo, yTo, color, delta);
    }

    public int waitForMultiPixel(PixelColor... pixelColors) throws InterruptedException {
        int dxMax;
        int dyMax;
        int red;
        int green;
        int blue;
        while (true) {
            for (PixelColor p : pixelColors) {
                dxMax = p.xTo - p.xFrom;
                dyMax = p.yTo - p.yFrom;
                for (int dx = 0; dx <= dxMax; dx++) {
                    for (int dy = 0; dy <= dyMax; dy++) {
                        Color color = robot.getPixelColor(p.xFrom + dx, p.yFrom + dy);
                        red = color.getRed();
                        green = color.getGreen();
                        blue = color.getBlue();
                        if (p.redMin <= red && red <= p.redMax && p.greenMin <= green && green <= p.greenMax && p.blueMin <= blue && blue <= p.greenMax) {
                            return p.id;
                        }
                    }
                }
            }
            delay(50l);
        }
    }

    public void delay(long milisecs) {
        try {
            Thread.sleep(milisecs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delayRandomShort() {
        try {
            Random r = new Random();
            Thread.sleep(r.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delayRandomMedium() {
        try {
            Random r = new Random();
            Thread.sleep(50 + r.nextInt(150));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void waitForPixel(int x, int y, int rgb) throws InterruptedException {
        while (true) {
            if (rgb == robot.getPixelColor(x, y).getRGB()) {
                return;
            } else {
                delay(50l);
            }
        }
    }

    public void clickInBox(int x0, int y0, int dx, int dy) throws InterruptedException {
        Random ran = new Random();
        int x = x0 + ran.nextInt(dx + 1);
        int y = y0 + ran.nextInt(dy + 1);

        robot.mouseMove(x, y);
        delay(10l);
        robot.mouseMove(x, y);
        delay(10l);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        delay(10l);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void moveMouseFast(int x, int y) throws InterruptedException {
        Point p = MouseInfo.getPointerInfo().getLocation();
        double dis = Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
        double jump = dis / 10;
        double dx = (x - p.x) / jump;
        double dy = (y - p.y) / jump;
        double xt = p.x;
        double yt = p.y;
        for (int i = (int) jump; i > 0; i--) {
            delay(10l);
            xt += dx;
            yt += dy;
            robot.mouseMove((int) Math.round(xt), (int) Math.round(yt));
        }
        robot.mouseMove(x, y);
    }

    public void moveAndClickInBox(int x0, int y0, int dx, int dy) throws InterruptedException {
        Random ran = new Random();
        int x = x0 + ran.nextInt(dx + 1);
        int y = y0 + ran.nextInt(dy + 1);
        moveMouseFast(x, y);
        delayRandomMedium();
        robot.mousePress(InputEvent.BUTTON1_MASK);
        delayRandomMedium();
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public Point waitForColor(int xFrom, int yFrom, int xTo, int yTo, int rgb, long maxWait) throws InterruptedException {
        long time0 = new Date().getTime();
        long timeMax = time0 + maxWait;

        Rectangle captureSize = new Rectangle(xFrom, yFrom, xTo - xFrom, yTo - yFrom);
        BufferedImage screen;
        DataBuffer scrDB;

        int scrW = xTo - xFrom + 1;
        int scrH = yTo - yFrom + 1;
        while (true) {
            screen = robot.createScreenCapture(captureSize);
            scrDB = screen.getData().getDataBuffer();

            System.out.println(scrDB.toString());
            for (int y = 0; y < scrH; y++) {
                for (int x = 0; x < scrW; x++) {
                    if (rgb == scrDB.getElem(y * scrW + x)) {
                        return new Point(x + xFrom, y + yFrom);
                    }
                }
            }
            if (new Date().getTime() >= timeMax) {
                return null;
            } else {
                delay(50l);
            }
        }
    }

    public void copyParseString(String str) {
        StringSelection stringSelection = new StringSelection(str);
        clipboard.setContents(stringSelection, null);
        delayRandomShort();
        doType(KeyEvent.VK_CONTROL, KeyEvent.VK_V);

    }


    public void type(char character) {
        switch (character) {
            case 'a':
                doType(KeyEvent.VK_A);
                break;
            case 'b':
                doType(KeyEvent.VK_B);
                break;
            case 'c':
                doType(KeyEvent.VK_C);
                break;
            case 'd':
                doType(KeyEvent.VK_D);
                break;
            case 'e':
                doType(KeyEvent.VK_E);
                break;
            case 'f':
                doType(KeyEvent.VK_F);
                break;
            case 'g':
                doType(KeyEvent.VK_G);
                break;
            case 'h':
                doType(KeyEvent.VK_H);
                break;
            case 'i':
                doType(KeyEvent.VK_I);
                break;
            case 'j':
                doType(KeyEvent.VK_J);
                break;
            case 'k':
                doType(KeyEvent.VK_K);
                break;
            case 'l':
                doType(KeyEvent.VK_L);
                break;
            case 'm':
                doType(KeyEvent.VK_M);
                break;
            case 'n':
                doType(KeyEvent.VK_N);
                break;
            case 'o':
                doType(KeyEvent.VK_O);
                break;
            case 'p':
                doType(KeyEvent.VK_P);
                break;
            case 'q':
                doType(KeyEvent.VK_Q);
                break;
            case 'r':
                doType(KeyEvent.VK_R);
                break;
            case 's':
                doType(KeyEvent.VK_S);
                break;
            case 't':
                doType(KeyEvent.VK_T);
                break;
            case 'u':
                doType(KeyEvent.VK_U);
                break;
            case 'v':
                doType(KeyEvent.VK_V);
                break;
            case 'w':
                doType(KeyEvent.VK_W);
                break;
            case 'x':
                doType(KeyEvent.VK_X);
                break;
            case 'y':
                doType(KeyEvent.VK_Y);
                break;
            case 'z':
                doType(KeyEvent.VK_Z);
                break;
            case 'A':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_A);
                break;
            case 'B':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_B);
                break;
            case 'C':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_C);
                break;
            case 'D':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_D);
                break;
            case 'E':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_E);
                break;
            case 'F':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_F);
                break;
            case 'G':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_G);
                break;
            case 'H':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_H);
                break;
            case 'I':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_I);
                break;
            case 'J':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_J);
                break;
            case 'K':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_K);
                break;
            case 'L':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_L);
                break;
            case 'M':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_M);
                break;
            case 'N':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_N);
                break;
            case 'O':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_O);
                break;
            case 'P':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_P);
                break;
            case 'Q':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Q);
                break;
            case 'R':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_R);
                break;
            case 'S':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_S);
                break;
            case 'T':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_T);
                break;
            case 'U':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_U);
                break;
            case 'V':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_V);
                break;
            case 'W':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_W);
                break;
            case 'X':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_X);
                break;
            case 'Y':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Y);
                break;
            case 'Z':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Z);
                break;
            case '`':
                doType(KeyEvent.VK_BACK_QUOTE);
                break;
            case '0':
                doType(KeyEvent.VK_0);
                break;
            case '1':
                doType(KeyEvent.VK_1);
                break;
            case '2':
                doType(KeyEvent.VK_2);
                break;
            case '3':
                doType(KeyEvent.VK_3);
                break;
            case '4':
                doType(KeyEvent.VK_4);
                break;
            case '5':
                doType(KeyEvent.VK_5);
                break;
            case '6':
                doType(KeyEvent.VK_6);
                break;
            case '7':
                doType(KeyEvent.VK_7);
                break;
            case '8':
                doType(KeyEvent.VK_8);
                break;
            case '9':
                doType(KeyEvent.VK_9);
                break;
            case '-':
                doType(KeyEvent.VK_MINUS);
                break;
            case '=':
                doType(KeyEvent.VK_EQUALS);
                break;
            case '~':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_QUOTE);
                break;
            case '!':
                doType(KeyEvent.VK_EXCLAMATION_MARK);
                break;
            case '@':
                doType(KeyEvent.VK_AT);
                break;
            case '#':
                doType(KeyEvent.VK_NUMBER_SIGN);
                break;
            case '$':
                doType(KeyEvent.VK_DOLLAR);
                break;
            case '%':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_5);
                break;
            case '^':
                doType(KeyEvent.VK_CIRCUMFLEX);
                break;
            case '&':
                doType(KeyEvent.VK_AMPERSAND);
                break;
            case '*':
                doType(KeyEvent.VK_ASTERISK);
                break;
            case '(':
                doType(KeyEvent.VK_LEFT_PARENTHESIS);
                break;
            case ')':
                doType(KeyEvent.VK_RIGHT_PARENTHESIS);
                break;
            case '_':
                doType(KeyEvent.VK_UNDERSCORE);
                break;
            case '+':
                doType(KeyEvent.VK_PLUS);
                break;
            case '\t':
                doType(KeyEvent.VK_TAB);
                break;
            case '\n':
                doType(KeyEvent.VK_ENTER);
                break;
            case '[':
                doType(KeyEvent.VK_OPEN_BRACKET);
                break;
            case ']':
                doType(KeyEvent.VK_CLOSE_BRACKET);
                break;
            case '\\':
                doType(KeyEvent.VK_BACK_SLASH);
                break;
            case '{':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_OPEN_BRACKET);
                break;
            case '}':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_CLOSE_BRACKET);
                break;
            case '|':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_SLASH);
                break;
            case ';':
                doType(KeyEvent.VK_SEMICOLON);
                break;
            case ':':
                doType(KeyEvent.VK_COLON);
                break;
            case '\'':
                doType(KeyEvent.VK_QUOTE);
                break;
            case '"':
                doType(KeyEvent.VK_QUOTEDBL);
                break;
            case ',':
                doType(KeyEvent.VK_COMMA);
                break;
            case '<':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_COMMA);
                break;
            case '.':
                doType(KeyEvent.VK_PERIOD);
                break;
            case '>':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_PERIOD);
                break;
            case '/':
                doType(KeyEvent.VK_SLASH);
                break;
            case '?':
                doType(KeyEvent.VK_SHIFT, KeyEvent.VK_SLASH);
                break;
            case ' ':
                doType(KeyEvent.VK_SPACE);
                break;
            default:
                throw new IllegalArgumentException("Cannot type character " + character);
        }
    }

    public void doType(int... keyCodes) {
        for (int i = 0; i <= keyCodes.length - 1; i++) {
            robot.keyPress(keyCodes[i]);
        }
        for (int i = keyCodes.length - 1; i >= 0; i--) {
            robot.keyRelease(keyCodes[i]);
        }
    }
}
