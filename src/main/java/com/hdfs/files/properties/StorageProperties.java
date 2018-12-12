package com.hdfs.files.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

  /**
   * Folder location for storing files
   */
  private String location = "/tmp/upload-dir";


  private String downloadLocation = "/tmp/download-dir";

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDownloadLocation() {
    return downloadLocation;
  }

  public void setDownloadLocation(String downloadLocation) {
    this.downloadLocation = downloadLocation;
  }
}
