package com.ngdb.web.services.infrastructure;

import com.ngdb.entities.shop.Basket;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.ShopOrder;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.MailService;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.joda.time.DateMidnight;

import java.util.HashMap;
import java.util.List;
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
        List<ShopOrder> bills = billFactory.createBills();
        for (ShopOrder shopOrder:bills) {
            session.save(shopOrder);
            sendBill(shopOrder);
        }
    }

    private void sendBill(ShopOrder order) {
        Map<String, String> paramsTemplate = new HashMap<String, String>();

        User buyer = order.getBuyer();
        paramsTemplate.put("buyerUsername", buyer.getLogin());
        paramsTemplate.put("buyerEmail", buyer.getEmail());
        paramsTemplate.put("buyerId", buyer.getId().toString());

        User seller = order.getSeller();
        paramsTemplate.put("sellerUsername", seller.getLogin());
        paramsTemplate.put("sellerEmail", seller.getEmail());
        paramsTemplate.put("sellerId", seller.getId().toString());

        paramsTemplate.put("orderDate", new DateMidnight().toString());
        paramsTemplate.put("orderBatchSummary", order.getBill());

        mailService.sendMail(buyer,"checkout", paramsTemplate);
        mailService.sendMail(seller,"checkout", paramsTemplate);
    }

    private void confirmCheckout(User buyer) {
        Basket basket = buyer.getBasket();
        Set<ShopItem> shopItems = basket.all();
        for (ShopItem shopItem : shopItems) {
            shopItem.sold();
            shopItem.removePotentialBuyer(buyer);
        }
    }
}
