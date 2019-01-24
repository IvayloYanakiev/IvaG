package com.emag;
import static org.assertj.core.api.Assertions.*;

import com.ivag.config.IvagApplication;
import com.ivag.config.SpringJdbcConfig;
import com.ivag.exception.AddressException;
import com.ivag.model.Address;
import com.ivag.service.AddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {IvagApplication.class, SpringJdbcConfig.class})
public class AddressServiceTests {


    @Autowired
    private AddressService addressService;

    @Test
    public void addAddressToUserAccount() throws AddressException {
        Address checkAdress = new Address("Ivo","0895622831","sofiq","slaveikov",5);
        Address address =  addressService.addAddress(33L, checkAdress);
        assertThat(checkAdress).isEqualTo(address);
    }

    @Test
    public void addInvalidAddress() throws AddressException {
        Address checkAdress = new Address(null,"0895622831","sofiq","slaveikov",5);
        Address address =  addressService.addAddress(33L, checkAdress);
        assertThat(checkAdress).isEqualTo(address);
    }
}
