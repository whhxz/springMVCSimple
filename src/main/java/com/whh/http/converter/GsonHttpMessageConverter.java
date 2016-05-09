package com.whh.http.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.whh.controller.request.vo.DemoParams;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;

import java.io.*;

/**
 *
 * Created by xuzhuo on 2016/4/25.
 */
public class GsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        try {
            String params = ((ServletServerHttpRequest) inputMessage).getServletRequest().getParameter("params");
//            return gson.fromJson(convertStreamToString(inputMessage.getBody()), clazz);
            return gson.fromJson(params, clazz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw new HttpMessageNotReadableException("Could not read JSON: " + e.getMessage());
        }
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        outputMessage.getBody().write(gson.toJson(o).getBytes());
    }

    /**
     * 流转String
     * @param inputStream 流
     * @return str
     */
    private String convertStreamToString(InputStream inputStream){
        if (inputStream != null){
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1){
                    writer.write(buffer, 0, n);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        String str = "{\"name\":\"whh\"}";
        DemoParams demoParams = new Gson().fromJson(str, DemoParams.class);
        System.out.println(demoParams);
    }
}
