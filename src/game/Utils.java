package game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {

    static String getImageFromName(String s) {
        return toCamelCase(s).replace("'", "").replace("é", "e");
    }

    static String toCamelCase(String s){
        String[] parts = s.split(" ");
        StringBuilder cc = new StringBuilder();
        for (String part : parts) cc.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
        return cc.toString();
    }

    static Image createColorImage(BufferedImage originalImage, int mask) {
        BufferedImage colorImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());
        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int pixel = originalImage.getRGB(x, y) & mask;
                colorImage.setRGB(x, y, pixel);
            }
        }
        return colorImage;
    }

    static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) return (BufferedImage) img;
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return bimage;
    }
}
