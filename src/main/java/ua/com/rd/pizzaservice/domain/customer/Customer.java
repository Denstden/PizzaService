package ua.com.rd.pizzaservice.domain.customer;

import ua.com.rd.pizzaservice.domain.address.Address;

public class Customer {
    private Long id;
    private String name;
    private Address address;

    public Customer(Long id, String name, Address address) {
        this.address = address;
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{"
                + "id=" + id
                + ", name='" + name
                + ", address=" + address
                + '}';
    }
}
