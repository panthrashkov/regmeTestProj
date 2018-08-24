package online.regme.fms.loader.model;

import javax.persistence.*;
import java.util.Date;



@NamedQueries({
        @NamedQuery(
                name = "findFmsByCode",
                query = "SELECT c FROM Fms c WHERE c.code = :code"
        )
})
@Entity
@Table(name = "fms")
public class Fms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    private Date version;
    @Column(name="code")
    private String code;
    @Column(name="name")
    private String name;

    @Column(name="record_version")
    private Integer recordVersion;

    public Fms(String code, String name, Integer recordVersion) {
        this.code = code;
        this.name = name;
        this.recordVersion = recordVersion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }

    public Fms() {
    }
}
