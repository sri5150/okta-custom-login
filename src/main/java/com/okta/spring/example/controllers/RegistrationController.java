package com.okta.spring.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import com.okta.spring.example.model.Registration;

@Controller
public class RegistrationController {
	
	public static final String OKTA_DOMAIN = "https://dev-563587.okta.com";
	public static final String API_TOKEN = "00FZ8wJ6mWUVllRVZkQMIQpbYL9WMf1Ay1QlWF_v_r";
		
			/*
			"curl -v -X POST +
			-H "Accept: application/json" \
			-H "Content-Type: application/json" \
			-H "Authorization: SSWS 00FZ8wJ6mWUVllRVZkQMIQpbYL9WMf1Ay1QlWF_v_r" \
			-d '{
			  "profile": {
			    "firstName": "Bill",
			    "lastName": "Brock52",
			    "email": "brock52@mailinator.com",
			    "login": "brock52@mailinator.com",
			    "mobilePhone": "555-415-1337"
			  },
				"credentials": {
						"password": {
							"value": "Nagesh01"
						}
				}
			}' "https://dev-563587.okta.com/api/v1/users?activate=false"
			*/

    @GetMapping("/registration")
    public String registrationForm(Model model) {
    	
    	System.out.println("/registration - GetMapping() ");
    	
    	Registration defReg =  new Registration();
    	defReg.setFirstName("John");
    	defReg.setLastName("Doe");
        model.addAttribute("registration", defReg);
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView registrationSubmit(@ModelAttribute Registration registration) {
    	
    	System.out.println("In PostMapping(). Incoming reg=" + registration);
    	
    	
    	// Create user
    	Client client = Clients.builder()
    		    .setOrgUrl(OKTA_DOMAIN)
    		    .setClientCredentials(new TokenClientCredentials(API_TOKEN))
    		    .build();
    	
    	User user = UserBuilder.instance()
    		    .setEmail(registration.getEmail())
    		    .setFirstName(registration.getFirstName())
    		    .setLastName(registration.getLastName())
    		    .setLogin(registration.getEmail())
    		    .setPassword(registration.getPassword().toCharArray())
    		    .buildAndCreate(client);
    	
    	System.out.println("***User was created" + user);    	
    	
    	// Return to the home page
        return new ModelAndView("forward:/");
    }

}
