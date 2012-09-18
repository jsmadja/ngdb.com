package com.ngdb.web.services.infrastructure;

import com.ngdb.entities.Market;
import com.ngdb.entities.shop.Basket;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.MailService;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.joda.time.DateMidnight;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CheckoutService {

    @Inject
    private MailService mailService;

    @Inject
    private Session session;

    public void checkout(User buyer) {
        sendBills(buyer);
        confirmCheckout(buyer);
    }

    private void sendBills(User buyer) {
        BillFactory billFactory = new BillFactory(buyer);
        Map<User, String> bills = billFactory.createBills();
        for (Map.Entry<User, String> bill:bills.entrySet()) {
            sendBill(buyer, bill);
        }
    }

    private void sendBill(User buyer, Map.Entry<User, String> bill) {
        Map<String, String> paramsTemplate = new HashMap<String, String>();

        paramsTemplate.put("buyerUsername", buyer.getLogin());
        paramsTemplate.put("buyerEmail", buyer.getEmail());
        paramsTemplate.put("buyerId", buyer.getId().toString());

        User seller = bill.getKey();
        paramsTemplate.put("sellerUsername", seller.getLogin());
        paramsTemplate.put("sellerEmail", seller.getEmail());
        paramsTemplate.put("sellerId", seller.getId().toString());

        paramsTemplate.put("orderDate", new DateMidnight().toString());
        paramsTemplate.put("orderBatchSummary", bill.getValue());

        mailService.sendMail(buyer,"checkout", paramsTemplate);
        mailService.sendMail(seller,"checkout", paramsTemplate);
    }

    private void confirmCheckout(User buyer) {
        Basket basket = buyer.getBasket();
        Set<ShopItem> all = basket.all();
        for (ShopItem shopItem : all) {
            shopItem.sold();
            shopItem.removePotentialBuyer(buyer);
        }
    }
}
