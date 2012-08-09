package com.ngdb.web.components.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.annotations.Parameter;

public class Platform {

	@Parameter
	private com.ngdb.entities.reference.Platform platform;

	private static final Map<String, String> shortNames = new HashMap<String, String>();

	static {
		shortNames.put("Neo·Geo CD", "NGCD");
		shortNames.put("Neo·Geo Pocket", "NGP");
		shortNames.put("Neo·Geo Pocket Color", "NGPC");
		shortNames.put("Home System", "AES");
		shortNames.put("Multi Video System", "MVS");
		shortNames.put("Hyper Neo·Geo 64", "H64");
	}

	public String getPlatformName() {
		return shortNames.get(platform.getName());
	}

}
