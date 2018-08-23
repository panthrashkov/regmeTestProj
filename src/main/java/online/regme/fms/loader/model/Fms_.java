package online.regme.fms.loader.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Fms.class)
public abstract class Fms_ {

    public static volatile SingularAttribute<Fms, Integer> id;
    public static volatile SingularAttribute<Fms, String> name;
    public static volatile SingularAttribute<Fms, String> code;
    public static volatile SingularAttribute<Fms, Integer> recordVersion;

}
