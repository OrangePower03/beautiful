package com.example.java;

import com.example.java.mapper.InitialDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InitDatabaseRunner implements ApplicationRunner {
    @Autowired
    private InitialDataMapper init;

	@Transactional
    @Override
    public void run(ApplicationArguments args) {
		// 打开redis服务器
		String url=this.getClass().getResource("").getPath();
		int uri="target/classes/com/example/java/".length();
		String serverUri="redis-5.0.14.1/redis-server.exe";
		String redisUrl=url.substring(1,url.length()-uri)+serverUri;
//		System.out.println(redisUrl);
		final Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(redisUrl);
			System.out.println("redis服务器启动成功");
		} catch (final Exception e) {
			System.out.println("redis服务器启动失败，你自己重新开启吧");
		}

		if(init.findCount()<=0){
			int index=0;
			init.addRole(index++,"普通用户");
			init.addRole(index++,"管理员");
			init.addRole(index,"超级管理员");
		}

		if(init.findKindNumber()<=0){
			String[] title=new String[]{
			"配音演员","艺术","演员","导演","剪辑","制片","录音","视效","编剧"
			};
			for (String s : title) {
				init.addTitle(s);
			}
			String[] kind=new String[]{
				"番剧","漫画","小说"
			};
			for(String s:kind){
				init.addKind(s);
			}
            System.out.println("数据库初始化完毕");
		}
    }
}
