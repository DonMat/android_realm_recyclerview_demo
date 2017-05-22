package pl.utkala.realmdemo.models;

import java.util.Locale;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Person extends RealmObject {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String AGE = "age";

    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String name;
    private String surname;
    private int age;
    private Address address;

    public Person() {
    }

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAddresText(){
        String out = "";

        if(address != null && address.getCountry() != null)
            out += address.getCountry();
        if(address != null && address.getCity() != null)
            out += " " + address.getCity();

        return out;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s %s, %d \n%s", name, surname, age, getAddresText());
    }
}
