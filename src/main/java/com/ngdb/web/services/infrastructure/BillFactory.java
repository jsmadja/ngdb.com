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

    public String createBuyerBill() {
        StringBuilder bill = new StringBuilder();
        for (User seller : sellers) {
            bill.append("\nSeller: ").append(seller).append("\n");
            bill.append(prepareBillOf(seller));
        }
        return bill.toString();
    }

    private String prepareBillOf(User seller) {
        StringBuilder bill = new StringBuilder();
        if(seller.getShopPolicy() != null) {
            bill.append(seller.getShopPolicy());
        }
        List<ShopItem> shopItems = basket.allItemsForSaleBy(seller);
        for (ShopItem shopItem : shopItems) {
            insertArticleLine(bill, shopItem);
        }
        String priceToPay = basket.getTotalFor(seller);
        bill.append("\nTotal: ").append(priceToPay);
        return bill.toString();
    }

    private void insertArticleLine(StringBuilder bill, ShopItem shopItem) {
        Article article = shopItem.getArticle();
        String platform = article.getPlatformShortName();
        String origin = article.getOriginTitle();
        String title = shopItem.getTitle();
        Double priceInDollars = shopItem.getPriceInDollars();
        String details = shopItem.getDetails();

        bill.append(title).append(" ").append(platform).append(" ").append(origin).append("\n");
        if(details != null) {
            bill.append(details).append("\n");
        }
        bill.append("$").append(priceInDollars).append("\n");
        bill.append("---").append("\n");
    }

    public Map<User, String> createSellerBills() {
        Map<User, String> bills = new HashMap<User, String>();
        for(User seller:sellers) {
            String bill = prepareBillOf(seller);
            bills.put(seller, bill);
        }
        return bills;
    }
}
