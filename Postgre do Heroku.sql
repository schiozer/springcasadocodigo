

table desc;

select * from user_role;

select * from t_user ;


select * from casadocodigo.product;

select * from casadocodigo.Role;
insert into casadocodigo.Role values('ROLE_ADMIN');
insert into casadocodigo.Role values('ROLE_COMPRADOR');

insert into casadocodigo.User(login,name,password)
	values('schiozer',
		   'Fabio',
		   '$2a$10$lt7pS7Kxxe5JfP.vjLNSyOXP11eHgh7RoPxo5fvvbMCZkCUss2DGu');
           
insert into casadocodigo.User(login,name,password)
	values('admin',
		   'Administrador',
		   '$2a$10$lt7pS7Kxxe5JfP.vjLNSyOXP11eHgh7RoPxo5fvvbMCZkCUss2DGu');

insert into casadocodigo.User(login,name,password)
	values('tuk',
		   'tuk',
		   'tuk');
           
insert into casadocodigo.User_Role(User_login,roles_name) values('schiozer','ROLE_COMPRADOR');
insert into casadocodigo.User_Role(User_login,roles_name) values('admin','ROLE_ADMIN');
insert into casadocodigo.User_Role(User_login,roles_name) values('tuk','ROLE_ADMIN');

update casadocodigo.User set email = 'schiozer@yahoo.com.br' where login = 'tuk';

update casadocodigo.User set password = 'schiozer' where login = 'schiozer';
update casadocodigo.User set name = 'Fabio Tuk' where login = 'tuk';
select * from casadocodigo.User ;



select * from casadocodigo_test.Product;


select sum(prices1_.value) as col_0_0_ from casadocodigo.Product product0_ inner join casadocodigo.Product_prices prices1_ on product0_.id=prices1_.Product_id where prices1_.bookType='1';

select * from casadocodigo.Product product0_ inner join casadocodigo.Product_prices prices1_ on product0_.id=prices1_.Product_id where prices1_.bookType='1';