package ua.com.rd.pizzaservice.repository.address.db;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.repository.address.AddressRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

@Repository
public class PostgreSQLAddressRepository implements AddressRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PostgreSQLAddressRepository() {
    }

    public PostgreSQLAddressRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Address getAddressById(Long id) {
        return entityManager.find(Address.class, id);
    }

    @Override
    public Address saveAddress(Address address) {
        entityManager.persist(address);
        return address;
    }

    @Override
    public void updateAddress(Address address) {
        Address managedAddress = entityManager.find(Address.class, address.getId());
        managedAddress.setBuilding(address.getBuilding());
        managedAddress.setStreet(address.getStreet());
        managedAddress.setCity(address.getCity());
        managedAddress.setCountry(address.getCountry());
        entityManager.flush();
    }

    @Override
    public Address deleteAddress(Address address) {
        Address managedAddress = entityManager.getReference(Address.class, address.getId());
        entityManager.remove(managedAddress);
        entityManager.flush();
        return managedAddress;
    }

    @Override
    public Set<Address> findAll() {
        return new HashSet<>(entityManager.createQuery("" +
                "SELECT a FROM Address a", Address.class).getResultList());
    }
}
