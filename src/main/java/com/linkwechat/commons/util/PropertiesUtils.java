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

    private Properties props = null;

    public PropertiesUtils() {
        props = new Properties();
    }

    public PropertiesUtils(String path) throws Exception {
        props = loadProperty(path);
    }

    public PropertiesUtils(InputStream inputStream) throws Exception {
        props = new Properties();
        props.load(inputStream);
    }

    public Object setProperty(String key, Object value) {
        return props.put(key, value);
    }

    public void putAll(Map<? extends Object, ? extends Object> t) {
        props.putAll(t);
    }

    public boolean containsKey(String key) {
        return props.containsKey(key);
    }

    public String getString(String key) throws Exception {
        try {
            return props.getProperty(key);
        } catch (Exception e) {
            throw new Exception("key:" + key);
        }
    }

    public int getInt(String key) throws Exception {
        try {
            return Integer.parseInt(props.getProperty(key));
        } catch (Exception e) {
            throw new Exception("key:" + key);
        }
    }

    public double getDouble(String key) throws Exception {
        try {
            return Double.parseDouble(props.getProperty(key));
        } catch (Exception e) {
            throw new Exception("key:" + key);
        }
    }

    public long getLong(String key) throws Exception {
        try {
            return Long.parseLong(props.getProperty(key));
        } catch (Exception e) {
            throw new Exception("key:" + key);
        }
    }

    public float getFloat(String key) throws Exception {
        try {
            return Float.parseFloat(props.getProperty(key));
        } catch (Exception e) {
            throw new Exception("key:" + key);
        }
    }

    public boolean getBoolean(String key) throws Exception {
        try {
            return Boolean.parseBoolean(props.getProperty(key));
        } catch (Exception e) {
            throw new Exception("key:" + key);
        }
    }

    public Set<Object> getAllKey() {
        return props.keySet();
    }

    public Collection<Object> getAllValue() {
        return props.values();
    }

    public Map<String, Object> getAllKeyValue() {
        Map<String, Object> mapAll = new HashMap<String, Object>();
        Set<Object> keys = getAllKey();

        Iterator<Object> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            mapAll.put(key, props.get(key));
        }
        return mapAll;
    }

    public static Properties loadProperty(String filePath) throws Exception {
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
