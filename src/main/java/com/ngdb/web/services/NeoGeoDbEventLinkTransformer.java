package com.ngdb.web.services;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.linktransform.ComponentEventLinkTransformer;

public class NeoGeoDbEventLinkTransformer implements ComponentEventLinkTransformer {

	@Override
	public Link transformComponentEventLink(Link defaultLink, ComponentEventRequestParameters parameters) {
		if (isProductionServer(defaultLink)) {
			return defaultLink.copyWithBasePath("http://www.neogeodb.com" + defaultLink.toString());
		}
		return defaultLink;
	}

	private boolean isProductionServer(Link defaultLink) {
		return !defaultLink.toAbsoluteURI().startsWith("http://localhost");
	}

	@Override
	public ComponentEventRequestParameters decodeComponentEventRequest(Request request) {
		return null;
	}

}
