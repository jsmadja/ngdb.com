package com.ngdb.web.model;

import java.util.List;

import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import com.ngdb.entities.Genre;

public class GenreList extends ModelList {

	private SelectModelImpl selectModel;

	public GenreList(List<Genre> genres) {
		List<OptionModel> options = CollectionFactory.newList();
		for (Genre genre : genres) {
			options.add(new OptionModelImpl(genre.getTitle(), genre));
		}
		selectModel = new SelectModelImpl(null, options);
	}

	@Override
	protected SelectModel getSelectModel() {
		return selectModel;
	}

}
