package com.mcgwinds.middleware.cache.cachekey.compress;

import java.io.ByteArrayInputStream;

/**
 * Created by mcg on 2017/12/16.
 */
public interface Compressor {

    /**
     *
     * 压缩
     * @param byteArrayInputStream
     * @return
     * @throws Exception
     */

    byte[] compress(ByteArrayInputStream byteArrayInputStream) throws Exception;

    /**
     *
     * 解压
     * @param byteArrayInputStream
     * @return
     * @throws Exception
     */
    byte[] decompress(ByteArrayInputStream byteArrayInputStream) throws Exception;

}
