package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-15T22:55:33")
@StaticMetamodel(Company.class)
public class Company_ extends User_ {

    public static volatile SingularAttribute<Company, String> name;
    public static volatile SingularAttribute<Company, String> company_num;
    public static volatile SingularAttribute<Company, String> pib;
    public static volatile SingularAttribute<Company, String> document_num;

}