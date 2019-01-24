package com.ivag.dao;

import com.ivag.exception.AddressException;

import com.ivag.config.ConstantsErrorMessages;
import com.ivag.config.ConstantsSQL;
import com.ivag.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;

import org.apache.log4j.Logger;

@Repository
public class AddressDaoImpl implements AddressDao {


    private static final Logger logger = Logger.getLogger(AddressDaoImpl.class);


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    public Long addAddress(Long userId, Address address) {

        TransactionTemplate tx = new TransactionTemplate(transactionManager);
        String insertIntoUserAddresses = ConstantsSQL.INSERT_INTO_USER_ADDRESSES;

        HashMap<String, Object> addressParam = new HashMap<>();
        addressParam.put("userId", userId);
        GeneratedKeyHolder holder = new GeneratedKeyHolder();


        tx.execute(new TransactionCallbackWithoutResult() {

            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    jdbcTemplate.getJdbcOperations().update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement statement = con.prepareStatement(ConstantsSQL.INSERT_INTO_ADDRESSES, Statement.RETURN_GENERATED_KEYS);
                            statement.setString(1, address.getReceiverName());
                            statement.setString(2, address.getReceiverPhone());
                            statement.setString(3, address.getCity());
                            statement.setString(4, address.getStreet());
                            statement.setInt(5, address.getFloor());
                            return statement;
                        }
                    }, holder);
                    long addressId = holder.getKey().longValue();
                    addressParam.put("addressId",addressId);
                    jdbcTemplate.update(insertIntoUserAddresses, addressParam);
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    status.setRollbackOnly();
                }
            }
        });
        long addressId = holder.getKey().longValue();
        return addressId;
    }

    @Override
    public Collection<Address> getAllAddresses(Long userId) throws AddressException {

        String getAllAddresses = ConstantsSQL.GET_ALL_ADDRESSES;
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        LinkedHashSet<Address> addresses = null;
        try {
            addresses = jdbcTemplate.query(getAllAddresses, params, new ResultSetExtractor<LinkedHashSet<Address>>() {

                @Override
                public LinkedHashSet<Address> extractData(ResultSet rs) throws SQLException {
                    LinkedHashSet<Address> myAddresses = new LinkedHashSet<>();

                    while (rs.next()) {
                        Long addressId = rs.getLong("address_id");
                        String receiverName = rs.getString("receiver_name");
                        String receiverPhone = rs.getString("receiver_phone");
                        String city = rs.getString("city");
                        String street = rs.getString("street");
                        Integer floor = rs.getInt("floor");

                        try {
                            Address address = new Address(addressId, receiverName, receiverPhone, city, street, floor);
                            myAddresses.add(address);
                        } catch (AddressException e) {
                            logger.error(e.getMessage());
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return myAddresses;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AddressException(e.getMessage(), e);
        }
        return addresses;
    }

    @Override
    public void updateAddress(Address address) throws AddressException {
        String updateAddress = ConstantsSQL.UPDATE_ADDRESS;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", address.getId());
        params.put("receiver_name", address.getReceiverName());
        params.put("receiver_phone", address.getReceiverPhone());
        params.put("city", address.getCity());
        params.put("street", address.getStreet());
        params.put("floor", address.getFloor());
        try {
            jdbcTemplate.update(updateAddress, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AddressException(ConstantsErrorMessages.ERROR_UPDATING_ADDRESS, e);
        }
    }


    @Override
    public Address getAddress(Long addressId) throws AddressException {
        String getAddress = ConstantsSQL.GET_ADDRESS_BY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        Address addressById = null;
        try {
            addressById = jdbcTemplate.query(getAddress, params, new ResultSetExtractor<Address>() {

                @Override
                public Address extractData(ResultSet rs) throws SQLException {
                    Address address = new Address();
                    if (rs.next()) {
                        try {
                            address.setId(rs.getLong("id"));
                            address.setReceiverName(rs.getString("receiver_name"));
                            address.setReceiverPhone(rs.getString("receiver_phone"));
                            address.setStreet(rs.getString("street"));
                            address.setFloor(rs.getInt("floor"));
                            address.setCity(rs.getString("city"));
                        } catch (AddressException e) {
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return address;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AddressException(e.getMessage(), e);
        }
        return addressById;
    }

    @Override
    public void deleteAddress(Long addressId) throws AddressException {
        String deleteAddress = ConstantsSQL.DELETE_ADDRESS;
        HashMap<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        try {
            jdbcTemplate.update(deleteAddress, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AddressException(ConstantsErrorMessages.ERROR_DELETING_ADDRESS, e);
        }
    }

}
