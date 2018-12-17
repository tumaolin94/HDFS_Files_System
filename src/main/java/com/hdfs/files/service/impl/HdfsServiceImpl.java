package com.hdfs.files.service.impl;

import com.hdfs.files.properties.FSConfigration;
import com.hdfs.files.properties.StorageProperties;
import com.hdfs.files.service.HdfsService;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HdfsServiceImpl implements HdfsService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());
  private final Path downloadLocation;

  @Autowired
  public HdfsServiceImpl(StorageProperties properties) {
    this.downloadLocation = new Path(properties.getDownloadLocation());
  }
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

  @Override
  public List<String> uploadFile(String oriPath, String desPath) throws IOException {
    FileSystem fs = FileSystem.get(FSConfigration.getConfiguration());

    fs.copyFromLocalFile(new Path(oriPath), new Path(desPath));
    logger.info("Upload to", FSConfigration.getConfiguration().get("fs.default.name"));

    return listAll(desPath);
  }

  @Override
  public String downloadFile(String strPath) throws IOException {
    FileSystem fs = FileSystem.get(FSConfigration.getConfiguration());
    Path oriPath = new Path(strPath);
    String tmpDir = downloadLocation.toString()+"/"+oriPath.getName()+String.valueOf(System.currentTimeMillis())+"/"+oriPath.getName();
    logger.info("oriPath {} dst location {}", oriPath, tmpDir);
    fs.copyToLocalFile(oriPath, new Path(tmpDir));
    fs.close();
    return tmpDir;
  }

  @Override
  public String mkdir(String dir) throws IOException{
    FileSystem fs = FileSystem.get(FSConfigration.getConfiguration());
    Path path = new Path(dir);
    if (fs.exists(path)) {
      logger.info("Dir " + dir + " already exists");
      return "Dir " + dir + " already exists";
    }

    fs.mkdirs(path);

    fs.close();

    return path + " build up successfully!";
  }
}
