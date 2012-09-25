package com.ngdb.web.components.article;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.File;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.FileService;
import org.apache.tapestry5.annotations.DiscardAfter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;

import java.util.*;

import static java.util.Arrays.asList;

public class FileBlock {

	@Property
	@Parameter
	private Article article;

	@Property
	private File resourceFile;

	@Inject
	private CurrentUser currentUser;

	@Inject
	private ArticleFactory articleFactory;

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
	public void onSuccess() {
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
		if (article.isGame()) {
            Game game = (Game) article;
            Set<File> files = new TreeSet<File>(game.getFiles().all());
            List<Game> relatedGames = articleFactory.findAllGamesByNgh(game.getNgh());
            for (Game relatedGame : relatedGames) {
                files.addAll(relatedGame.getFiles().all());
            }
            return files;
        }
        return article.getFiles().all();
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
