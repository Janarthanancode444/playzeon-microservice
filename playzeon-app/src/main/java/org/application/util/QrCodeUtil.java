package org.application.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QrCodeUtil {

    public static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageConfig imageConfig = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, imageConfig);
        BufferedImage rgbQrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = rgbQrImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.drawImage(qrImage, 0, 0, null);
        g.dispose();
        BufferedImage logoImage = ImageIO.read(new File("F://Internship//frontend//logotexticon.png"));
        int logoScaledWidth = qrImage.getWidth() / 5;
        int logoScaledHeight = qrImage.getHeight() / 5;
        int finalImageHeight = (qrImage.getHeight() - logoScaledHeight) / 2;
        int finalImageWidth = (qrImage.getWidth() - logoScaledWidth) / 2;
        BufferedImage finalImage = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) finalImage.getGraphics();
        graphics.setColor(Color.RED);
        graphics.fillRect(0, 0, finalImage.getWidth(), finalImage.getHeight());
        graphics.drawImage(qrImage, 0, 0, null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        graphics.drawImage(logoImage, finalImageHeight, finalImageWidth, logoScaledWidth, logoScaledHeight, Color.white, null);
        graphics.dispose();
        ImageIO.write(finalImage, "PNG", new File(String.valueOf(path)));
    }

}

