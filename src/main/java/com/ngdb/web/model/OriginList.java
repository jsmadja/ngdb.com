package com.ngdb.web.model;

import com.ngdb.entities.reference.Origin;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import java.util.Collections;
import java.util.List;

public class OriginList extends ModelList {

	private SelectModelImpl selectModel;

	public OriginList(List<Origin> origins) {
		Collections.sort(origins);
		List<OptionModel> options = CollectionFactory.newList();
		for (Origin origin : origins) {
			options.add(new OptionModelImpl(origin.getTitle(), origin));
		}
		selectModel = new SelectModelImpl(null, options);
	}

	@Override
	protected SelectModel getSelectModel() {
		return selectModel;
	}

}
