drop database if exists DB_SGP;
create database DB_SGP;
use DB_SGP;

create table T_SGP_FUNCIONARIO(
	id_funcionario 	int,
    nm_funcionario 	varchar(150) 	not null,
    dt_nascimento 	date 			not null,
    nr_cpf 			char(14)		not null,
    ds_senha		varchar(255)	not null,
    tp_func			tinyint			not null
);

alter table T_SGP_FUNCIONARIO
	modify column id_funcionario int not null auto_increment,
	add constraint FUNCIONARIO_PK primary key (id_funcionario),
    add constraint FUNCIONARIO_CPF_UN unique (nr_cpf);
    

create table T_SGP_GERENTE(
	id_funcionario 	int
);

alter table T_SGP_GERENTE
	add constraint GERENTE_PK primary key (id_funcionario),
    add constraint FUNCIONARIO_GERENTE foreign key (id_funcionario) references T_SGP_FUNCIONARIO(id_funcionario);
    

create table T_SGP_COZINHEIRO(
	id_funcionario 	int
);

alter table T_SGP_COZINHEIRO
	add constraint COZINHEIRO_PK primary key (id_funcionario),
    add constraint FUNCIONARIO_COZINHEIRO foreign key (id_funcionario) references T_SGP_FUNCIONARIO(id_funcionario);
    

create table T_SGP_CAIXA(
	id_funcionario 	int
);

alter table T_SGP_CAIXA
	add constraint CAIXA_PK primary key (id_funcionario),
    add constraint FUNCIONARIO_CAIXA foreign key (id_funcionario) references T_SGP_FUNCIONARIO(id_funcionario);
    

create table T_SGP_CLIENTE(
	id_cliente 	int,
    nm_cliente 	varchar(150) 	not null,
    nr_telefone char(11) 		not null
);

alter table T_SGP_CLIENTE
	modify column id_cliente int not null auto_increment,
	add constraint CLIENTE_PK primary key(id_cliente);
    

create table T_SGP_PRODUTO(
	id_produto	int,
    nm_produto	varchar(100)	not null,
    vl_preco	decimal(6,2)	not null,
    tp_produto	tinyint			not null
);

alter table T_SGP_PRODUTO
	modify column id_produto int not null auto_increment,
	add constraint PRODUTO_PK primary key(id_produto);
    

create table T_SGP_PEDIDO(
	id_pedido 		int,
    id_cliente 		int 	not null,
    id_funcionario 	int 	not null,
    dt_pedido 		date	not null
);

alter table T_SGP_PEDIDO
	modify column id_pedido int not null auto_increment,
	add constraint PEDIDO_PK primary key(id_pedido),
    add constraint CLIENTE_PEDIDO_FK foreign key (id_cliente) references T_SGP_CLIENTE (id_cliente),
    add constraint CAIXA_PEDIDO_FK foreign key (id_funcionario) references T_SGP_CAIXA (id_funcionario);
    
    
create table T_PEDIDO_PRODUTO(
	id_pedido 		int,
    id_produto		int
);

alter table T_PEDIDO_PRODUTO
	add constraint PEDIDO_PRODUTO_PK primary key (id_pedido, id_produto),
    add constraint PRODUTO_PEDIDO_FK foreign key (id_produto) references T_SGP_PRODUTO (id_produto),
    add constraint PEDIDO_PRODUTO_FK foreign key (id_pedido) references T_SGP_PEDIDO (id_pedido);
    