package com.example.java.dto;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GetArtworkDto {
    public static final String ALL="all";
    public static final String IP="ip";
    public static final String USER="user";
    public static final String STAFF="staff";

    public Integer id;
    public String atitle;
    public String title;
    public String avatar;
    public String intro;
    public String ip;
    public String kind;
    public String resourceAddress;
    public String userName;

    // 媒介
    public Integer ipId;
    public Integer kid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetArtworkDto that)) return false;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(atitle, that.atitle)) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(avatar, that.avatar)) return false;
        if (!Objects.equals(intro, that.intro)) return false;
        if (!Objects.equals(ip, that.ip)) return false;
        if (!Objects.equals(kind, that.kind)) return false;
        if (!Objects.equals(resourceAddress, that.resourceAddress))
            return false;
        if (!Objects.equals(userName, that.userName)) return false;
        if (!Objects.equals(ipId, that.ipId)) return false;
        return Objects.equals(kid, that.kid);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (atitle != null ? atitle.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (intro != null ? intro.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (kind != null ? kind.hashCode() : 0);
        result = 31 * result + (resourceAddress != null ? resourceAddress.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (ipId != null ? ipId.hashCode() : 0);
        result = 31 * result + (kid != null ? kid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GetArtworkDto{" +
                "id=" + id +
                ", atitle='" + atitle + '\'' +
                ", title='" + title + '\'' +
                ", avatar='" + avatar + '\'' +
                ", intro='" + intro + '\'' +
                ", ip='" + ip + '\'' +
                ", kind='" + kind + '\'' +
                ", resourceAddress='" + resourceAddress + '\'' +
                ", userName='" + userName + '\'' +
                ", ipId=" + ipId +
                ", kid=" + kid +
                '}';
    }
}
