package com.mobanker.config.bsw.manager.impl;

import com.mobanker.framework.dto.LoginUserDto;
import com.mobanker.framework.service.LoginService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml"})
//		"classpath:/spring/spring-servlet.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
// @Transactional
public abstract class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	@Resource
	private LoginService loginService;

	// @Resource
	// private AdminUsersService adminUsersService;

	@Before()
	public void setUp() {
		// AdminUsers adminUsers = adminUsersService.getUserByUserName("admin");

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		LoginUserDto user = new LoginUserDto();
		// if (adminUsers == null) {
		user.setId("695425f2111040a39e0a57ea8e8e53c7");
		user.setUserName("admin");
		user.setLoginIp("127.0.0.1");
		// } else {
		// user.setId(adminUsers.getId());
		// user.setUserName(adminUsers.getUserName());
		// }
		loginService.userLoginSuccess(user, request, response);

	}

}
