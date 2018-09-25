package com.xishuai.demo.resourceserver.controller;

import com.xishuai.demo.resourceserver.dto.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Controller
public class BarController {

    public BarController() {
        super();
    }

    // API - read
    @PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/bars/{id}")
    @ResponseBody
    public Bar findById(@PathVariable final long id) {

        return new Bar(Long.parseLong(randomNumeric(2)), randomAlphabetic(4));
    }

    // API - write
    @PreAuthorize("#oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST, value = "/bars")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Bar create(@RequestBody final Bar bar) {
        bar.setId(Long.parseLong(randomNumeric(2)));
        return bar;
    }

    // API - users/extra
    @PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/users/extra")
    @ResponseBody
    public Map<String, Object> getExtraInfo(Authentication auth) {

        OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
        Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
        System.out.println("username is " + details.get("username"));

        return details;
    }

}
