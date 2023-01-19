package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class TextGraphicsConverterImpl implements TextGraphicsConverter {

    private int width;
    private int height;
    private double maxRatio;
    private TextColorSchema schema;


    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        double widthRatio = (double) img.getWidth() / (double) img.getHeight();
        double heightRatio = (double) img.getHeight() / (double) img.getWidth();
        double ratio = Math.max(widthRatio, heightRatio);

        if (ratio > maxRatio) {
            throw new BadImageSizeException(maxRatio, ratio);
        }


        int y = img.getWidth() / width;
        int x = img.getHeight() / height;
        int newWidth = img.getWidth();
        int newHeight = img.getHeight();
        if (img.getHeight() > height && img.getWidth() > width) {
            if (img.getHeight() > img.getWidth()) {
                newHeight = height;
                newWidth = (img.getWidth() / x);
            }
            if (img.getWidth() > img.getHeight()) {
                newWidth = width;
                newHeight = img.getHeight() / y;
            }
        }
        if (img.getHeight() > height && img.getWidth() < width) {
            newHeight = height;
            newWidth = (img.getWidth() / x);
        }
        if (img.getWidth() > width && img.getHeight() < height) {
            newWidth = width;
            newHeight = img.getHeight() / y;
        }


        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();


        StringBuilder result = new StringBuilder();
        for (int h = 0; h < bwImg.getHeight(); h++) {
            for (int w = 0; w < bwImg.getWidth(); w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                result.append(c);
                result.append(c);
            }
            result.append("\n");
        }
        return result.toString();
    }


    @Override
    public void setMaxWidth(int width) {
        if (width < 200 || width > 500) {
            System.out.println("Некорректная ширина, введите значение от 200 до 500");
            throw new IllegalArgumentException();
        }
        this.width = width;
    }


    @Override
    public void setMaxHeight(int height) {
        if (height < 200 || height > 500) {
            System.out.println("Некорректная высота, введите значение от 200 до 500");
            throw new IllegalArgumentException();
        }
        this.height = height;
    }


    @Override
    public void setMaxRatio(double maxRatio) {
        if (maxRatio < 2 || maxRatio > 4) {
            System.out.println("Некорректное соотношение сторон, введите значение от 2 до 4");
            throw new IllegalArgumentException();
        }
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
