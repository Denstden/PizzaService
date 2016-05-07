package ua.com.rd.pizzaservice.service.address;

import ua.com.rd.pizzaservice.domain.address.Address;

import java.util.Set;

public interface AddressService {
    Address getAddressById(Long id);

    Address saveAddress(Address address);

    void updateAddress(Address address);

    Address deleteAddress(Address address);

    Set<Address> findAll();
}
