package org.application.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QrCodeUtil {

    public static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

//    @Value("${qr.base.url}")
//    private String qrBaseUrl;
//    @Value("${qr.output.path}")
//    private String qrOutputPath;
//
//    public ResponseDTO generateQRCode(final String tableName) {
//        try {
//
//            final String qrText = this.qrBaseUrl;
//            final String path = this.qrOutputPath;
//            final int width = 300;
//            final int height = 300;
//            final QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
//            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//            final BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height, hints);
//            MatrixToImageConfig imageConfig = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);
//            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, imageConfig);
//            BufferedImage rgbQrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            Graphics2D g = rgbQrImage.createGraphics();
//            g.setColor(Color.WHITE);
//            g.fillRect(0, 0, width, height);
//            g.drawImage(qrImage, 0, 0, null);
//            g.dispose();
//            BufferedImage logoImage = ImageIO.read(new File("F://Internship//Microservice-playzeon//common//scn.png"));
//            int logoScaledWidth = qrImage.getWidth() / 5;
//            int logoScaledHeight = qrImage.getHeight() / 5;
//            int finalImageHeight = (qrImage.getHeight() - logoScaledHeight) / 2;
//            int finalImageWidth = (qrImage.getWidth() - logoScaledWidth) / 2;
//            BufferedImage finalImage = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_RGB);
//            Graphics2D graphics = (Graphics2D) finalImage.getGraphics();
//            graphics.setColor(Color.WHITE);
//            graphics.fillRect(0, 0, finalImage.getWidth(), finalImage.getHeight());
//            graphics.drawImage(qrImage, 0, 0, null);
//            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//            graphics.drawImage(logoImage, finalImageHeight, finalImageWidth, logoScaledWidth, logoScaledHeight, Color.white, null);
//            graphics.dispose();
//            ImageIO.write(finalImage, "PNG", new File(path));
//
//            return new ResponseDTO(Constants.CREATED, null, HttpStatus.OK.getReasonPhrase());
//        } catch (Exception e) {
//            throw new RuntimeException(Constants.NOT_FOUND + e);
//        }
//    }


}

