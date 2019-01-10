# HDFS_Files_System


Author: Maolin Tu

2019.1.9

API:

``` 
   /**
   * getIfPathExists
   * return if the path is existed in HDFS
   * @param path: the target path need to be visited
   * @return ResponseEntity<String>
   * */
   
   /**
   * getList
   * 
   * return all files/directories of target path
   * @param path: the target path need to be visited
   * @return ResponseEntity<List<String>>
   * */
   
  /**
   * uploadFile
   *
   * @param file: upload file
   * @param strPath: the target path need to be visited
   * @return ResponseEntity<List<String>>
   * */
      
   /**
   * downloadFile
   *
   * download files/directories of target path
   * @param path: the target path need to be visited
   * @return ResponseEntity<Resource>
   * */
   
   /**
   * downloadDirectory
   *
   * download directories of target path
   * @param path: the target path need to be visited
   * @return ResponseEntity<Resource>
   * */

  /**
   * makeDir
   *
   * create new directory for target path
   * @param path: the target path need to be visited
   * @return ResponseEntity<String>
   * */
```

