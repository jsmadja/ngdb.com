package com.ngdb.web.pages;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.ArticlePictures;
import com.ngdb.entities.article.element.File;
import com.ngdb.entities.article.element.Files;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.ShopItems;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.FileService;
import com.ngdb.web.services.infrastructure.PictureService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiresUser
public class Administration {

    @Property
    private Long articleId;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private Session session;

    @Inject
    private ArticleFactory articleFactory;

    @Inject
    private com.ngdb.entities.Market market;

    @Inject
    private FileService fileService;

    @Inject
    private PictureService pictureService;

    private static final Logger LOG = LoggerFactory.getLogger(Administration.class);

    @CommitAfter
    public Object onSuccess() {
        if(currentUser.isContributor()) {
            deleteArticle();
        }
        return this;
    }

    private void deleteArticle() {
        Article article = articleFactory.findById(articleId);
        pictureService.invalidateCoverOf(article);
        ArticlePictures pictures = article.getPictures();
        for (Picture picture : pictures) {
            pictureService.delete(picture);
        }
        ShopItems shopItems = article.getShopItems();
        for (ShopItem shopItem:shopItems) {
            for (Picture picture:shopItem.getPictures()) {
                pictureService.delete(picture);
            }
        }
        Files files = article.getFiles();
        for (File file : files) {
            fileService.delete(file);
        }
        session.delete(article);
        session.flush();
        market.refresh();
        LOG.info(currentUser.getUsername()+" has just deleted "+articleId);
    }

}
