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
    public void run(ApplicationArguments args) throws Exception {
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
