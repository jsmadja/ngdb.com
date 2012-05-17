package com.ngdb.web.model;

import java.util.List;

import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import com.ngdb.entities.Publisher;

public class PublisherList extends ModelList {

	private SelectModelImpl selectModel;

	public PublisherList(List<Publisher> publishers) {
		List<OptionModel> options = CollectionFactory.newList();
		for (Publisher publisher : publishers) {
			options.add(new OptionModelImpl(publisher.getName(), publisher));
		}
		selectModel = new SelectModelImpl(null, options);
	}

	@Override
	protected SelectModel getSelectModel() {
		return selectModel;
	}

}
