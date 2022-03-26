package com.jimisun.simplemybatis.io;

import java.io.InputStream;

/**
 * 资源工具类
 *
 * @author jimisun
 * @create 2022-03-26 10:20 AM
 **/
public class Resources {

    // step 1 根据传入的文件地址，将文件信息已字节输入流的形式存储到内存中
    public static InputStream getResourceAsStream(String path) {
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }

}
