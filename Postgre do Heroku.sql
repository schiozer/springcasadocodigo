

table desc;

drop table product_prices;

select * from T_USER ;


select * from casadocodigo.product;

select * from casadocodigo.Role;
insert into Role values('ROLE_ADMIN');
insert into Role values('ROLE_COMPRADOR');

insert into T_User(login,name,password)
	values('schiozer',
		   'schiozer',
		   'schiozer');
           
insert into T_User(login,name,password)
	values('admin',
		   'admin',
		   'admin');

insert into T_User(login,name,password)
	values('tuk',
		   'tuk',
		   'tuk');
           
insert into T_User_Role(T_User_login,roles_name) values('schiozer','ROLE_COMPRADOR');
insert into T_User_Role(T_User_login,roles_name) values('admin','ROLE_ADMIN');
insert into T_User_Role(T_User_login,roles_name) values('tuk','ROLE_ADMIN');

update T_User set email = 'schiozer@yahoo.com.br' where login = 'tuk';


select * from casadocodigo_test.Product;


select sum(prices1_.value) as col_0_0_ from casadocodigo.Product product0_ inner join casadocodigo.Product_prices prices1_ on product0_.id=prices1_.Product_id where prices1_.bookType='1';

select * from casadocodigo.Product product0_ inner join casadocodigo.Product_prices prices1_ on product0_.id=prices1_.Product_id where prices1_.bookType='1';