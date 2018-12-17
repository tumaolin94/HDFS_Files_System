package com.hdfs.files.Utils;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

/**
 * @ClassName: ZipUtils
 *
 */
public class ZipUtils {

  /**
   * compress file/files
   * @param srcPathName original file/directory
   * @param finalFile target zip file
   */
  public static void compressExe(String srcPathName, String finalFile) {
    File zipFile = new File(finalFile);
    File srcdir = new File(srcPathName);
    if (!srcdir.exists()){
      throw new RuntimeException(srcPathName + "Does not exist!");
    }

    Project prj = new Project();
    Zip zip = new Zip();
    zip.setProject(prj);
    zip.setDestFile(zipFile);
    FileSet fileSet = new FileSet();
    fileSet.setProject(prj);
    fileSet.setDir(srcdir);
    //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");
    //fileSet.setExcludes(...); //排除哪些文件或文件夹
    zip.addFileset(fileSet);
    zip.execute();
  }
}