package com.hdfs.files.controller;

import com.hdfs.files.exception.StorageFileNotFoundException;
import com.hdfs.files.properties.FSConfigration;
import com.hdfs.files.service.HdfsService;
import com.hdfs.files.service.StorageService;
import java.io.IOException;

import java.util.List;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class FileController {

  @Autowired
  private StorageService storageService;

  @Autowired
  private HdfsService hdfsService;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @GetMapping("/")
  public ResponseEntity<List<java.nio.file.Path>> listUploadedFiles() throws IOException {

    List<java.nio.file.Path> collect = storageService.loadAll().collect(Collectors.toList());

    return new ResponseEntity(collect, HttpStatus.OK);
  }


  @GetMapping("/test/hadoop")
  public ResponseEntity<String> testHadoop(@RequestParam("path") String path){
    String str = "at first";
    try {

      str = path + "  is " +hdfsService.exits(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ResponseEntity<String>(str, HttpStatus.OK);
  }






  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

    Resource file = storageService.loadAsResource(filename);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @PostMapping("/")
  public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes) {

    logger.info("handleFileUpload {}", file.getOriginalFilename());
    String path = storageService.store(file);
    redirectAttributes.addFlashAttribute("message",
        "You successfully uploaded " + file.getOriginalFilename() + "!");

    return new ResponseEntity<>(path, HttpStatus.OK);
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }

}