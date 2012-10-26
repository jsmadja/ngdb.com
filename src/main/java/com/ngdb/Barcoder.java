package com.ngdb;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.UPCAWriter;
import com.ngdb.services.Cacher;
import org.apache.commons.codec.binary.Base64;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Barcoder {

    @Inject
    private Cacher cacher;

    public String toBarcodeBase64Image(String ean) {
        if(cacher.hasBarcodeOf(ean)) {
            return cacher.getBarcodeOf(ean);
        }
        String barcode = getBarcode(ean);
        cacher.setBarcodeOf(ean, barcode);
        return barcode;
    }

    private String getBarcode(String ean) {
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

    private String toEAN(String ean) throws WriterException, IOException {
        BitMatrix bitMatrix = new EAN13Writer().encode(ean, BarcodeFormat.EAN_13, 80, 37);
        return toImage(bitMatrix);
    }

    private String toUPC(String upc) throws WriterException, IOException {
        BitMatrix bitMatrix = new UPCAWriter().encode(upc, BarcodeFormat.UPC_A, 80, 37);
        return toImage(bitMatrix);
    }

    private String toImage(BitMatrix bitMatrix) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "gif", stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeBase64String(byteArray);
    }

    public void invalidate(String ean) {
        cacher.invalidateBarcode(ean);
    }
}
