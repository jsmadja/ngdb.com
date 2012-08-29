package com.ngdb.web.components.article;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.File;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.FileService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.Collection;

public class FileBlock {

	@Property
	@Parameter
	private Article article;

	@Property
	private File resourceFile;

	@Inject
	private CurrentUser currentUser;

	@Inject
	private GameFactory gameFactory;

    @Property
    @Validate("required")
    private String name;

    @Property
    @Validate("required")
    private String type;

    @Property
    private UploadedFile file;

    @Inject
    private FileService fileService;

    @CommitAfter
    @DiscardAfter
	public void onSuccess() {
        fileService.store(this.file, article, name, type);
    }

    public User getUser() {
		return currentUser.getUser();
	}

	public Collection<File> getFiles() {
		return article.getFiles().all();
	}

    public Collection<String> getTypes() {
        return Arrays.asList("Cheat code", "Move list", "Technical infos", "Other");
    }

	public String getCssClass() {
		if (getFiles().isEmpty() && currentUser.isAnonymous()) {
			return "";
		}
		return "table table-striped table-bordered table-condensed";
	}

}
