package com.example.java.security;

import com.example.java.dto.LoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("MA")
public class MethodAuthentication {
    public boolean hasAuthority(String... authorities){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginDto login=(LoginDto)authentication.getPrincipal();
        for (String authority : authorities) {
            if(login.getPermissions().contains(authority)){
                return true;
            }
        }
        return false;
    }
}
