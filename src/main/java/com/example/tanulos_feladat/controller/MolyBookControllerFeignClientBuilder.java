package com.example.tanulos_feladat.controller;

import com.example.tanulos_feladat.client.BookClient;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.Getter;

@Getter
public class MolyBookControllerFeignClientBuilder {

    String key = "3a32342d336e489ddfb82fde4a971f43";
    String text = "vándorünnep";
    private BookClient bookClient = createClient(BookClient.class, "https://moly.hu/api/books.json?q="+text+"&key="+key);

    private static <T> T createClient(Class<T> type, String uri) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(type))
                .logLevel(Logger.Level.FULL)
                .target(type, uri);
    }
}
