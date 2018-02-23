package com.windhc.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * @author windhc
 */
public class FileUtil {

    /**
     * 1kb
     */
    private final static int KB_1 = 1024;

    /**
     * 获得文件的CRC32校验和
     * @param file 要进行校验的文件
     * @return String
     * @throws Exception
     */
    public static String getFileCRCCode(File file) throws Exception {
        FileInputStream is = new FileInputStream(file);
        CRC32 crc32 = new CRC32();
        CheckedInputStream cis = new CheckedInputStream(is, crc32);
        byte[] buffer = null;
        buffer = new byte[KB_1];
        while (cis.read(buffer) != -1) {
        }
        is.close();
        return Long.toHexString(crc32.getValue());
    }

    /**
     * 获得字串的CRC32校验和
     *
     * @param string
     *            要进行校验的字串
     * @return
     * @throws Exception
     */
    public static String getStringCRCCode(String string) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(string.getBytes());
        CRC32 crc32 = new CRC32();
        CheckedInputStream checkedinputstream = new CheckedInputStream(inputStream, crc32);
        byte[] buffer = null;
        buffer = new byte[KB_1];
        while (checkedinputstream.read(buffer) != -1) {
        }
        inputStream.close();
        return Long.toHexString(crc32.getValue());
    }

    /**
     * 写字串到文件中，若文件不存在，则建立新文件
     * 文件编码为UTF-8
     * @param filePath 需要写的文件的路径
     * @param data 需要写入的字串
     * @return true:写入成功 false:写入失败
     */
    public static boolean writeToFile(String filePath, String data) {
        return writeToFile(filePath, data, "UTF-8");
    }

    /**
     * 写字串到文件中，若文件不存在，则建立新文件
     *
     * @param filePath 需要写的文件的路径
     * @param data 需要写入的字串
     * @param encoding 文件编码，默认为UTF-8
     * @return true:写入成功 false:写入失败
     */
    public static boolean writeToFile(String filePath, String data, String encoding) {
        try {
            if (encoding == null || "".equals(encoding)) {
                encoding = "UTF-8";
            }
            org.apache.commons.io.FileUtils.writeStringToFile(new File(filePath), data, encoding);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 判断文件和目录是否已存在
     * @param filePath 文件和目录完整路径
     * @return tru:存在 false：不存在
     */
    public static boolean exists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 判断特定的路径是否为文件
     * @param filePath 文件完整的路径
     * @return 若是文件，则返回true，否则返回false
     */
    public static boolean isFile(String filePath) {
        File file = new File(filePath);
        return file.isFile();
    }

    /**
     * 判断特定的路径是否为目录
     *
     * @param filePath 文件完整的路径
     * @return 若是目录，则返回true，否则返回false
     */
    public static boolean isDirectory(String filePath) {
        File file = new File(filePath);
        return file.isDirectory();
    }

    /**
     * 更改文件的名称，若不在同一个目录下,则系统会移动文件
     * @param srcFile
     *            源文件路径名称
     * @param destFile
     *            目的文件路径名称
     * @return
     */
    public static boolean renameTo(String srcFile, String destFile) {
        File file = new File(srcFile);
        return file.renameTo(new File(destFile));
    }
}
