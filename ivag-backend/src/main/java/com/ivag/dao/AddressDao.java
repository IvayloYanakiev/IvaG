package com.ivag.dao;

import com.ivag.exception.AddressException;
import com.ivag.model.Address;

import java.util.Collection;

public interface AddressDao {
    Long addAddress(Long userId, Address address) throws AddressException;

    Collection getAllAddresses(Long userId) throws AddressException;

    void updateAddress(Address address) throws AddressException;

    Address getAddress(Long addressId) throws AddressException;

    void deleteAddress(Long addressId) throws AddressException;

}
