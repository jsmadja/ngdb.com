package com.ngdb.web.services;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.linktransform.PageRenderLinkTransformer;

public class NeoGeoDbLinkTransformer implements PageRenderLinkTransformer {

	public Link transformPageRenderLink(Link defaultLink, PageRenderRequestParameters parameters) {
		if (isProductionServer(defaultLink)) {
			return defaultLink.copyWithBasePath("http://www.neogeodb.com" + defaultLink.toString());
		}
		return defaultLink;
	}

	private boolean isProductionServer(Link defaultLink) {
		return !defaultLink.toAbsoluteURI().startsWith("http://localhost");
	}

	@Override
	public PageRenderRequestParameters decodePageRenderRequest(Request request) {
		return null;
	}
}