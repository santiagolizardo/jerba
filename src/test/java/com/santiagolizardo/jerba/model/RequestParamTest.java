package com.santiagolizardo.jerba.model;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;
import org.junit.Test;

import com.santiagolizardo.jerba.utilities.RequestParam;

import junit.framework.TestCase;

public class RequestParamTest extends TestCase {

	@Test
	public void testGetParams() {
		HttpServletRequest req = EasyMock.createMock(HttpServletRequest.class);
		EasyMock.expect(req.getParameter("dollars")).andReturn(null).times(1);
		EasyMock.expect(req.getParameter("pounds")).andReturn("90").times(1);
		EasyMock.replay(req);

		RequestParam requestParam = new RequestParam(req);
		assertNull(requestParam.getInteger("dollars"));
		assertEquals(90, requestParam.getInteger("pounds").intValue());

		EasyMock.verify(req);
	}
}
