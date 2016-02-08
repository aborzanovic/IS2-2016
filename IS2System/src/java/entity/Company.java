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
@Table(name = "companies")
@XmlRootElement
public class Company extends User {
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "pib")
    private String pib;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "document_num")
    private String document_num;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "company_num")
    private String company_num;
    
    public Company(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getDocument_num() {
        return document_num;
    }

    public void setDocument_num(String document_num) {
        this.document_num = document_num;
    }

    public String getCompany_num() {
        return company_num;
    }

    public void setCompany_num(String company_num) {
        this.company_num = company_num;
    }
    
    public static Company convertUser(User u){
        Company c = new Company();
        c.setAddress(u.getAddress());
        c.setCity(u.getCity());
        c.setCountry(u.getCountry());
        c.setEmail(u.getEmail());
        c.setPassword(u.getPassword());
        c.setPhone(u.getPhone());
        c.setUsername(u.getUsername());
        return c;
    }
    
    public static List<Company> convertUsers(List<User> list){
        List<Company> retList = new ArrayList<>();
        for(User u : list){
            retList.add(convertUser(u));
        }
        return retList;
    }
    
    public String toStirng(){
        return super.toString()+", name="+name+", pib="+pib+", document number="+document_num+", company number="+company_num;
    }
}
