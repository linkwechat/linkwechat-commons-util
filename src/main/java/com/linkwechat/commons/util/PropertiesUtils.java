package com.linkwechat.commons.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Properties文件处理工具类
 * 
 * @author linkwechat linkwechat@foxmail.com
 * @version 1.0
 */
public class PropertiesUtils {

    private static final Log logger = LogFactory.getLog(PropertiesUtils.class);

    private Properties pro = null;

    public PropertiesUtils(String path) throws Exception {
        pro = loadProperty(path);
    }

    public PropertiesUtils(InputStream inputStream) throws Exception {
        pro = new Properties();
        pro.load(inputStream);
    }

    public String getString(String key) throws Exception {
        try {
            return pro.getProperty(key);
        } catch (Exception e) {
            throw new Exception("key:" + key);
        }
    }

    public int getInt(String key) throws Exception {
        try {
            return Integer.parseInt(pro.getProperty(key));
        } catch (Exception e) {
            throw new Exception("key:" + key);
        }
    }

    public double getDouble(String key) throws Exception {
        try {
            return Double.parseDouble(pro.getProperty(key));
        } catch (Exception e) {
            throw new Exception("key:" + key);
        }
    }

    public long getLong(String key) throws Exception {
        try {
            return Long.parseLong(pro.getProperty(key));
        } catch (Exception e) {
            throw new Exception("key:" + key);
        }
    }

    public float getFloat(String key) throws Exception {
        try {
            return Float.parseFloat(pro.getProperty(key));
        } catch (Exception e) {
            throw new Exception("key:" + key);
        }
    }

    public boolean getBoolean(String key) throws Exception {
        try {
            return Boolean.parseBoolean(pro.getProperty(key));
        } catch (Exception e) {
            throw new Exception("key:" + key);
        }
    }

    public Set<Object> getAllKey() {
        return pro.keySet();
    }

    public Collection<Object> getAllValue() {
        return pro.values();
    }

    public Map<String, Object> getAllKeyValue() {
        Map<String, Object> mapAll = new HashMap<String, Object>();
        Set<Object> keys = getAllKey();

        Iterator<Object> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            mapAll.put(key, pro.get(key));
        }
        return mapAll;
    }

    private Properties loadProperty(String filePath) throws Exception {
        FileInputStream fis = null;
        Properties pro = new Properties();
        try {
            fis = new FileInputStream(filePath);
            if (fis != null) {
                pro.load(fis);
            }
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Throwable t) {
                    logger.error(t.getMessage(), t);
                }
            }
        }
        return pro;
    }
}
