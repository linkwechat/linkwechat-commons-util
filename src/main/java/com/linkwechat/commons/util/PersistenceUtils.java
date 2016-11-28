package com.linkwechat.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 对象持久化工具类
 * 
 * @author linkwechat linkwechat@foxmail.com
 * @version 1.0
 */
public class PersistenceUtils {

    private static final Log logger = LogFactory.getLog(PersistenceUtils.class);

    /**
     * 读取持久化对象
     * 
     * @param filePath
     *            文件路径
     * @return Object
     */
    public static Object readObject(String filePath) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object obj = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            obj = ois.readObject();
        } catch (Throwable t) {
            logger.error("Read Object error!", t);
            return null;
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Throwable t) {
                    logger.error(t.getMessage(), t);
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Throwable t) {
                    logger.error(t.getMessage(), t);
                }
            }
        }
        return obj;
    }

    /**
     * 写入持久化对象
     * 
     * @param obj
     *            持久化对象
     * @param filePath
     *            文件路径
     * @return boolean
     */
    public static boolean writeObject(Object obj, String filePath) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    if (!file.getParentFile().mkdirs()) {
                        return false;
                    }
                }
                if (!file.createNewFile()) {
                    return false;
                }
            }
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
        } catch (Throwable t) {
            logger.error("Write Object error!", t);
            return false;
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (Throwable t) {
                    logger.error(t.getMessage(), t);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Throwable t) {
                    logger.error(t.getMessage(), t);
                }
            }
        }
        return true;
    }
}
