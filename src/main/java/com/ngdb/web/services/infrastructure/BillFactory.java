package com.ngdb.web.services.infrastructure;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.Basket;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.ShopOrder;
import com.ngdb.entities.user.User;

import java.util.ArrayList;
import java.util.List;

public class BillFactory {

    private Basket basket;
    private List<User> sellers;
    private User buyer;

    public BillFactory(User buyer) {
        this.buyer = buyer;
        basket = buyer.getBasket();
        sellers = basket.allSellers();
    }

    public List<ShopOrder> createBills() {
        List<ShopOrder> bills = new ArrayList<ShopOrder>();
        for(User seller:sellers) {
            ShopOrder order = new ShopOrder();
            buyer.addOrder(order);
            String bill = prepareBillOf(seller, order);
            order.addBill(bill);
            bills.add(order);
        }
        return bills;
    }

    private String prepareBillOf(User seller, ShopOrder order) {
        StringBuilder bill = new StringBuilder();
        if(seller.getShopPolicy() != null) {
            bill.append(seller.getShopPolicy());
        }

        List<ShopItem> shopItems = basket.allItemsForSaleBy(seller);
        String priceToPay = basket.getTotalFor(seller);

        bill.append("Total article: ").append(shopItems.size()).append("\n");
        bill.append("Total    : ").append(priceToPay).append("\n");

        bill.append("\n--------------------------------------\n");
        bill.append("Order Contents: \n");
        bill.append("--------------------------------------\n\n");

        for (ShopItem shopItem : shopItems) {
            insertArticleLine(bill, shopItem, seller.getPreferedCurrency());
            order.addShopItem(shopItem);
        }

        return bill.toString();
    }

    private void insertArticleLine(StringBuilder bill, ShopItem shopItem, String currency) {
        Article article = shopItem.getArticle();
        String platform = article.getPlatformShortName();
        String origin = article.getOriginTitle();
        String title = shopItem.getTitle();
        String price = shopItem.getPriceAsStringIn(currency);
        String details = shopItem.getDetails();

        bill.append(title).append(" (").append(platform).append(" / ").append(origin).append(")\n");
        bill.append("http://www.neogeodb.com/").append(article.getViewPage()).append("/").append(article.getId()).append("\n");
        bill.append("Quantity: 1   Condition: ").append(shopItem.getState().getTitle()).append("   Price: ").append(price).append("\n");
        if(details != null) {
            bill.append("Description: ").append(details).append("\n\n");
        }
    }

}
