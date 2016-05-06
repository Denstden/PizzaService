package ua.com.rd.pizzaservice.domain.address;

import javax.persistence.*;


@Entity
@Table(name = "ADDRESSES")
public class Address {
    @Id
    //@SequenceGenerator(name="ADDRESS_SEQ", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY/*, generator="ADDRESS_SEQ"*/)
    @Column(name = "ADDRESS_ID")
    private Long id;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STREET")
    private String street;

    @Column(name = "BUILDING")
    private String building;

    @Version
    private Integer version;

    public Address(){
    }

    public Address(String country, String city, String street, String building) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return country + ", " + city + ", " + street + ", " + building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;
        if (id!=null && address.getId()!=null && id.equals(address.getId())){
            return true;
        }

        if (!country.equals(address.country)) return false;
        if (!city.equals(address.city)) return false;
        if (!street.equals(address.street)) return false;
        return building.equals(address.building);

    }

    @Override
    public int hashCode() {
        int result = country.hashCode();
        if (id!=null) {
            result = 31 * result + id.hashCode();
        }
        result = 31 * result + city.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + building.hashCode();
        return result;
    }
}