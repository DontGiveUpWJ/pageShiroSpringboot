package com.sitech.paas.service;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonArray;
import com.sitech.paas.entity.Http2in;
import com.sitech.paas.entity.RequestParam;

/**
 * Created by 72707 on 2018/9/11.
 */
public interface IHttp2inServer {

    void saveHttp2ins(String path) throws IOException;

    List<Http2in> getHttp2inAll();

    Http2in getByHttp2inId(String id);

    List<Http2in> getHttp2inDataByRequestParam(RequestParam param);

    JsonArray getRelative(String http2inId, String http2inChild, String wires);

}
