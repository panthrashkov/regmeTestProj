package online.regme.fms.loader.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.Date;

@Entity
public class Fms {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Date version;

    private String code;

    private String name;

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
