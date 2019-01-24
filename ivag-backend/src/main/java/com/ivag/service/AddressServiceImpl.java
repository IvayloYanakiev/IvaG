package com.ivag.service;

import com.ivag.exception.AddressException;
import com.ivag.dao.AddressDao;
import com.ivag.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressDao addressDao;

    @Override
    public Address addAddress(Long userId, Address address) throws AddressException {
        Long addressId = addressDao.addAddress(userId, address);
        address.setId(addressId);
        return address;
    }

    @Override
    public Collection<Address> getAllAddresses(Long userId) throws AddressException {
        return addressDao.getAllAddresses(userId);
    }

    @Override
    public void updateAddress(Address address) throws AddressException {
        addressDao.updateAddress(address);
    }

    @Override
    public Address getAddress(Long addressId) throws AddressException {
        return addressDao.getAddress(addressId);
    }

    @Override
    public void deleteAddress(Long addressId) throws AddressException {
        addressDao.deleteAddress(addressId);
    }
}
