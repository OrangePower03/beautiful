package com.example.java.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CelebrityDto {
    private Integer cid;
    private Integer actid=null;

    public String name;

    @URL(message = "职工头像地址必须是一个正确的地址")
    public String avatar;

    public Integer title;

    public Integer getCid() {
        return cid;
    }

    public Integer getActid() {
        return actid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public void setActid(Integer actid) {
        this.actid = actid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CelebrityDto that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, title);
    }

    @Override
    public String toString() {
        return "CelebrityDto{" +
                "cid=" + cid +
                ", actid=" + actid +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", title=" + title +
                '}';
    }
}

