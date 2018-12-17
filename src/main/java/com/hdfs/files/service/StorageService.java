package com.hdfs.files.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;


public interface StorageService {
  void init();

  String store(MultipartFile file);

  Stream<Path> loadAll();

  Stream<Path> loadLocalFiles(String strPath);

  Path load(String filename);

  Resource loadAsResource(String filename);

  Resource loadFromPath(String path);

  void deleteAll();

  String zipFile(String filename);
}
