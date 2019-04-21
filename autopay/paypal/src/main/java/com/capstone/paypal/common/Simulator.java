package com.capstone.paypal.common;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class Simulator {

    private final Robot robot;
    private final Clipboard clipboard;

    private int imgW;
    private int imgPixSize;
    private DataBuffer imgDB;

    public Simulator() throws AWTException {
        this.robot = new Robot();
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public class PixelColor {
        public int id;
        int xFrom;
        int yFrom;
        int xTo;
        int yTo;
        int redMin;
        int redMax;
        int greenMin;
        int greenMax;
        int blueMin;
        int blueMax;

        PixelColor(int id, int xFrom, int yFrom, int xTo, int yTo, int color, int delta) {
            this.id = id;
            this.xFrom = xFrom;
            this.yFrom = yFrom;
            this.xTo = xTo;
            this.yTo = yTo;
            int red = (color >> 16) & 0xFF;
            int green = (color >> 8) & 0xFF;
            int blue = color & 0xFF;
            this.redMin = Math.max(0, red - delta);
            this.redMax = Math.min(255, red + delta);
            this.greenMin = Math.max(0, green - delta);
            this.greenMax = Math.min(255, green + delta);
            this.blueMin = Math.max(0, blue - delta);
            this.blueMax = Math.min(255, blue + delta);
        }
    }

    public Dimension getScreenSize(){
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public PixelColor createPixelColor(int id, int xFrom, int yFrom, int xTo, int yTo, int color, int delta) {
        return new PixelColor(id, xFrom, yFrom, xTo, yTo, color, delta);
    }

    public int waitForMultiPixel(PixelColor... pixelColors) {
        int dxMax;
        int dyMax;
        int red;
        int green;
        int blue;
        int x;
        int y;

        while (true) {
            for (PixelColor p : pixelColors) {
                dxMax = p.xTo - p.xFrom;
                dyMax = p.yTo - p.yFrom;
                for (int dx = 0; dx <= dxMax; dx++) {
                    for (int dy = 0; dy <= dyMax; dy++) {
                        x = p.xFrom + dx;
                        y = p.yFrom + dy;

                        Color color = robot.getPixelColor(x, y);
                        System.out.println("color " + p.id + " - " + x + " - " + y + " - " + color.toString());
                        red = color.getRed();
                        green = color.getGreen();
                        blue = color.getBlue();
                        if (p.redMin <= red && red <= p.redMax && p.greenMin <= green && green <= p.greenMax && p.blueMin <= blue && blue <= p.blueMax) {
                            System.out.println("found at" + x + " - " + y);
                            return p.id;
                        }
                    }
                }
            }
            System.out.println("end loop");
            delay(50);
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

    public void waitForPixel(int x, int y, int rgb) {
        while (true) {
            if (rgb == robot.getPixelColor(x, y).getRGB()) {
                return;
            } else {
                delay(50);
            }
        }
    }

    public void clickInBox(int x0, int y0, int dx, int dy) {
        Random ran = new Random();
        int x = x0 + ran.nextInt(dx + 1);
        int y = y0 + ran.nextInt(dy + 1);

        robot.mouseMove(x, y);
        delay(10);
        robot.mouseMove(x, y);
        delay(10);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        delay(10);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void moveMouseFast(int x, int y) {
        Point p = MouseInfo.getPointerInfo().getLocation();
        double dis = Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
        double jump = dis / 10;
        double dx = (x - p.x) / jump;
        double dy = (y - p.y) / jump;
        double xt = p.x;
        double yt = p.y;
        for (int i = (int) jump; i > 0; i--) {
            delay(10);
            xt += dx;
            yt += dy;
            robot.mouseMove((int) Math.round(xt), (int) Math.round(yt));
        }
        robot.mouseMove(x, y);
    }

    public void moveAndClickInBox(int x0, int y0, int dx, int dy) {
        Random ran = new Random();
        int x = x0 + ran.nextInt(dx + 1);
        int y = y0 + ran.nextInt(dy + 1);
        moveMouseFast(x, y);
        delayRandomMedium();
        robot.mousePress(InputEvent.BUTTON1_MASK);
        delayRandomMedium();
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public Point waitForColor(int xFrom, int yFrom, int xTo, int yTo, int rgb, long maxWait) {
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
                delay(50);
            }
        }
    }

    public void copyParseString(String str) {
        StringSelection stringSelection = new StringSelection(str);
        clipboard.setContents(stringSelection, null);
        delayRandomShort();
        doType(KeyEvent.VK_CONTROL, KeyEvent.VK_V);

    }

    private int getRGBFromImg(int x, int y) {
        int rgb = 0;
        int pos = imgPixSize * (y * imgW + x);
        try {
            if (imgPixSize == 4) {
                rgb = 0xFF000000 | (imgDB.getElem(pos + 3) << 16) | (imgDB.getElem(pos + 2) << 8) | imgDB.getElem(pos + 1);
            }
            if (imgPixSize == 3) {
                rgb = 0xFF000000 | (imgDB.getElem(pos + 2) << 16) | (imgDB.getElem(pos + 1) << 8) | imgDB.getElem(pos);
            }
        } catch (Exception e) {
            System.out.println("Error x " + x + " y " + y + " p " + pos);
            throw e;
        }
        return rgb;
    }

    private void setPixel(int[][] difRGB, int[][] difX, int[][] difY, int[] count, int dif, int rgb, int x, int y) {
        int pos = count[dif];
        difRGB[dif][pos] = rgb;
        difX[dif][pos] = x;
        difY[dif][pos] = y;
        count[dif] = count[dif] + 1;
    }

    private int isDiffUp(int rgb, int x, int y) {
        return rgb != getRGBFromImg(x, y - 1) ? 1 : 0;
    }

    private int isDiffDown(int rgb, int x, int y) {
        return rgb != getRGBFromImg(x, y + 1) ? 1 : 0;
    }

    private int isDiffLeft(int rgb, int x, int y) {
        return rgb != getRGBFromImg(x - 1, y) ? 1 : 0;
    }

    private int isDiffRight(int rgb, int x, int y) {
        return rgb != getRGBFromImg(x + 1, y) ? 1 : 0;
    }

    public Point waitForImage(int xFrom, int yFrom, int xTo, int yTo, String path, long maxWait) throws IOException {
        long time0 = new Date().getTime();
        long timeMax = time0 + maxWait;
        int sWth = xTo - xFrom + 1;
        BufferedImage image = ImageIO.read(new File(path));
        imgDB = image.getData().getDataBuffer();
        imgW = image.getWidth();
        int imgH = image.getHeight();
        int imgSize = imgDB.getSize();
        imgPixSize = imgSize / imgH / imgW;

        int[][] difRGB = new int[5][imgW * imgH];
        int[][] difX = new int[5][imgW * imgH];
        int[][] difY = new int[5][imgW * imgH];
        int[] count = new int[5];

        int xImgMax = imgW - 1;
        int yImgMax = imgH - 1;
        int xImgMid = xImgMax / 2;
        int yImgMid = yImgMax / 2;

        int xStop = xTo - xFrom + 1 - imgW;
        int yStop = yTo - yFrom + 1 - imgH;

        int dif;
        int rgb;
        // Top Left
        rgb = getRGBFromImg(0, 0);
        dif = isDiffDown(rgb, 0, 0) + isDiffRight(rgb, 0, 0);
        setPixel(difRGB, difX, difY, count, dif, rgb, 0, 0);

        // Bot Left
        rgb = getRGBFromImg(0, yImgMax);
        dif = isDiffUp(rgb, 0, yImgMax) + isDiffRight(rgb, 0, yImgMax);
        setPixel(difRGB, difX, difY, count, dif, rgb, 0, yImgMax);

        // Top Right
        rgb = getRGBFromImg(xImgMax, 0);
        dif = isDiffDown(rgb, xImgMax, 0) + isDiffLeft(rgb, xImgMax, 0);
        setPixel(difRGB, difX, difY, count, dif, rgb, xImgMax, 0);

        // Bot Right
        rgb = getRGBFromImg(xImgMax, yImgMax);
        dif = isDiffUp(rgb, xImgMax, yImgMax) + isDiffLeft(rgb, xImgMax, yImgMax);
        setPixel(difRGB, difX, difY, count, dif, rgb, xImgMax, yImgMax);

        for (int x = 1; x < xImgMax; x++) {
            // Top Side
            rgb = getRGBFromImg(x, 0);
            dif = isDiffDown(rgb, x, 0) + isDiffLeft(rgb, x, 0) + isDiffRight(rgb, x, 0);
            setPixel(difRGB, difX, difY, count, dif, rgb, x, 0);

            // Bot Side
            rgb = getRGBFromImg(x, yImgMax);
            dif = isDiffUp(rgb, x, yImgMax) + isDiffLeft(rgb, x, yImgMax) + isDiffRight(rgb, x, yImgMax);
            setPixel(difRGB, difX, difY, count, dif, rgb, x, yImgMax);
        }

        for (int y = 1; y < yImgMax; y++) {
            // Left Side
            rgb = getRGBFromImg(0, y);
            dif = isDiffUp(rgb, 0, y) + isDiffDown(rgb, 0, y) + isDiffRight(rgb, 0, y);
            setPixel(difRGB, difX, difY, count, dif, rgb, 0, y);

            // Right Side
            rgb = getRGBFromImg(xImgMax, y);
            dif = isDiffUp(rgb, xImgMax, y) + isDiffDown(rgb, xImgMax, y) + isDiffLeft(rgb, xImgMax, y);
            setPixel(difRGB, difX, difY, count, dif, rgb, xImgMax, y);
        }

        for (int y = 1; y < yImgMax; y++) {
            for (int x = 1; x < xImgMax; x++) {
                rgb = getRGBFromImg(x, y);
                dif = isDiffLeft(rgb, x, y) + isDiffRight(rgb, x, y) + isDiffUp(rgb, x, y) + isDiffDown(rgb, x, y);
                setPixel(difRGB, difX, difY, count, dif, rgb, x, y);
            }
        }

        int st = 0;
        for (int i = 4; i > 0 && st == 0; i--) {
            if (count[i] > 0) {
                st = i;
            }
        }
        long time1 = new Date().getTime();
        long time3 = 0;
        long cost;
        long mod = 0;
        long wait;
        boolean match;
        Rectangle captureSize = new Rectangle(xFrom, yFrom, xTo - xFrom + 1, yTo - yFrom + 1);
        BufferedImage screen;
        DataBuffer sdb;
        while (true) {
            screen = robot.createScreenCapture(captureSize);
            sdb = screen.getData().getDataBuffer();

            for (int y = 0; y <= yStop; y++) {
                for (int x = 0; x <= xStop; x++) {
                    match = true;
                    for (int i = st; i >= 0 && match; i--) {
                        for (int j = count[i] - 1; j >= 0 && match; j--) {
                            if (difRGB[i][j] != sdb.getElem((y + difY[i][j]) * sWth + x + difX[i][j])) {
                                match = false;
                            }
                        }
                    }
                    if (match) {
                        long time4 = new Date().getTime();
                        System.out.println("x " + (x + xFrom + xImgMid) + ", y " + (y + yFrom + yImgMid) + ", time total " + (time4 - time0) + " time loop " + (time4 - (time3 - mod + 100)));
                        return new Point(x + xFrom + xImgMid, y + yFrom + yImgMid);
                    }
                }
            }
            time3 = new Date().getTime();
            cost = time3 - time0;
            mod = cost % 100L;
            wait = 100L - mod;
            if ((time3 + wait) >= timeMax) {
                System.out.println("Not found, time total " + (time3 - time0) + "  time loop  " + (time3 - time1));
                return null;
            }
            if (mod != 0) {
                delay(wait);
            }
        }
    }

    public class ImgData {
        int id;
        int[][] difRGB;
        int[][] difX;
        int[][] difY;
        int[] count;
        int xMid;
        int yMid;
        int xStop;
        int yStop;
        int highestTier;
    }

    public int waitForImages(int xFrom, int yFrom, int xTo, int yTo, int[] ids, String[] paths, long maxWait) throws IOException {
        long time0 = new Date().getTime();
        long timeMax = time0 + maxWait;
        int sWth = xTo - xFrom + 1;
        ImgData[] imgList = new ImgData[paths.length];
        for (int i = 0; i < paths.length; i++) {
            ImgData imgData = new ImgData();
            BufferedImage image = ImageIO.read(new File(paths[i]));
            imgDB = image.getData().getDataBuffer();
            imgW = image.getWidth();
            int imgH = image.getHeight();
            int imgSize = imgDB.getSize();
            imgPixSize = imgSize / imgH / imgW;

            int[][] difRGB = new int[5][imgW * imgH];
            int[][] difX = new int[5][imgW * imgH];
            int[][] difY = new int[5][imgW * imgH];
            int[] count = new int[5];

            int xImgMax = imgW - 1;
            int yImgMax = imgH - 1;

            int dif;
            int rgb;
            // Top Left
            rgb = getRGBFromImg(0, 0);
            dif = isDiffDown(rgb, 0, 0) + isDiffRight(rgb, 0, 0);
            setPixel(difRGB, difX, difY, count, dif, rgb, 0, 0);

            // Bot Left
            rgb = getRGBFromImg(0, yImgMax);
            dif = isDiffUp(rgb, 0, yImgMax) + isDiffRight(rgb, 0, yImgMax);
            setPixel(difRGB, difX, difY, count, dif, rgb, 0, yImgMax);

            // Top Righ
            rgb = getRGBFromImg(xImgMax, 0);
            dif = isDiffDown(rgb, xImgMax, 0) + isDiffLeft(rgb, xImgMax, 0);
            setPixel(difRGB, difX, difY, count, dif, rgb, xImgMax, 0);

            // Bot Right
            rgb = getRGBFromImg(xImgMax, yImgMax);
            dif = isDiffUp(rgb, xImgMax, yImgMax) + isDiffLeft(rgb, xImgMax, yImgMax);
            setPixel(difRGB, difX, difY, count, dif, rgb, xImgMax, yImgMax);

            for (int x = 1; x < xImgMax; x++) {
                // Bot Side
                rgb = getRGBFromImg(x, yImgMax);
                dif = isDiffUp(rgb, x, yImgMax) + isDiffLeft(rgb, x, yImgMax) + isDiffRight(rgb, x, yImgMax);
                setPixel(difRGB, difX, difY, count, dif, rgb, x, yImgMax);
                // Top Side
                rgb = getRGBFromImg(x, 0);
                dif = isDiffDown(rgb, x, 0) + isDiffLeft(rgb, x, 0) + isDiffRight(rgb, x, 0);
                setPixel(difRGB, difX, difY, count, dif, rgb, x, 0);
            }

            for (int y = 1; y < yImgMax; y++) {
                // Right Side
                rgb = getRGBFromImg(xImgMax, y);
                dif = isDiffUp(rgb, xImgMax, y) + isDiffDown(rgb, xImgMax, y) + isDiffLeft(rgb, xImgMax, y);
                setPixel(difRGB, difX, difY, count, dif, rgb, xImgMax, y);
                // Left Side
                rgb = getRGBFromImg(0, y);
                dif = isDiffUp(rgb, 0, y) + isDiffDown(rgb, 0, y) + isDiffRight(rgb, 0, y);
                setPixel(difRGB, difX, difY, count, dif, rgb, 0, y);
            }

            for (int y = 1; y < yImgMax; y++) {
                for (int x = 1; x < xImgMax; x++) {
                    rgb = getRGBFromImg(x, y);
                    dif = isDiffUp(rgb, x, y) + isDiffDown(rgb, x, y) + isDiffLeft(rgb, x, y) + isDiffRight(rgb, x, y);
                    setPixel(difRGB, difX, difY, count, dif, rgb, x, y);
                }
            }

            int st = 0;
            for (int j = 4; j > 0 && st == 0; j--) {
                if (count[j] > 0) {
                    st = j;
                }
            }

            imgData.id = ids[i];
            imgData.difRGB = difRGB;
            imgData.difX = difX;
            imgData.difY = difY;
            imgData.count = count;
            imgData.xMid = xImgMax / 2;
            imgData.yMid = yImgMax / 2;
            imgData.xStop = xTo - xFrom + 1 - imgW;
            imgData.yStop = yTo - yFrom + 1 - imgH;
            imgData.highestTier = st;
            imgList[i] = imgData;
        }


        long time1 = new Date().getTime();
        long time3;
        long cost;
        long mod;
        long wait;
        boolean match;
        Rectangle captureSize = new Rectangle(xFrom, yFrom, xTo - xFrom + 1, yTo - yFrom + 1);
        BufferedImage screen;
        DataBuffer sdb;
        while (true) {
            screen = robot.createScreenCapture(captureSize);
            sdb = screen.getData().getDataBuffer();

            for (ImgData imgData : imgList) {
                for (int y = 0; y <= imgData.yStop; y++) {
                    for (int x = 0; x <= imgData.xStop; x++) {
                        match = true;
                        for (int i = imgData.highestTier; i >= 0 && match; i--) {
                            for (int j = imgData.count[i] - 1; j >= 0 && match; j--) {
                                if (imgData.difRGB[i][j] != sdb.getElem((y + imgData.difY[i][j]) * sWth + x + imgData.difX[i][j])) {
                                    match = false;
                                }
                            }
                        }
                        if (match) {
//                            long time4 = new Date().getTime();
//                            System.out.println("x " + (x + xFrom + imgData.xMid) + ", y " + (y + yFrom + imgData.yMid) + ", time total " + (time4 - time0) + " time loop " + (time4 - (time3 - mod + 100)));
                            return imgData.id;
                        }
                    }
                }
            }

            time3 = new Date().getTime();
            cost = time3 - time0;
            mod = cost % 100L;
            wait = 100L - mod;
            if ((time3 + wait) >= timeMax) {
                System.out.println("Not found, time total " + (time3 - time0) + "  time loop  " + (time3 - time1));
                return -1;
            }
            if (mod != 0) {
                delay(wait);
            }
        }
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
