package com.hdfs.files.service;

import java.io.IOException;

public interface HdfsService {
  boolean exits(String path) throws IOException;

  ;
}
