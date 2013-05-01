package com.ngdb.web.components.article;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.File;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.FileService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.DiscardAfter;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;

import java.util.Collection;

import static java.util.Arrays.asList;

public class FileBlock {

	@Property
	@Parameter
	private Article article;

	@Property
	private File resourceFile;

	@Inject
	private CurrentUser currentUser;

	@Property
    @Validate("required")
    private String name;

    @Property
    @Validate("required")
    private String type;

    @Property
    private UploadedFile file;

    @Property
    private String url;

    @Inject
    private FileService fileService;

    @CommitAfter
    @DiscardAfter
    @OnEvent(value = EventConstants.SUCCESS, component = "fileForm")
    public void onSuccessFromFileForm() {
        if(this.file != null) {
            fileService.store(this.file, article, name, type);
        }
        if(this.url != null) {
            fileService.store(this.url, article, name, type);
        }
    }

    public User getUser() {
		return currentUser.getUser();
	}

	public Collection<File> getFiles() {
		return fileService.getFilesOf(article);
    }

    public Collection<String> getTypes() {
        return asList(
                "Cheat code",
                "Move list",
                "Technical infos",
                "Video",
                "Web page",
                "Other"
        );
    }

	public String getCssClass() {
		if (getFiles().isEmpty() && currentUser.isAnonymous()) {
			return "";
		}
		return "table table-striped table-bordered table-condensed";
	}

}
