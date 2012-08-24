package com.ngdb;

import java.io.ByteArrayOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.UPCAWriter;

public class UPCUtil {

    public static String toUpcBase64Image(String upc) {
        try {
            BitMatrix bitMatrix = new UPCAWriter().encode(upc, BarcodeFormat.UPC_A, 80, 37);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "gif", stream);
            byte[] byteArray = stream.toByteArray();
            return Base64.encodeBase64String(byteArray);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
