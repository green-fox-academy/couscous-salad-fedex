package com.greenfoxacademy.fedex.controllers;

import com.greenfoxacademy.fedex.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class TestController {

  @Autowired
  ImageService imageService;

  @GetMapping("/uploadTestPage")
  public String getUploadPage () {
        return "uploadTest";
  }

  @PostMapping("/uploadTest")
  public String uploadImage (@RequestParam("image")MultipartFile image) throws IOException {
    imageService.save(image);
    return "redirect:/uploadTestPage";
  }

}
