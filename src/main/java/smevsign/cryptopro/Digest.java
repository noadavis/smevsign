package smevsign.cryptopro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Digest {
    final private String className = this.getClass().getSimpleName();
    Log log = LogFactory.getLog(className);

    public byte[] digest(byte[] data, String digestAlgorithm) {
        InputStream content = new ByteArrayInputStream(data);
        try {
            MessageDigest md = MessageDigest.getInstance(digestAlgorithm, "JCP");
            byte[] buff = new byte[1024];
            int readCount;
            content.reset();
            while ((readCount = content.read(buff)) > 0) {
                md.update(buff, 0, readCount);
            }
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException | NoSuchProviderException e) {
            log.error("", e);
        }
        return null;
    }

    public byte[] fileDigest(File file, String digestAlgorithm) {
        try {
            InputStream content = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance(digestAlgorithm, "JCP");
            byte[] buff = new byte[1048576];
            int readCount;
            //content.reset();
            while ((readCount = content.read(buff)) > 0) {
                md.update(buff, 0, readCount);
            }
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException | NoSuchProviderException e) {
            log.error("", e);
        }
        return null;
    }
}
