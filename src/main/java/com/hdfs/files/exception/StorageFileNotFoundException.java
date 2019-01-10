package com.hdfs.files.exception;
/**
 * author: Maolin Tu
 * 2019.1.9
 * */
public class StorageFileNotFoundException extends StorageException {

  public StorageFileNotFoundException(String message) {
    super(message);
  }

  public StorageFileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
