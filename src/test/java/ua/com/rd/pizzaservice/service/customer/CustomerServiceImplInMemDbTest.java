package ua.com.rd.pizzaservice.service.customer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.service.ServiceTestUtils;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/inMemDbRepositoryContext.xml",
        "classpath:/appContext.xml",
})
public class CustomerServiceImplInMemDbTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private CustomerService customerService;

    private ServiceTestUtils serviceTestUtils;

    @Before
    public void setUp() {
        serviceTestUtils = new ServiceTestUtils(jdbcTemplate);
    }

    @Test
    public void getCustomerByIdTest() {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Customer customer = serviceTestUtils.createCustomer(1L, "Petya", address);

        Customer newCustomer = customerService.getCustomerById(customer.getId());
        Assert.assertEquals(customer, newCustomer);
    }

    @Test
    public void saveCustomerTest() {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Customer customer = new Customer();
        customer.setName("Petr");
        customer.setAddresses(new HashSet<Address>() {{
            add(address);
        }});
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getId());
    }

    @Test
    public void updateCustomerTest() {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Customer customer = serviceTestUtils.createCustomer(1L, "Petya", address);

        Address newAddress = serviceTestUtils.createAddress(2L, "newCountry", "newCity", "newStreet", "newBuilding");
        customer.setName("Ivan");
        customer.addAddress(newAddress);
        customerService.updateCustomer(customer);

        Customer updatedCustomer = getById(customer.getId());
        Assert.assertEquals(customer, updatedCustomer);
    }

    private Customer getById(Long id) {
        return jdbcTemplate.query("SELECT c.customer_name, a.address_id, a.country, a.city, a.street, a.building " +
                "FROM customers c, addresses a, customers_addresses ca " +
                "WHERE ca.customer_customer_id = c.customer_id AND ca.addresses_address_id = a.address_id AND " +
                "c.customer_id = ?;", new Object[]{id}, resultSet -> {
            Customer customer = new Customer();
            customer.setId(id);
            Set<Address> addresses = new HashSet<>();
            while (resultSet.next()) {
                customer.setName(resultSet.getString("customer_name"));
                Address address = new Address();
                address.setId(resultSet.getLong("address_id"));
                address.setCountry(resultSet.getString("country"));
                address.setCity(resultSet.getString("city"));
                address.setStreet(resultSet.getString("street"));
                address.setBuilding(resultSet.getString("building"));
                addresses.add(address);
            }
            customer.setAddresses(addresses);
            return customer;
        });
    }

    @Test
    public void deleteCustomerTest() {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Customer customer = serviceTestUtils.createCustomer(1L, "Petya", address);

        customerService.deleteCustomer(customer);
        int countOfRecords = getCountOfCustomersById(customer.getId());
        Assert.assertEquals(0, countOfRecords);
    }

    private int getCountOfCustomersById(Long id) {
        final String sql = "SELECT COUNT(*) FROM customers WHERE customer_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
    }

    @Test
    public void findAllTest() {
        Address address1 = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Address address2 = serviceTestUtils.createAddress(2L, "newCountry", "newCity", "newStreet", "newBuilding");
        Customer customer1 = serviceTestUtils.createCustomer(1L, "Petya", address1);
        Customer customer2 = serviceTestUtils.createCustomer(2L, "Ivan", address1);
        Customer customer3 = serviceTestUtils.createCustomer(3L, "Kolya", address2);

        Set<Customer> customers = customerService.findAll();
        Assert.assertEquals(3, customers.size());

        Set<Customer> expected = new HashSet<>();
        expected.add(customer1);
        expected.add(customer2);
        expected.add(customer3);
        Assert.assertEquals(expected, customers);
    }
}
