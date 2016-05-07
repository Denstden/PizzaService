package ua.com.rd.pizzaservice.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.repository.address.AddressRepository;

import java.util.Set;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public AddressServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public Address getAddressById(Long id) {
        return addressRepository.getAddressById(id);
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.saveAddress(address);
    }

    @Override
    public void updateAddress(Address address) {
        addressRepository.updateAddress(address);
    }

    @Override
    public Address deleteAddress(Address address) {
        return addressRepository.deleteAddress(address);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Address> findAll() {
        return addressRepository.findAll();
    }
}
