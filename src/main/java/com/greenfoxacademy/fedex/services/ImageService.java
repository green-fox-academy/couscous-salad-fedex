package com.greenfoxacademy.fedex.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

  public void save(MultipartFile image) throws IOException {
    InputStream in = image.getInputStream();
    File targetDir = new File(System.getProperty("user.home") + "\\pictures");
    String path = targetDir.getAbsolutePath();
    FileOutputStream fos = new FileOutputStream(path + "\\" + image.getOriginalFilename());
    int ch = 0;
    while ((ch = in.read()) != -1) {
      fos.write(ch);
    }
    fos.flush();
    fos.close();
  }
}
