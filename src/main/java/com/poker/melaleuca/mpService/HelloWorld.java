package com.poker.melaleuca.mpService;

import static spark.Spark.*;

/**
 * Created by pokerwen on 2015/10/20.
 */
public class HelloWorld {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}
