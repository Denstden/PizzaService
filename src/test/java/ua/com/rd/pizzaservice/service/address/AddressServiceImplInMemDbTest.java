package ua.com.rd.pizzaservice.service.address;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.service.ServiceTestUtils;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/inMemDbRepositoryContext.xml",
        "classpath:/appContext.xml",
})
public class AddressServiceImplInMemDbTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private AddressService addressService;

    private ServiceTestUtils serviceTestUtils;

    @Before
    public void setUp() {
        serviceTestUtils = new ServiceTestUtils(jdbcTemplate);
    }

    @Test
    public void getAddressByIdTest() {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");

        Address newAddress = addressService.getAddressById(address.getId());
        Assert.assertEquals(address, newAddress);
    }

    @Test
    public void saveAddressTest() {
        Address address = new Address("Country", "City", "Street", "Building");
        addressService.saveAddress(address);
        Assert.assertNotNull(address.getId());
    }

    @Test
    public void deleteAddressTest() {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        addressService.deleteAddress(address);

        int countOfRecords = getCountOfAddressesById(address.getId());
        Assert.assertEquals(0, countOfRecords);
    }

    private int getCountOfAddressesById(Long id) {
        final String sql = "SELECT COUNT(*) FROM addresses WHERE address_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
    }

    @Test
    public void findAllTest() {
        Address address1 = serviceTestUtils.createAddress(1L, "Country1", "City1", "Street1", "Building1");
        Address address2 = serviceTestUtils.createAddress(2L, "Country2", "City2", "Street2", "Building2");
        Address address3 = serviceTestUtils.createAddress(3L, "Country3", "City3", "Street3", "Building3");
        Address address4 = serviceTestUtils.createAddress(4L, "Country4", "City4", "Street4", "Building4");


        Set<Address> addresses = addressService.findAll();
        Assert.assertEquals(4, addresses.size());

        Set<Address> expected = new HashSet<>();
        expected.add(address1);
        expected.add(address2);
        expected.add(address3);
        expected.add(address4);
        Assert.assertEquals(expected, addresses);
    }
}
