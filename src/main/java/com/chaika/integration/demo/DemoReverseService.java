package com.chaika.integration.demo;

/**
 * Created by echaika on 24.03.2019
 */
public class DemoReverseService {

    public void reverse(String message) {
        System.out.println(new StringBuilder(message).reverse().toString());
    }
}
