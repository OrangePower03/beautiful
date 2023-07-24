package com.example.java;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@MapperScan(basePackages = {"com.example.java.mapper"})
@SpringBootApplication
public class JavaApplication {

	public static void main(String[] args) {

		SpringApplication.run(JavaApplication.class, args);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			String code="redis-cli shutdown";
			String url=JavaApplication.class.getResource("").getPath();
			int uri="target/classes/com/example/java/".length();
			String serverUri="redis-5.0.14.1";
			ProcessBuilder builder=new ProcessBuilder("cmd","/c",code);
        	builder.directory(new File(url.substring(1,url.length()-uri)+serverUri));
			try {
				Process process = builder.start();

				// 获取CMD错误流
				InputStream errorStream = process.getErrorStream();
				BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));


				// 读取错误信息
				String line;
				if ((line = errorReader.readLine()) != null) {
					System.out.println("redis服务器关闭失败，请手动到redis目录下打开cmd，并且输入"+code);
					System.out.println(line);
				}
				else
					System.out.println("redis服务器已关闭");
			}
			catch (IOException e) {
				e.printStackTrace();
				System.out.println("运行错误");
			}
        }));
	}

}


