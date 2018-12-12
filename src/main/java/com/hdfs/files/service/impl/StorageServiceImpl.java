package com.hdfs.files.service.impl;

import com.hdfs.files.exception.StorageException;
import com.hdfs.files.exception.StorageFileNotFoundException;
import com.hdfs.files.properties.StorageProperties;
import com.hdfs.files.service.StorageService;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService {

  private final Path rootLocation;
  private final Path downloadLocation;
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public StorageServiceImpl(StorageProperties properties) {
    this.rootLocation = Paths.get(properties.getLocation());
    this.downloadLocation = Paths.get(properties.getDownloadLocation());
  }

  @Override
  public String store(MultipartFile file) {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file " + filename);
      }
      if (filename.contains("..")) {
        // This is a security check
        throw new StorageException(
            "Cannot store file with relative path outside current directory "
                + filename);
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, this.rootLocation.resolve(filename),
            StandardCopyOption.REPLACE_EXISTING);
      }

      return this.rootLocation+"/"+file.getOriginalFilename();
    }
    catch (IOException e) {
      throw new StorageException("Failed to store file " + filename, e);
    }
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      logger.info("loadAll {}", rootLocation);
      return Files.list(this.rootLocation).collect(Collectors.toList()).stream();

    }
    catch (IOException e) {
      throw new StorageException("Failed to read stored files", e);
    }

  }

  @Override
  public Path load(String filename) {
    return rootLocation.resolve(filename);
  }

  @Override
  public Resource loadAsResource(String filename) {
    try {
      Path file = load(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      }
      else {
        throw new StorageFileNotFoundException(
            "Could not read file: " + filename);

      }
    }
    catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + filename, e);
    }
  }

  @Override
  public Resource loadFromPath(String path) {
    try {
      Path file  =Paths.get(path);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      }
      else {
        throw new StorageFileNotFoundException(
            "Could not read file: " + path);

      }
    }
    catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + path, e);
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(rootLocation.toFile());
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(rootLocation);
      logger.info("Init succeed! {}",rootLocation);
      Files.createDirectories(downloadLocation);
      logger.info("Init succeed! {}",downloadLocation);
    }
    catch (IOException e) {
      logger.error("Init failed! ",e);
      throw new StorageException("Could not initialize storage", e);
    }
  }
}
