package com.ngdb.web.model;

import com.ngdb.entities.reference.Publisher;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import java.util.Collections;
import java.util.List;

public class PublisherList extends ModelList {

	private SelectModelImpl selectModel;

	public PublisherList(List<Publisher> publishers) {
		Collections.sort(publishers);
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
