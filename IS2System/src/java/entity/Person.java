/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aleksandar
 */
@Entity
@Table(name = "persons")
@XmlRootElement
public class Person extends User {
    public static final int SEX_FEMALE = 0;
    public static final int SEX_MALE = 1;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "surname")
    private String surname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sex")
    private int sex;
    
    public Person(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
    
    public static Person convertUser(User u){
        Person p = new Person();
        p.setAddress(u.getAddress());
        p.setCity(u.getCity());
        p.setCountry(u.getCountry());
        p.setEmail(u.getEmail());
        p.setPassword(u.getPassword());
        p.setPhone(u.getPhone());
        p.setUsername(u.getUsername());
        return p;
    }
    
    public static List<Person> convertUsers(List<User> list){
        List<Person> retList = new ArrayList<>();
        for(User u : list){
            retList.add(convertUser(u));
        }
        return retList;
    }
    
    public String toStirng(){
        return super.toString()+", name="+name+", surname="+surname+", sex="+(sex==Person.SEX_MALE ? "male" : "female");
    }
}
