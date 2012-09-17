package com.ngdb.web.services.infrastructure;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.PotentialBuys;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillFactory {

    private PotentialBuys basket;
    private List<User> sellers;

    public BillFactory(User buyer) {
        basket = buyer.getBasket();
        sellers = basket.allSellers();
    }

    public Map<User, String> createBills() {
        Map<User, String> bills = new HashMap<User, String>();
        for(User seller:sellers) {
            String bill = prepareBillOf(seller);
            bills.put(seller, bill);
        }
        return bills;
    }

    private String prepareBillOf(User seller) {
        StringBuilder bill = new StringBuilder();
        if(seller.getShopPolicy() != null) {
            bill.append(seller.getShopPolicy());
        }

        List<ShopItem> shopItems = basket.allItemsForSaleBy(seller);
        String priceToPay = basket.getTotalFor(seller);

        bill.append("Total article: ").append(shopItems.size()).append("\n");
        bill.append("Total    : ").append(priceToPay).append("\n");

        bill.append("--------------------------------------\n");
        bill.append("Order Contents: \n");
        bill.append("--------------------------------------\n\n");

        for (ShopItem shopItem : shopItems) {
            insertArticleLine(bill, shopItem);
        }

        return bill.toString();
    }

    private void insertArticleLine(StringBuilder bill, ShopItem shopItem) {
        Article article = shopItem.getArticle();
        String platform = article.getPlatformShortName();
        String origin = article.getOriginTitle();
        String title = shopItem.getTitle();
        Double priceInDollars = shopItem.getPriceInDollars();
        String details = shopItem.getDetails();

        bill.append(title).append(" (").append(platform).append(" / ").append(origin).append(")\n");
        bill.append("http://www.neogeodb.com/").append(article.getViewPage()).append("/").append(article.getId()).append("\n");
        bill.append("Quantity: 1   Condition: ").append(shopItem.getState().getTitle()).append("   Price: $").append(priceInDollars).append("\n");
        if(details != null) {
            bill.append("Description: ").append(details).append("\n\n");
        }
    }

}
