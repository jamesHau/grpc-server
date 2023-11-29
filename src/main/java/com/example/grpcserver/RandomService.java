package com.example.grpcserver;

import org.springframework.stereotype.Service;

@Service
public class RandomService {

  public String magic(String input) {
    return "Did you just say " + input;
  }
}
