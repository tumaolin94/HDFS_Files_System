package com.hdfs.files.service;

import java.io.IOException;
import java.util.List;

public interface HdfsService {
  boolean exits(String path) throws IOException;

  List<String> listAll(String strPath) throws IOException;

  List<String> uploadFile(String oriPath, String desPath) throws IOException;

  String downloadFile(String strPath) throws IOException;

  String mkdir(String path) throws IOException;
}
