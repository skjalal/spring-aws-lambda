package com.example.controller;

import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  @GetMapping(value = {"/user"})
  public Map<String, Object> user() {
    return Map.of("id", 1, "name", "Test", "isWorking", true);
  }
}
