package com.hdfs.files.properties;

import org.apache.hadoop.conf.ConfigRedactor;
import org.apache.hadoop.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
/**
 * author: Maolin Tu
 * 2019.1.9
 * */
@Component
@PropertySource("classpath:hadoop.properties")
@ConfigurationProperties(prefix = "fs")
public class FSConfigration {



  private static String defaultFS;

  private static Logger logger = LoggerFactory.getLogger(FSConfigration.class);

  private static Configuration conf;

  public static Configuration getConfiguration(){

    if(conf == null) {
      synchronized (FSConfigration.class){
        if(conf == null){
          conf = new Configuration(true);
          // 指定hadoop fs的地址
          logger.info("getConfiguration: {}", defaultFS);
          conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
          conf.set("fs.defaultFS", defaultFS);
          logger.info("create new conf: {}", conf);
        }
      }
    }

      return conf;
  }
  @Value("${fs.defaultFS}")
  public void setDefaultFS(String defaultFS) {

    FSConfigration.defaultFS = defaultFS;
    logger.info("init {}", FSConfigration.defaultFS);
  }

  public static String getDefaultFS() {
    return defaultFS;
  }

}
