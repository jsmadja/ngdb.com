UPDATE ShopItem SET customCurrency = null WHERE priceInCustomCurrency is null; 
UPDATE ShopItem SET customCurrency = "EUR" WHERE priceInCustomCurrency is null;
UPDATE ShopItem SET priceInCustomCurrency = priceInEuros WHERE priceInCustomCurrency is null;

ALTER TABLE `ngdb`.`shopitem` DROP COLUMN `priceInEuros` , DROP COLUMN `priceInDollars` ;
