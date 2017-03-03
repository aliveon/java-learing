package com.huanyu.doc.demo.pom.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * xml工具类
 *
 * @author yangtao
 */
public class XmlUtil {

  private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

  public static final String XML_HEADER =
    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";


  /**
   * 将java对象转换成XML字符串
   */
  public static String Obj2Xml(Object obj, Class[] classes) {
    return Obj2Xml(obj, classes, XML_HEADER);
  }

  /**
   * 将java对象转换成XML字符串
   */
  public static String Obj2Xml(Object obj, Class[] classes, String xmlTop) {
    if (obj == null)
      return null;
    XStream xstream = new XStream(new DomDriver("UTF-8"));
    xstream.registerConverter(new DateConverter(DATE_FORMAT, null, TimeZone.getTimeZone("GMT+8")));
    xstream.processAnnotations(classes);
    StringWriter sw = new StringWriter();
    xstream.marshal(obj, new CompactWriter(sw, new NoNameCoder())); // 压缩方式输出xml格式

    return StringUtils.isBlank(xmlTop) ? sw.toString() : (xmlTop + sw.toString());
  }

  public static boolean toXMLFile(Object obj, Class[] classes, String filePath) throws Exception {
    String strXml = Obj2Xml(obj, classes);
    File file = new File(filePath);

    createDir(file);

    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        logger.error("创建{}文件异常,异常信息:{}", new Object[]{filePath, e});
        return false;
      }
    }
    BufferedWriter writer = null;
    try {
      writer = new BufferedWriter(new FileWriter(filePath));
      writer.write(strXml);
      writer.flush();
    } catch (Exception e) {
      logger.error("写{}文件异常,异常信息:{}", new Object[]{filePath, e});
      return false;
    } finally {
      if (writer != null)
        try {
          writer.close();
        } catch (IOException e) {
          logger.error("写{}文件关闭输出流异常,异常信息:{}", new Object[]{filePath, e});
        }
    }
    return true;
  }

  private static void createDir(File file) throws Exception {
    //判断目标文件所在的目录是否存在
    if (file.getParentFile() != null) {
      if (!file.getParentFile().exists()) {
        if (!file.getParentFile().getParentFile().exists()) {
          createDir(file.getParentFile());
        }
        logger.info("创建文件夹{}", new Object[]{file.getParentFile()});
        if (!file.getParentFile().mkdirs()) {
          logger.info("创建文件夹:{} 失败", new Object[]{file.getParentFile()});
          throw new RuntimeException("创建文件夹:" + file.getParentFile() + " 失败");
        }
      }
    }
  }

}