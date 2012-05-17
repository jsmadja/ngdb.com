package com.ngdb.web.model;

import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.SelectModelVisitor;

public abstract class ModelList implements SelectModel {

	@Override
	public List<OptionGroupModel> getOptionGroups() {
		return getSelectModel().getOptionGroups();
	}

	@Override
	public List<OptionModel> getOptions() {
		return getSelectModel().getOptions();
	}

	@Override
	public void visit(SelectModelVisitor visitor) {
		getSelectModel().visit(visitor);
	}

	protected abstract SelectModel getSelectModel();

}