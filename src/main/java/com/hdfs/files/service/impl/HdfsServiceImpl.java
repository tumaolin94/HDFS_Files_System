package com.hdfs.files.service.impl;

import com.hdfs.files.properties.FSConfigration;
import com.hdfs.files.service.HdfsService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.hadoop.fs.FileStatus;
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
  @Override
  public boolean exits(String path) throws IOException {
    FileSystem fs = FileSystem.get(FSConfigration.getConfiguration());
    return fs.exists(new Path(path));
  }

  @Override
  public List<String> listAll(String strPath) throws IOException {
    FileSystem fs = FileSystem.get(FSConfigration.getConfiguration());
    Path path = new Path(strPath);
    FileStatus[] fileStatuses = fs.listStatus(path);
    List<String> res = Arrays.stream(fileStatuses).map(fileStatus -> fileStatus.getPath().toString()).collect(
        Collectors.toList());
    return res;
  }
}
