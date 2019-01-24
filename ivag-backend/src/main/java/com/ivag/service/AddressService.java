package com.ivag.service;

import com.ivag.exception.AddressException;
import com.ivag.model.Address;

import java.util.Collection;

public interface AddressService  {

    Address addAddress(Long userId, Address address) throws AddressException;

    Collection<Address> getAllAddresses(Long id) throws AddressException;

    void updateAddress(Address address) throws AddressException;

    Address getAddress(Long addressId) throws AddressException;

    void deleteAddress(Long addressId) throws AddressException;
}
