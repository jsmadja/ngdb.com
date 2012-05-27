package com.ngdb.web.model;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import com.ngdb.entities.reference.Platform;

public class PlatformList extends ModelList {

	private SelectModelImpl selectModel;

	public PlatformList(List<Platform> platforms) {
		Collections.sort(platforms);
		List<OptionModel> options = CollectionFactory.newList();
		for (Platform platform : platforms) {
			options.add(new OptionModelImpl(platform.getName(), platform));
		}
		selectModel = new SelectModelImpl(null, options);
	}

	@Override
	protected SelectModel getSelectModel() {
		return selectModel;
	}

}
