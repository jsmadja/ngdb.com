package com.ngdb.web.services.infrastructure;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Picture;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

import com.ngdb.entities.article.element.File;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.io.ByteStreams.toByteArray;
import static com.google.common.io.Files.createParentDirs;
import static com.google.common.io.Files.write;
import static java.util.UUID.randomUUID;

public class FileService {

	private static final String FILE_ROOT = "/ngdb/files/";

    @Inject
    private Session session;

    public File store(UploadedFile uploadedFile, Article article, String name, String type) {
		try {
			File file = createFileFromUploadedFile(uploadedFile, article);
            file.setName(name);
            file.setType(type);
            file.setArticle(article);
            article.addFile(file);
            file = (File) session.merge(file);
            return file;
		} catch (IOException e) {
			throw new IllegalStateException("Cannot create file with name '" + uploadedFile.getFileName() + "' for article " + article.getId(), e);
		}
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
