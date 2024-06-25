package com.Mercurius.ApiGateway.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter  implements Converter<Jwt, Collection<GrantedAuthority>> {

	  @Override
	    public Collection<GrantedAuthority> convert(Jwt source) {
	        Map<String, Object> realmAccess = source.getClaim("realm_access");
	        System.out.println("------realmaccess"+realmAccess);
	        if (realmAccess == null || realmAccess.isEmpty()) {
	            return List.of();
	        }

	        List<String> roles = (List<String>) realmAccess.get("roles");
	        Collection<GrantedAuthority> authorities = roles.stream()
	                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
	                .collect(Collectors.toList());
	        System.out.println("authorities:"+authorities);
	        return authorities;
	    }
	}

    

