/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entity.Company;
import entity.Component;
import entity.Person;
import entity.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stefan
 */
public class DB {
    public static String dbDriver = "com.mysql.jdbc.Driver";
    public static String dbName = "jdbc:mysql://localhost:3306/is2_system";
    public static String dbUsername = "root";
    public static String dbPassword = "";
    
    static{
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static Connection openConnection(){
        try {
            return DriverManager.getConnection(dbName, dbUsername, dbPassword);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private static void closeConnection(Connection c){
        try {
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean hasComponent(String username, int id){
        boolean ret = false;
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT username from components WHERE id = "+id);
            if(r.next()){
                ret = username.equals(r.getString("username"));
            }
            closeConnection(c);
            return ret;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean login(String username, String password){
        boolean ret = false;
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * from users WHERE username = '"+username+"' and password = '"+password+"'");
            ret = r.next();
            closeConnection(c); 
            return ret;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private static boolean usernameExists(String username){
        boolean ret = false;
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * from users where username = '"+username+"'");
            ret = r.next();
            closeConnection(c);
            return ret;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private static boolean createUser(User u){
        if(usernameExists(u.getUsername()))
            return false;
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            s.executeUpdate("INSERT INTO users(username, password, email, address, phone, city, country) VALUES ('"+u.getUsername()+"','"+u.getPassword()+"','"+u.getEmail()+"','"+u.getAddress()+"','"+u.getPhone()+"','"+u.getCity()+"','"+u.getCountry()+"')");
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static void createPerson(Person p){
        try {
            createUser(p);
            Connection c = openConnection();
            Statement s = c.createStatement();
            s.executeUpdate("INSERT INTO persons(username, name, surname, sex) VALUES ('"+p.getUsername()+"','"+p.getName()+"','"+p.getSurname()+"',"+p.getSex()+")");
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createCompany(Company com){
        try {
            createUser(com);
            Connection c = openConnection();
            Statement s = c.createStatement();
            s.executeUpdate("INSERT INTO companies(username, name, pib, document_num, company_num) VALUES ('"+com.getUsername()+"','"+com.getName()+"','"+com.getPib()+"',"+com.getDocument_num()+"','"+com.getCompany_num()+"')");
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createComponent(Component com){
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            s.executeUpdate("INSERT INTO components(id, company_id, name, type, available, price) VALUES (null,'"+com.getUsername()+"','"+com.getName()+"','"+com.getType()+"',"+com.getAvailable()+","+com.getPrice()+")");
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void editUser(User u){
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            s.executeUpdate("UPDATE users SET username='"+u.getUsername()+"',password='"+u.getPassword()+"',email='"+u.getEmail()+"',address='"+u.getAddress()+"',phone='"+u.getPhone()+"',city='"+u.getCity()+"',country='"+u.getCountry()+"' WHERE username = '"+u.getUsername()+"'");
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void editPerson(Person p){
        try {
            editUser(p);
            Connection c = openConnection();
            Statement s = c.createStatement();
            s.executeUpdate("UPDATE persons SET name='"+p.getName()+"',surname='"+p.getSurname()+"',sex="+p.getSex()+" WHERE username = '"+p.getUsername()+"'");
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void editCompany(Company com){
        try {
            editUser(com);
            Connection c = openConnection();
            Statement s = c.createStatement();
            s.executeUpdate("UPDATE companies SET name='"+com.getName()+"',pib='"+com.getPib()+"',document_num="+com.getDocument_num()+"',company_num="+com.getCompany_num()+" WHERE username = '"+com.getUsername()+"'");
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void editComponent(Component com){
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            s.executeUpdate("UPDATE components SET name='"+com.getName()+"',type='"+com.getType()+"',available="+com.getAvailable()+",price="+com.getPrice()+" WHERE id = '"+com.getId()+"'");
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void removeUser(String username){
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            s.executeUpdate("DELETE from users where username = '"+username+"'");
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void removePerson(String username){
        try {
            removeUser(username);
            Connection c = openConnection();
            Statement s = c.createStatement();
            s.executeUpdate("DELETE from persons where username = '"+username+"'");
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void removeCompany(String username){
        try {
            removeUser(username);
            Connection c = openConnection();
            Statement s = c.createStatement();
            s.executeUpdate("DELETE from companies where username = '"+username+"'");
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void removeComponent(int id){
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            s.executeUpdate("DELETE from components where id = "+id);
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static User findUser(String username){
        try {
            User u = new User();
            Connection c = openConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * from users where username = '"+username+"'");
            if(r.next()){
                u.setUsername(r.getString("username"));
                u.setPassword(r.getString("password"));
                u.setEmail(r.getString("email"));
                u.setPhone(r.getString("phone"));
                u.setAddress(r.getString("address"));
                u.setCity(r.getString("city"));
                u.setCountry(r.getString("country"));
            }
            closeConnection(c);
            return u;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Person findPerson(String username){
        try {
            Person p = Person.convertUser(findUser(username));
            Connection c = openConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * from persons where username = '"+username+"'");
            if(r.next()){
                p.setName(r.getString("name"));
                p.setSurname(r.getString("surname"));
                p.setSex(r.getInt("sex"));
            }
            closeConnection(c);
            return p;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Company findCompany(String username){
        try {
            Company com = Company.convertUser(findUser(username));
            Connection c = openConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * from companies where username = '"+username+"'");
            if(r.next()){
                com.setName(r.getString("name"));
                com.setPib(r.getString("pib"));
                com.setDocument_num(r.getString("document_num"));
                com.setCompany_num(r.getString("company_num"));
            }
            closeConnection(c);
            return com;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Component findComponent(int id){
        try {
            Component com = new Component();
            Connection c = openConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * from components where id = "+id);
            if(r.next()){
                com.setId(r.getInt("id"));
                com.setUsername(r.getString("company_id"));
                com.setName(r.getString("name"));
                com.setAvailable(r.getInt("available"));
                com.setPrice(r.getDouble("price"));
            }
            closeConnection(c);
            return com;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static List<Person> findAllPersons(){
        List<Person> list = new ArrayList<>();
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * from users as u join persons as p on p.username = u.username");
            while(r.next()){
                Person p = new Person();
                p.setUsername(r.getString("username"));
                p.setPassword(r.getString("password"));
                p.setEmail(r.getString("email"));
                p.setPhone(r.getString("phone"));
                p.setAddress(r.getString("address"));
                p.setCity(r.getString("city"));
                p.setCountry(r.getString("country"));
                
                p.setName(r.getString("name"));
                p.setSurname(r.getString("surname"));
                p.setSex(r.getInt("sex"));
                list.add(p);
            }
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static List<Company> findAllCompanies(){
        List<Company> list = new ArrayList<>();
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * from users as u join persons as p on p.username = u.username");
            while(r.next()){
                Company com = new Company();
                com.setUsername(r.getString("username"));
                com.setPassword(r.getString("password"));
                com.setEmail(r.getString("email"));
                com.setPhone(r.getString("phone"));
                com.setAddress(r.getString("address"));
                com.setCity(r.getString("city"));
                com.setCountry(r.getString("country"));
                
                com.setName(r.getString("name"));
                com.setPib(r.getString("pib"));
                com.setDocument_num(r.getString("document_num"));
                com.setCompany_num(r.getString("company_num"));
                list.add(com);
            }
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static List<Component> findAllComponents(){
        List<Component> list = new ArrayList<>();
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * from components");
            while(r.next()){
                Component com = new Component();
                com.setId(r.getInt("id"));
                com.setUsername(r.getString("company_id"));
                com.setName(r.getString("name"));
                com.setAvailable(r.getInt("available"));
                com.setPrice(r.getDouble("price"));
                list.add(com);
            }
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static List<Component> findAllComponentsByCompany(String company_id){
        List<Component> list = new ArrayList<>();
        try {
            Connection c = openConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * from components where company_id = '"+company_id+"'");
            while(r.next()){
                Component com = new Component();
                com.setId(r.getInt("id"));
                com.setUsername(r.getString("company_id"));
                com.setName(r.getString("name"));
                com.setAvailable(r.getInt("available"));
                com.setPrice(r.getDouble("price"));
                list.add(com);
            }
            closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
