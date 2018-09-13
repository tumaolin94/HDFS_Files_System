package com.hdfs.files.service.impl;

import com.hdfs.files.properties.FSConfigration;
import com.hdfs.files.service.HdfsService;
import java.io.IOException;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HdfsServiceImpl implements HdfsService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());
  /**
   * judge if path exists
   *
   * @param path
   * @return true or false
   * @throws IOException
   */
  public boolean exits(String path) throws IOException {
    FileSystem fs = FileSystem.get(FSConfigration.getConfiguration());
    return fs.exists(new Path(path));
  }
}
