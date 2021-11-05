create table if not exists users(
    id int not null auto_increment primary key,
    username varchar(20) not null unique,
    password varchar(100) not null,
    enabled bit(1) not null,
    role_id int not null,
    CONSTRAINT FK_roleId_user FOREIGN KEY (role_id) REFERENCES role(id)
);

create table if not exists bankAccount(
    id int not null auto_increment primary key,
    accountNumber varchar(100) not null,
    balance double not null,
    type varchar(100) not null,
    cardNumber varchar(16),
    user_id int not null,
    CONSTRAINT FK_userId_account FOREIGN KEY (user_id) REFERENCES users(id)
);

create table if not exists product(
    id int not null auto_increment primary key,
    name varchar(100) not null,
    description varchar(100) not null,
    price double not null,
    stock int not null,
    category varchar(45) not null,
    image varchar(300)
);

create table if not exists review(
    id int not null auto_increment primary key,
    comment varchar(250) not null,
    rating int not null,
    author varchar(100) not null,
    product int not null,
   CONSTRAINT FK_productId_review FOREIGN KEY (product) REFERENCES product(id)
);

create table if not exists orders(
    id int not null auto_increment primary key,
    totalAmount double not null,
    datePlaced date not null,
    account int not null,
    user_id int not null,
    CONSTRAINT FK_accountId_order FOREIGN KEY (account) REFERENCES bankAccount(id),
    CONSTRAINT FK_userId_order FOREIGN KEY (user_id) REFERENCES users(id)
);

create table if not exists order_item(
    id int not null auto_increment primary key,
   quantity int not null,
   price double not null,
   product int not null,
   orders int not null,
   CONSTRAINT FK_productId_item FOREIGN KEY (product) REFERENCES product(id),
   CONSTRAINT FK_orderId_item FOREIGN KEY (orders) REFERENCES orders(id)
);

create table if not exists cart(
    id int not null auto_increment primary key,
    totalAmount double not null,
    user_id int not null,
    CONSTRAINT FK_userId_cart FOREIGN KEY (user_id) REFERENCES users(id)
);

