package com.mcgwinds.middleware.cache.cachekey.compress;

import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by mcg on 2017/12/16.
 */
public class DefaultCompressor implements Compressor {

    private static final int BUFFER=1024;

    private static final CompressorStreamFactory FACTORY=new CompressorStreamFactory();

    private String name;

    public DefaultCompressor(String name) {
        this.name=name;
    }

    public byte[] compress(ByteArrayInputStream byteArrayInputStream) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        CompressorOutputStream compressorOutputStream=FACTORY.createCompressorOutputStream(name,byteArrayOutputStream);
        int len;
        byte buf[]=new byte[BUFFER];
        while((len=byteArrayInputStream.read(buf, 0, BUFFER)) != -1) {
            compressorOutputStream.write(buf, 0, len);
        }
        compressorOutputStream.flush();
        compressorOutputStream.close();
        byte[] output=byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        byteArrayInputStream.close();
        return output;
    }

    public byte[] decompress(ByteArrayInputStream byteArrayInputStream) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        CompressorInputStream compressorInputStream=FACTORY.createCompressorInputStream(name, byteArrayInputStream);
        int len;
        byte buf[]=new byte[BUFFER];
        while((len=compressorInputStream.read(buf, 0, BUFFER)) != -1) {
            byteArrayOutputStream.write(buf, 0, len);
        }
        byteArrayOutputStream.close();

        byte[] output=byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        byteArrayInputStream.close();
        return output;
    }
}
