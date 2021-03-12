package com.luban.testcache;

import cn.hutool.core.io.FileUtil;

import java.io.File;

/**
 * @Description
 * @Author wangwei
 * @Date2020/12/28 2:48 下午
 * @Version V1.0
 **/
public class FileCourseTest {
    public static void main(String[] args) {
       FileUtil.loopFiles("/Users/wangwei/IdeaProjects/documents/course/Java 业务开发常见错误 100 例",
                (file)->{
                    return !file.getPath().contains(".pdf");
                }).forEach((file)->{
           file.delete();
       });
    }
}
