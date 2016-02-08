package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-15T22:55:34")
@StaticMetamodel(Person.class)
public class Person_ extends User_ {

    public static volatile SingularAttribute<Person, Integer> sex;
    public static volatile SingularAttribute<Person, String> name;
    public static volatile SingularAttribute<Person, String> surname;

}