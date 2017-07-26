package digitalquantuminc.inscribesecuresms.UserInterface;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorOutputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream;
import org.apache.commons.compress.compressors.pack200.Pack200CompressorOutputStream;
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorOutputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Bagus Hanindhito on 22/07/2017.
 */

public class CompressionDecompression {

    public static byte[] BZip2Compress (byte[] content) {

        BZip2CompressorOutputStream outStream;
        ByteArrayOutputStream bytearrayoutStream = null;

        try
        {
            bytearrayoutStream = new ByteArrayOutputStream();
            outStream = new BZip2CompressorOutputStream(bytearrayoutStream);
            outStream.write(content);
            outStream.close();
        }
        catch (IOException e) {

        }
        return bytearrayoutStream.toByteArray();
    }

    public static byte[] BZip2Decompress (byte[] compressedcontent) {

        BZip2CompressorInputStream inStream;
        ByteArrayInputStream bytearrayinStream;
        ByteArrayOutputStream bytearrayoutStream = null;
        try
        {
            bytearrayoutStream =  new ByteArrayOutputStream();
            bytearrayinStream = new ByteArrayInputStream(compressedcontent);
            inStream = new BZip2CompressorInputStream(bytearrayinStream);
            IOUtils.copy(inStream,bytearrayoutStream);

        }
        catch (IOException e) {

        }
        return bytearrayoutStream.toByteArray();
    }

    public static byte[] GZipCompress (byte[] content) {

        GzipCompressorOutputStream outStream;
        ByteArrayOutputStream bytearrayoutStream = null;

        try
        {
            bytearrayoutStream = new ByteArrayOutputStream();
            outStream = new GzipCompressorOutputStream(bytearrayoutStream);
            outStream.write(content);
            outStream.close();
        }
        catch (IOException e) {

        }
        return bytearrayoutStream.toByteArray();
    }

    public static byte[] LZMACompress (byte[] content) {


        LZMACompressorOutputStream outStream;
        ByteArrayOutputStream bytearrayoutStream = null;

        try
        {
            bytearrayoutStream = new ByteArrayOutputStream();
            outStream = new LZMACompressorOutputStream(bytearrayoutStream);
            outStream.write(content);
            outStream.close();
        }
        catch (IOException e) {

        }
        return bytearrayoutStream.toByteArray();
    }

    public static byte[] FramedLZ4Compress (byte[] content) {


        FramedLZ4CompressorOutputStream outStream;
        ByteArrayOutputStream bytearrayoutStream = null;

        try
        {
            bytearrayoutStream = new ByteArrayOutputStream();
            outStream = new FramedLZ4CompressorOutputStream(bytearrayoutStream);
            outStream.write(content);
            outStream.close();
        }
        catch (IOException e) {

        }
        return bytearrayoutStream.toByteArray();
    }

    public static byte[] BlockLZ4Compress (byte[] content) {


        BlockLZ4CompressorOutputStream outStream;
        ByteArrayOutputStream bytearrayoutStream = null;

        try
        {
            bytearrayoutStream = new ByteArrayOutputStream();
            outStream = new BlockLZ4CompressorOutputStream(bytearrayoutStream);
            outStream.write(content);
            outStream.close();
        }
        catch (IOException e) {

        }
        return bytearrayoutStream.toByteArray();
    }

    public static byte[] BlockLZ4Decompress (byte[] compressedcontent) {

        BlockLZ4CompressorInputStream inStream;
        ByteArrayInputStream bytearrayinStream;
        ByteArrayOutputStream bytearrayoutStream = null;
        try
        {
            bytearrayoutStream =  new ByteArrayOutputStream();
            bytearrayinStream = new ByteArrayInputStream(compressedcontent);
            inStream = new BlockLZ4CompressorInputStream(bytearrayinStream);
            IOUtils.copy(inStream,bytearrayoutStream);
            return bytearrayoutStream.toByteArray();

        }
        catch (Exception  e) {
            return compressedcontent;
        }
    }

    public static byte[] SnappyCompress (byte[] content) {


        FramedSnappyCompressorOutputStream outStream;
        ByteArrayOutputStream bytearrayoutStream = null;

        try
        {
            bytearrayoutStream = new ByteArrayOutputStream();
            outStream = new FramedSnappyCompressorOutputStream(bytearrayoutStream);
            outStream.write(content);
            outStream.close();
        }
        catch (IOException e) {

        }
        return bytearrayoutStream.toByteArray();
    }

    public static byte[] DeflateCompress (byte[] content) {


        DeflateCompressorOutputStream outStream;
        ByteArrayOutputStream bytearrayoutStream = null;

        try
        {
            bytearrayoutStream = new ByteArrayOutputStream();
            outStream = new DeflateCompressorOutputStream(bytearrayoutStream);
            outStream.write(content);
            outStream.close();
        }
        catch (IOException e) {

        }
        return bytearrayoutStream.toByteArray();
    }

    public static byte[] DeflateDecompress (byte[] compressedcontent) {

        DeflateCompressorInputStream inStream;
        ByteArrayInputStream bytearrayinStream;
        ByteArrayOutputStream bytearrayoutStream = null;
        try
        {
            bytearrayoutStream =  new ByteArrayOutputStream();
            bytearrayinStream = new ByteArrayInputStream(compressedcontent);
            inStream = new DeflateCompressorInputStream(bytearrayinStream);
            IOUtils.copy(inStream,bytearrayoutStream);
            return bytearrayoutStream.toByteArray();

        }
        catch (Exception e) {
            return compressedcontent;
        }

    }

    public static byte[] XZCompress (byte[] content) {


        XZCompressorOutputStream outStream;
        ByteArrayOutputStream bytearrayoutStream = null;

        try
        {
            bytearrayoutStream = new ByteArrayOutputStream();
            outStream = new XZCompressorOutputStream(bytearrayoutStream);
            outStream.write(content);
            outStream.close();
        }
        catch (IOException e) {

        }
        return bytearrayoutStream.toByteArray();
    }

}
