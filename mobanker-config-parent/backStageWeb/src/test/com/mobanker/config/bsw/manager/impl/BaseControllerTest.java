package com.mobanker.config.bsw.manager.impl;


import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by qiuyangjun on 2015/1/27.
 */
@WebAppConfiguration
public class BaseControllerTest extends BaseTest {
	 public MockMvc mockMvc;
	 @Autowired
	 private WebApplicationContext wac;
	 @Before()
	 public void setUp() {
	 super.setUp();
	 mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	 }
}
