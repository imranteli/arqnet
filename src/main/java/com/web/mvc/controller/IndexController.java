package com.web.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {

	  @RequestMapping(method = RequestMethod.GET)
	    public String getIndexPage() {
	        return "UserManagement";
	    }
         //@RequestMapping(method = RequestMethod.GET)
	  @RequestMapping(value = "/elastic-seaarch/", method = RequestMethod.GET)
	    public String getElasticIndexPage() {
	        return "ElasticSearch";
	    }

}
