package com.ngdb.web.services.infrastructure;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.File;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.io.ByteStreams.toByteArray;
import static com.google.common.io.Files.createParentDirs;
import static com.google.common.io.Files.write;

public class FileService {

	private static final String FILE_ROOT = "/ngdb/files/";

    @Inject
    private Session session;

    @Inject
    private CurrentUser currentUser;

    public File store(UploadedFile uploadedFile, Article article, String name, String type) {
		try {
			File file = createFileFromUploadedFile(uploadedFile, article);
            return addFile(article, name, type, file);
		} catch (IOException e) {
			throw new IllegalStateException("Cannot create file with name '" + uploadedFile.getFileName() + "' for article " + article.getId(), e);
		}
	}

    public File store(String url, Article article, String name, String type) {
        File file = new File(name, url, type);
        return addFile(article, name, type, file);
    }

    private File addFile(Article article, String name, String type, File file) {
        file.setName(name);
        file.setType(type);
        file.setArticle(article);
        article.addFile(file);
        file = (File) session.merge(file);
        currentUser.addFile(article);
        return file;
    }

    private File createFileFromUploadedFile(UploadedFile uploadedFile, Article article) throws IOException {
		InputStream fromStream = uploadedFile.getStream();
		String fileName = uploadedFile.getFileName();
        String parentFolder = FILE_ROOT + article.getId() + "/";
        java.io.File to = new java.io.File(new java.io.File(parentFolder), fileName);
        createParentDirs(to);
        byte[] from = toByteArray(fromStream);
        write(from, to);
        return new File(parentFolder + fileName);
    }

	public void delete(File file) {
		new java.io.File(file.getUrl()).getParentFile().delete();
	}

}
