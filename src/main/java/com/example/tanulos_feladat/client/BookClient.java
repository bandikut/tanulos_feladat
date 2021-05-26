package com.example.tanulos_feladat.client;

import com.example.tanulos_feladat.entity.MolyBook;
import com.example.tanulos_feladat.entity.MolyBooks;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface BookClient {
    @RequestLine("GET /{title}")
    MolyBook findByTitle(@Param("title") String isbn);

    @RequestLine("GET")
//    List<MolyBook> findAll();
    Object findAll();

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    void create(MolyBook molyBook);

}
