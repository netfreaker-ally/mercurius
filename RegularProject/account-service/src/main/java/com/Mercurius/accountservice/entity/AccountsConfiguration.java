package com.Mercurius.accountservice.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "")
public record AccountsConfiguration() {

}
