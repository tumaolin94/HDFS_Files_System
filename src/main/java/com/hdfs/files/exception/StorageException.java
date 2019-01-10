package com.hdfs.files.exception;
/**
 * author: Maolin Tu
 * 2019.1.9
 * */
public class StorageException extends RuntimeException {

  public StorageException(String message) {
    super(message);
  }

  public StorageException(String message, Throwable cause) {
    super(message, cause);
  }
}
