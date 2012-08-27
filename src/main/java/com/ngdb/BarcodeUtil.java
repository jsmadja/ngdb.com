package com.ngdb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.UPCAWriter;

public class BarcodeUtil {

    public static String toBarcodeBase64Image(String ean) {
        try {
            return toEAN(ean);
        } catch (Exception e) {
            try {
                return toUPC(ean);
            } catch (Exception e1) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static String toEAN(String ean) throws WriterException, IOException {
        BitMatrix bitMatrix = new EAN13Writer().encode(ean, BarcodeFormat.EAN_13, 80, 37);
        return toImage(bitMatrix);
    }

    private static String toUPC(String upc) throws WriterException, IOException {
        BitMatrix bitMatrix = new UPCAWriter().encode(upc, BarcodeFormat.UPC_A, 80, 37);
        return toImage(bitMatrix);
    }

    private static String toImage(BitMatrix bitMatrix) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "gif", stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeBase64String(byteArray);
    }

}
