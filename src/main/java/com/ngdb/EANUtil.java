package com.ngdb;

import java.io.ByteArrayOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;

public class EANUtil {

    public static String toEanBase64Image(String ean) {
        try {
            BitMatrix bitMatrix = new EAN13Writer().encode(ean, BarcodeFormat.EAN_13, 80, 37);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "gif", stream);
            byte[] byteArray = stream.toByteArray();
            return Base64.encodeBase64String(byteArray);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
