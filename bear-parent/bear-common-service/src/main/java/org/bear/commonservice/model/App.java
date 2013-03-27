package org.bear.commonservice.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.bear.framework.util.Statusable;
import org.bear.global.type.Status;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-27
 */
@Entity
@Table(name="cs_app")
/*@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class App implements Statusable, Serializable {
    private static final long serialVersionUID = 1134958276588667762L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "app_key", length = 32, nullable = false, unique = true)
    private String key;
    @Column(length = 64)
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "cs_app_attr", joinColumns = @JoinColumn(name = "app_id"))
    @MapKeyColumn(length = 64, name = "attr_key")
    @Column(name = "value", length = 100000)
    private Map<String, String> attributes;
    @OneToMany(mappedBy = "app")
    @OrderBy("id")
    private List<Biz> bizs;
    @Enumerated
    @Column(nullable = false)
    private Status status = Status.ENABLED;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<Biz> getBizs() {
        return bizs;
    }

    public void setBizs(List<Biz> bizs) {
        this.bizs = bizs;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
