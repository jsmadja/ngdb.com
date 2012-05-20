package com.ngdb.web.model;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import com.ngdb.entities.State;

public class StateList extends ModelList {

	private SelectModelImpl selectModel;

	public StateList(List<State> states) {
		Collections.sort(states);
		List<OptionModel> options = CollectionFactory.newList();
		for (State state : states) {
			options.add(new OptionModelImpl(state.getTitle(), state));
		}
		selectModel = new SelectModelImpl(null, options);
	}

	@Override
	protected SelectModel getSelectModel() {
		return selectModel;
	}

}
