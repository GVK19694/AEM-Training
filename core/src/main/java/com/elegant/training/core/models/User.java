package com.elegant.training.core.models;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class)
public class User {
    private final Logger LOGGER = LoggerFactory.getLogger(User.class);

    @ValueMapValue
    private String fname;

    @ValueMapValue
    private String lname;

    @ValueMapValue
    private String gender;

    @ValueMapValue
    private String address;

    @ValueMapValue
    private String email;

    @PostConstruct
    protected void init(){
        LOGGER.info("");
    }

    public String getFname() { return fname; }

    public String getLname() { return lname; }

    public String getGender() { return gender; }

    public String getEmail() { return email; }

    public String getAddress() { return address; }
}
