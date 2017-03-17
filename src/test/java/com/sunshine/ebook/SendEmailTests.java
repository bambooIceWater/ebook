package com.sunshine.ebook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendEmailTests {

    @Test
    public void sendEmail() {
        String address = "913771070@qq.com";
        String content = "这是一个测试邮件，呵呵呵呵";
//        SendEmail.send(address, content);
    }

}
