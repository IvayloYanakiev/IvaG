package com.emag;

import com.ivag.config.SpringJdbcConfig;
import com.ivag.exception.UserException;
import com.ivag.service.UserService;
import com.ivag.config.IvagApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {IvagApplication.class, SpringJdbcConfig.class})
public class UserServiceTests {


    @Autowired
    UserService userService;

    @Test
    public void findUserByEmail() throws UserException {
        userService.findUserByEmail("ivayloyanakiev95@gmail.com");
    }

    @Test
    public void findInvalidUserByEmail() throws UserException {
        userService.findUserByEmail(null);
    }
}
