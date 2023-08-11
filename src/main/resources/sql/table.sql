CREATE TABLE shoe_inventory (
  id INT NOT NULL AUTO_INCREMENT,
  brand VARCHAR(50) NOT NULL,
  model VARCHAR(50) NOT NULL,
  colorway VARCHAR(50) NOT NULL,
  size  DECIMAL(3,1) NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  est_sale_price DECIMAL(10,2),
  quantity INT NOT NULL,
  styleCode VARCHAR(50) NOT NULL,
  sku VARCHAR(50) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE shoe_sale (
	id INT NOT NULL AUTO_INCREMENT,
	sku VARCHAR(50) NOT NULL,
    sale_price DECIMAL(10,2) NOT NULL,
    vendor_id INT,
    total_payout DECIMAL(10,2),
    sale_date DATE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE sale_vendor(
	id INT NOT NULL AUTO_INCREMENT,
    vendor_name VARCHAR(50),
    vendor_fee DECIMAL(10,2),
    fee_desc VARCHAR(100),
    PRIMARY KEY (id)
);

INSERT INTO sale_vendor VALUES (1, 'Ebay', .08, '8% Processing Fee');
INSERT INTO sale_vendor VALUES (2, 'StockX', .12, '9% Transaction Fee + 3% Payment Processing Fee + $4 Shipping');