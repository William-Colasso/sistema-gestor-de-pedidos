INSERT INTO T_SGP_CATEGORIA_PRODUTO (nm_categoria, sg_categoria, sq_imagem)
VALUES ('Lanches', 'LA', '0x00'),
       ('Bebidas', 'BE', '0x00'),
       ('Sobremesas', 'SO', '0x00'),
       ('Porções', 'PO', '0x00'),
       ('Molhos', 'MO', '0x00'),
       ('Veganos', 'VG', '0x00'),
       ('Promoções', 'PR', '0x00'),
       ('Infantil', 'IN', '0x00'),
       ('Especial', 'ES', '0x00'),
       ('Clássicos', 'CL', '0x00');


INSERT INTO T_SGP_CLIENTE (nm_cliente, nr_telefone, nr_cpf, dt_registro)
VALUES 
('Ana Costa', '11970000001', '111.111.199-92', NOW()),
('Pedro Rocha', '11970000002', '221.12.121-02', NOW()),
('Luiza Martins', '11970000003', '111.211.111-03', NOW()),
('Marcos Paulo', '11970000004', '111.121.111-04', NOW()),
('Julia Antunes', '11970000005', '111.121.111-05', NOW()),
('Ricardo Alves', '11970000006', '111.121.111-06', NOW()),
('Beatriz Ramos', '11970000007', '111.111.111-07', NOW()),
('Tiago Salles', '11970000008', '111.111.111-08', NOW()),
('Fernanda Prado', '11970000009', '111.112.111-09', NOW()),
('Gustavo Moura', '11970000010', '111.111.111-10', NOW()),
('Rafaela Dias', '11970000011', '111.111.211-12', NOW()),
('Otávio Mendes', '11970000012', '131.111.111-33', NOW()),
('Carolina Luz', '11970000013', '131.111.111-14', NOW()),
('Samuel Vieira', '11970000014', '111.111.211-15', NOW()),
('Isabela Nunes', '11970000015', '111.211.111-16', NOW()),
('Felipe Amaral', '11970000016', '111.211.111-17', NOW()),
('Patrícia Klein', '11970000017', '111.411.111-18', NOW()),
('Eduardo Souza', '11970000018', '111.151.111-19', NOW()),
('Marcela Linhares', '11970000019', '111.131.111-20', NOW()),
('Diego Moreira', '11970000020', '111.131.111-21', NOW());

INSERT INTO T_SGP_PARCEIRO (nm_parceiro, ds_email, nr_telefone)
VALUES 
('Coca-Cola', 'coca@corp.com', '11988880001'),
('Heinz', 'heinz@corp.com', '11988880002'),
('Nestlé', 'nestle@corp.com', '11988880003'),
('Burger Corp', 'burger@corp.com', '11988880004'),
('FritasMax', 'fritas@corp.com', '11988880005'),
('Pão&Arte', 'pao@corp.com', '11988880006'),
('Molho&cia', 'molho@corp.com', '11988880007'),
('SucoTop', 'suco@corp.com', '11988880008'),
('ChocoFun', 'choco@corp.com', '11988880009'),
('MegaFoods', 'mega@corp.com', '11988880010');

INSERT INTO T_SGP_FORMA_PAGAMENTO (nm_pagamento, sg_pagamento, sq_imagem)
VALUES 
('Crédito', 'CRT', '0x00'),
('Débito', 'DEB', '0x00'),
('Pix', 'PIX', '0x00'),
('Dinheiro', 'DIN', '0x00'),
('Voucher', 'VCH', '0x00');



INSERT INTO T_SGP_FUNCIONARIO (id_gerente, nm_funcionario, dt_nascimento, nr_cpf)
VALUES 
(NULL, 'Carlos Gerente', '1980-02-10', '200.000.000-01'),
(NULL, 'Marina Gerente', '1985-06-10', '200.000.000-02'),
(NULL, 'Rogério Gerente', '1979-04-10', '200.000.000-03');

INSERT INTO T_SGP_GERENTE (id_funcionario, ds_senha, nm_login)
VALUES 
(1, 'senha1', 'carlos.g'),
(2, 'senha2', 'marina.g'),
(3, 'senha3', 'rogerio.g');

INSERT INTO T_SGP_FUNCIONARIO (id_gerente, nm_funcionario, dt_nascimento, nr_cpf)
VALUES
(1, 'João Atendente', '1995-01-01', '200.000.001-01'),
(1, 'Paulo Andrade', '1994-02-02', '200.000.001-02'),
(1, 'Fernanda Silva', '1997-03-03', '200.000.001-03'),
(2, 'Ricardo Lopes', '1992-04-04', '200.000.001-04'),
(2, 'Patrícia Melo', '1993-05-05', '200.000.001-05'),
(2, 'Sérgio Pedro', '1991-06-06', '200.000.001-06'),
(3, 'Larissa Cunha', '1996-07-07', '200.000.001-07'),
(3, 'Eduardo Pinto', '1998-08-08', '200.000.001-08'),
(3, 'Beatriz Lima', '1999-09-09', '200.000.001-09'),
(1, 'Mateus Prado', '1995-10-10', '200.000.001-10'),
(2, 'Camila Torres', '1994-11-11', '200.000.001-11'),
(3, 'Thiago Luz', '1992-12-12', '200.000.001-12'),
(1, 'Sofia Martins', '1995-01-13', '200.000.001-13'),
(2, 'Bruno Kaiser', '1990-02-14', '200.000.001-14'),
(3, 'Vinicius Falcão', '1991-03-15', '200.000.001-15'),
(1, 'Helena Duarte', '1998-04-16', '200.000.001-16'),
(2, 'Renato Garcia', '1996-05-17', '200.000.001-17'),
(3, 'Mariana Porto', '1997-06-18', '200.000.001-18'),
(1, 'Lucas Rezende', '1999-07-19', '200.000.001-19'),
(2, 'Isabela Rocha', '1998-08-20', '200.000.001-20');

INSERT INTO T_SGP_CUPOM (id_parceiro, vl_desconto, ds_cupom, nm_cupom, st_valido)
VALUES
(1, 5.00, 'Cupom 1', 'CP1', 1),
(2, 3.00, 'Cupom 2', 'CP2', 1),
(3, 2.50, 'Cupom 3', 'CP3', 1),
(4, 4.00, 'Cupom 4', 'CP4', 1),
(5, 3.50, 'Cupom 5', 'CP5', 1),
(6, 2.00, 'Cupom 6', 'CP6', 1),
(7, 4.50, 'Cupom 7', 'CP7', 1),
(8, 3.00, 'Cupom 8', 'CP8', 1),
(9, 1.50, 'Cupom 9', 'CP9', 1),
(10, 5.00, 'Cupom 10', 'CP10', 1),
(1, 5.00, 'Cupom 11', 'CP11', 1),
(2, 3.00, 'Cupom 12', 'CP12', 1),
(3, 2.50, 'Cupom 13', 'CP13', 1),
(4, 4.00, 'Cupom 14', 'CP14', 1),
(5, 3.50, 'Cupom 15', 'CP15', 1),
(6, 2.00, 'Cupom 16', 'CP16', 1),
(7, 4.50, 'Cupom 17', 'CP17', 1),
(8, 3.00, 'Cupom 18', 'CP18', 1),
(9, 1.50, 'Cupom 19', 'CP19', 1),
(10, 5.00, 'Cupom 20', 'CP20', 1),
(1, 5.00, 'Cupom 21', 'CP21', 1),
(2, 3.00, 'Cupom 22', 'CP22', 1),
(3, 2.50, 'Cupom 23', 'CP23', 1),
(4, 4.00, 'Cupom 24', 'CP24', 1),
(5, 3.50, 'Cupom 25', 'CP25', 1),
(6, 2.00, 'Cupom 26', 'CP26', 1),
(7, 4.50, 'Cupom 27', 'CP27', 1),
(8, 3.00, 'Cupom 28', 'CP28', 1),
(9, 1.50, 'Cupom 29', 'CP29', 1),
(10, 5.00, 'Cupom 30', 'CP30', 1),
(1, 5.00, 'Cupom 31', 'CP31', 1),
(2, 3.00, 'Cupom 32', 'CP32', 1),
(3, 2.50, 'Cupom 33', 'CP33', 1),
(4, 4.00, 'Cupom 34', 'CP34', 1),
(5, 3.50, 'Cupom 35', 'CP35', 1),
(6, 2.00, 'Cupom 36', 'CP36', 1),
(7, 4.50, 'Cupom 37', 'CP37', 1),
(8, 3.00, 'Cupom 38', 'CP38', 1),
(9, 1.50, 'Cupom 39', 'CP39', 1),
(10, 5.00, 'Cupom 40', 'CP40', 1),
(1, 5.00, 'Cupom 41', 'CP41', 1),
(2, 3.00, 'Cupom 42', 'CP42', 1),
(3, 2.50, 'Cupom 43', 'CP43', 1),
(4, 4.00, 'Cupom 44', 'CP44', 1),
(5, 3.50, 'Cupom 45', 'CP45', 1),
(6, 2.00, 'Cupom 46', 'CP46', 1),
(7, 4.50, 'Cupom 47', 'CP47', 1),
(8, 3.00, 'Cupom 48', 'CP48', 1),
(9, 1.50, 'Cupom 49', 'CP49', 1),
(10, 5.00, 'Cupom 50', 'CP50', 1);

INSERT INTO T_SGP_ITEM (nm_item, ds_item, tp_item, st_ativo, dt_criacao) VALUES
-- PRODUTOS (1 a 15)
('Hambúrguer Simples', 'Pão, carne 120g e queijo', 'P', 1, NOW()),
('Hambúrguer Duplo', 'Dois hambúrgueres 120g e queijo', 'P', 1, NOW()),
('Cheeseburger', 'Hambúrguer com queijo cheddar', 'P', 1, NOW()),
('X-Salada', 'Hambúrguer com alface, tomate e queijo', 'P', 1, NOW()),
('X-Bacon', 'Hambúrguer com bacon crocante', 'P', 1, NOW()),
('X-Egg', 'Hambúrguer com ovo', 'P', 1, NOW()),
('Batata Média', 'Porção média de batatas fritas', 'P', 1, NOW()),
('Batata Grande', 'Porção grande de batatas fritas', 'P', 1, NOW()),
('Refrigerante 300ml', 'Bebida lata 300ml', 'P', 1, NOW()),
('Refrigerante 600ml', 'Bebida 600ml', 'P', 1, NOW()),
('Suco Natural', 'Copo de suco natural', 'P', 1, NOW()),
('Água Mineral', 'Água sem gás 500ml', 'P', 1, NOW()),
('Milkshake Morango', 'Milkshake de morango 400ml', 'P', 1, NOW()),
('Milkshake Chocolate', 'Milkshake de chocolate 400ml', 'P', 1, NOW()),
('Café Expresso', 'Dose de café expresso', 'P', 1, NOW()),

-- COMBOS (16 a 25)
('Combo Clássico', 'Hambúrguer simples + batata + refri', 'C', 1, NOW()),
('Combo Duplo', 'Hambúrguer duplo + batata grande + refri', 'C', 1, NOW()),
('Combo Família', '2 hambúrgueres + batatas + bebidas', 'C', 1, NOW()),
('Combo Kids', 'Mini lanche + suco + brinde', 'C', 1, NOW()),
('Combo Bacon', 'X-bacon + batata grande + refri', 'C', 1, NOW()),
('Combo Egg', 'X-egg + batata média + suco', 'C', 1, NOW()),
('Combo Shake', 'Cheeseburger + milkshake', 'C', 1, NOW()),
('Combo Light', 'X-salada + suco natural', 'C', 1, NOW()),
('Combo Energia', 'Café + batata + água', 'C', 1, NOW()),
('Combo Duplo Premium', '2 hambúrgueres duplos + batata + 2 refris', 'C', 1, NOW());


INSERT INTO T_SGP_PRODUTO (id_item, id_categoria, vl_produto) VALUES
(1, 1, 12.90),
(2, 1, 15.50),
(3, 1, 11.00),
(4, 2, 32.00),
(5, 2, 29.90),
(6, 2, 35.00),
(7, 3, 25.00),
(8, 3, 27.50),
(9, 4, 22.00),
(10, 4, 24.90),
(11, 5, 9.90),
(12, 5, 8.50),
(13, 6, 6.00),
(14, 6, 7.50),
(15, 7, 4.00);

INSERT INTO T_SGP_COMBO (id_item, vl_desconto) VALUES
(16, 3.00),
(17, 5.00),
(18, 7.50),
(19, 2.00),
(20, 4.00),
(21, 3.50),
(22, 2.50),
(23, 3.00),
(24, 1.50),
(25, 6.00);


INSERT INTO T_PRODUTO_COMBO (id_item_combo, id_item_produto, nr_quantidade) VALUES
(16, 1, 1),
(16, 11, 1),
(17, 2, 1),
(17, 12, 2),
(18, 3, 2),
(18, 15, 1),
(19, 5, 1),
(19, 7, 1),
(20, 6, 1),
(21, 8, 1),
(22, 9, 2),
(23, 10, 1),
(24, 4, 2),
(25, 14, 3);

INSERT INTO T_SGP_MIDIA (id_item, ds_midia, sq_midia, tp_midia) VALUES
(1, 'IMG', 0x00, 'I');


INSERT INTO T_SGP_MIDIA (id_item, ds_midia, sq_midia, tp_midia) VALUES
(1, 'IMG', 0x00, 'I'),
(1, 'IMG', 0x00, 'I'),
(2, 'IMG', 0x00, 'I'),
(3, 'IMG', 0x00, 'I'),
(4, 'IMG', 0x00, 'I'),
(5, 'IMG', 0x00, 'I'),
(6, 'IMG', 0x00, 'I'),
(7, 'IMG', 0x00, 'I'),
(8, 'IMG', 0x00, 'I'),
(9, 'IMG', 0x00, 'I'),
(10, 'IMG', 0x00, 'I'),
(11, 'IMG', 0x00, 'I'),
(12, 'IMG', 0x00, 'I'),
(13, 'IMG', 0x00, 'I'),
(14, 'IMG', 0x00, 'I'),
(15, 'IMG', 0x00, 'I'),
(16, 'IMG', 0x00, 'I'),
(17, 'IMG', 0x00, 'I'),
(18, 'IMG', 0x00, 'I'),
(19, 'IMG', 0x00, 'I'),
(20, 'IMG', 0x00, 'I'),
(21, 'IMG', 0x00, 'I'),
(22, 'IMG', 0x00, 'I'),
(23, 'IMG', 0x00, 'I'),
(24, 'IMG', 0x00, 'I'),
(25, 'IMG', 0x00, 'I'),
(1, 'VID', 0x00, 'V'),
(2, 'VID', 0x00, 'V'),
(3, 'VID', 0x00, 'V'),
(4, 'VID', 0x00, 'V'),
(5, 'VID', 0x00, 'V');

INSERT INTO T_SGP_PEDIDO (id_funcionario, id_pagamento, id_cliente, id_cupom, dt_pedido, nm_cliente, st_pedido) VALUES
-- Últimos 7 dias (1-30)
(NULL,1,1,1,'2025-11-26 10:00:00', NULL, 'P'),
(NULL,2,2,2,'2025-11-26 10:05:00', NULL, 'E'),
(2,1,1,NULL,'2025-11-26 10:10:00', NULL, 'C'),
(2,2,2,1,'2025-11-27 11:00:00', NULL, 'P'),
(NULL,1,1,2,'2025-11-27 11:05:00', NULL, 'E'),
(NULL,2,2,NULL,'2025-11-27 11:10:00', NULL, 'C'),
(2,1,1,1,'2025-11-28 12:00:00', NULL, 'P'),
(2,2,2,2,'2025-11-28 12:05:00', NULL, 'E'),
(2,1,1,NULL,'2025-11-28 12:10:00', NULL, 'C'),
(2,2,2,1,'2025-11-29 13:00:00', NULL, 'P'),
(2,1,1,2,'2025-11-29 13:05:00', NULL, 'E'),
(2,2,2,NULL,'2025-11-29 13:10:00', NULL, 'C'),
(2,1,1,1,'2025-11-30 14:00:00', NULL, 'P'),
(2,2,2,2,'2025-11-30 14:05:00', NULL, 'E'),
(NULL,1,1,NULL,'2025-11-30 14:10:00', NULL, 'C'),
(NULL,2,2,1,'2025-12-01 15:00:00', NULL, 'P'),
(NULL,1,1,2,'2025-12-01 15:05:00', NULL, 'E'),
(2,2,2,NULL,'2025-12-01 15:10:00', NULL, 'C'),
(2,1,1,1,'2025-12-02 09:00:00', NULL, 'P'),
(2,2,2,2,'2025-12-02 09:05:00', NULL, 'E'),
(2,1,1,NULL,'2025-12-02 09:10:00', NULL, 'C'),
(NULL,2,2,1,'2025-12-02 09:15:00', NULL, 'P'),
(NULL,1,1,2,'2025-12-02 09:20:00', NULL, 'E'),
(NULL,2,2,NULL,'2025-12-02 09:25:00', NULL, 'C'),
(NULL,1,1,1,'2025-12-02 09:30:00', NULL, 'P'),
(NULL,2,2,2,'2025-12-02 09:35:00', NULL, 'E'),
(2,1,1,NULL,'2025-12-02 09:40:00', NULL, 'C'),
(NULL,2,2,1,'2025-12-02 09:45:00', NULL, 'P'),
(NULL,1,1,2,'2025-12-02 09:50:00', NULL, 'E'),
(NULL,2,2,NULL,'2025-12-02 09:55:00', NULL, 'C'),

-- Últimos 30 dias (31-65)
(2,1,1,1,'2025-11-03 10:00:00', NULL, 'P'),
(2,2,2,2,'2025-11-04 10:10:00', NULL, 'E'),
(2,1,1,NULL,'2025-11-05 10:20:00', NULL, 'C'),
(2,2,2,1,'2025-11-06 10:30:00', NULL, 'P'),
(NULL,1,1,2,'2025-11-07 10:40:00', NULL, 'E'),
(NULL,2,2,NULL,'2025-11-08 10:50:00', NULL, 'C'),
(NULL,1,1,1,'2025-11-09 11:00:00', NULL, 'P'),
(2,2,2,2,'2025-11-10 11:10:00', NULL, 'E'),
(2,1,1,NULL,'2025-11-11 11:20:00', NULL, 'C'),
(2,2,2,1,'2025-11-12 11:30:00', NULL, 'P'),
(NULL,1,1,2,'2025-11-13 11:40:00', NULL, 'E'),
(NULL,2,2,NULL,'2025-11-14 11:50:00', NULL, 'C'),
(NULL,1,1,1,'2025-11-15 12:00:00', NULL, 'P'),
(NULL,2,2,2,'2025-11-16 12:10:00', NULL, 'E'),
(NULL,1,1,NULL,'2025-11-17 12:20:00', NULL, 'C'),
(NULL,2,2,1,'2025-11-18 12:30:00', NULL, 'P'),
(2,1,1,2,'2025-11-19 12:40:00', NULL, 'E'),
(2,2,2,NULL,'2025-11-20 12:50:00', NULL, 'C'),
(2,1,1,1,'2025-11-21 13:00:00', NULL, 'P'),
(2,2,2,2,'2025-11-22 13:10:00', NULL, 'E'),
(2,1,1,NULL,'2025-11-23 13:20:00', NULL, 'C'),
(2,2,2,1,'2025-11-24 13:30:00', NULL, 'P'),
(2,1,1,2,'2025-11-25 13:40:00', NULL, 'E'),
(2,2,2,NULL,'2025-11-26 13:50:00', NULL, 'C'),
(2,1,1,1,'2025-11-27 14:00:00', NULL, 'P'),
(2,2,2,2,'2025-11-28 14:10:00', NULL, 'E'),
(2,1,1,NULL,'2025-11-29 14:20:00', NULL, 'C'),
(2,2,2,1,'2025-11-30 14:30:00', NULL, 'P'),
(2,1,1,2,'2025-12-01 14:40:00', NULL, 'E'),
(2,2,2,NULL,'2025-12-02 14:50:00', NULL, 'C'),

-- Últimos 365 dias (66-100)
(2,1,1,1,'2024-12-03 09:00:00', NULL, 'P'),
(2,2,2,2,'2025-01-10 10:10:00', NULL, 'E'),
(2,1,1,NULL,'2025-02-15 11:20:00', NULL, 'C'),
(2,2,2,1,'2025-03-20 12:30:00', NULL, 'P'),
(NULL,1,1,2,'2025-04-25 13:40:00', NULL, 'E'),
(NULL,2,2,NULL,'2025-05-30 14:50:00', NULL, 'C'),
(NULL,1,1,1,'2025-06-05 15:00:00', NULL, 'P'),
(NULL,2,2,2,'2025-07-10 16:10:00', NULL, 'E'),
(NULL,1,1,NULL,'2025-08-15 17:20:00', NULL, 'C'),
(NULL,2,2,1,'2025-09-20 18:30:00', NULL, 'P'),
(NULL,1,1,2,'2025-10-25 19:40:00', NULL, 'E'),
(2,2,2,NULL,'2025-11-30 20:50:00', NULL, 'C'),
(2,1,1,1,'2025-12-01 08:00:00', NULL, 'P'),
(2,2,2,2,'2025-12-02 08:10:00', NULL, 'E'),
(2,1,1,NULL,'2025-12-02 08:20:00', NULL, 'C'),
(2,2,2,1,'2025-12-02 08:30:00', NULL, 'P'),
(2,1,1,2,'2025-12-02 08:40:00', NULL, 'E'),
(2,2,2,NULL,'2025-12-02 08:50:00', NULL, 'C'),
(2,1,1,1,'2025-01-01 09:00:00', NULL, 'P'),
(2,2,2,2,'2025-02-01 09:10:00', NULL, 'E'),
(2,1,1,NULL,'2025-03-01 09:20:00', NULL, 'C'),
(2,2,2,1,'2025-04-01 09:30:00', NULL, 'P'),
(2,1,1,2,'2025-05-01 09:40:00', NULL, 'E'),
(NULL,2,2,NULL,'2025-06-01 09:50:00', NULL, 'C'),
(NULL,1,1,1,'2025-07-01 10:00:00', NULL, 'P'),
(2,2,2,2,'2025-08-01 10:10:00', NULL, 'E'),
(2,1,1,NULL,'2025-09-01 10:20:00', NULL, 'C'),
(NULL,2,2,1,'2025-10-01 10:30:00', NULL, 'P'),
(2,1,1,2,'2025-11-01 10:40:00', NULL, 'E'),
(NULL,2,2,NULL,'2025-11-15 10:50:00', NULL, 'C'),
(NULL,1,1,1,'2025-12-01 11:00:00', NULL, 'P'),
(NULL,2,2,2,'2025-12-02 11:10:00', NULL, 'E'),
(NULL,1,1,NULL,'2025-12-02 11:20:00', NULL, 'C'),
(NULL,2,2,1,'2025-12-02 11:30:00', NULL, 'P');

INSERT INTO T_ITEM_PEDIDO (id_item, id_pedido, nr_quantidade) VALUES
-- Pedido 1
(1,1,1),(5,1,2),(9,1,1),
-- Pedido 2
(3,2,1),(7,2,1),(14,2,2),
-- Pedido 3
(2,3,1),(11,3,2),(6,3,1),
-- Pedido 4
(4,4,1),(8,4,1),(12,4,1),
-- Pedido 5
(1,5,1),(10,5,2),(15,5,1),
-- Pedido 6
(6,6,1),(3,6,1),(9,6,1),
-- Pedido 7
(2,7,2),(4,7,1),(11,7,1),
-- Pedido 8
(5,8,1),(7,8,2),(13,8,1),
-- Pedido 9
(3,9,1),(6,9,1),(12,9,2),
-- Pedido 10
(1,10,1),(8,10,1),(14,10,1),
-- Pedido 11
(7,11,1),(9,11,2),(5,11,1),
-- Pedido 12
(4,12,1),(12,12,1),(15,12,1),
-- Pedido 13
(6,13,1),(8,13,1),(10,13,2),
-- Pedido 14
(2,14,1),(5,14,1),(11,14,1),
-- Pedido 15
(1,15,1),(7,15,1),(13,15,1),
-- Pedido 16
(3,16,2),(9,16,1),(14,16,1),
-- Pedido 17
(4,17,1),(6,17,1),(12,17,1),
-- Pedido 18
(5,18,1),(11,18,2),(15,18,1),
-- Pedido 19
(1,19,1),(3,19,1),(7,19,1),
-- Pedido 20
(8,20,1),(10,20,1),(14,20,1),
-- Pedido 21
(2,21,1),(4,21,2),(9,21,1),
-- Pedido 22
(5,22,1),(7,22,2),(12,22,1),
-- Pedido 23
(3,23,1),(6,23,1),(11,23,1),
-- Pedido 24
(1,24,1),(8,24,1),(15,24,2),
-- Pedido 25
(7,25,1),(9,25,1),(13,25,1),
-- Pedido 26
(4,26,2),(10,26,1),(14,26,1),
-- Pedido 27
(2,27,1),(6,27,2),(12,27,1),
-- Pedido 28
(3,28,1),(11,28,1),(15,28,1),
-- Pedido 29
(1,29,1),(5,29,2),(7,29,1),
-- Pedido 30
(8,30,1),(10,30,1),(13,30,1),
-- Pedido 31
(6,31,1),(9,31,2),(14,31,1),
-- Pedido 32
(2,32,1),(4,32,1),(11,32,1),
-- Pedido 33
(3,33,1),(7,33,1),(12,33,1),
-- Pedido 34
(1,34,1),(5,34,1),(10,34,2),
-- Pedido 35
(6,35,1),(8,35,2),(13,35,1),
-- Pedido 36
(3,36,1),(9,36,1),(15,36,1),
-- Pedido 37
(2,37,2),(4,37,1),(12,37,1),
-- Pedido 38
(5,38,1),(7,38,1),(11,38,2),
-- Pedido 39
(1,39,1),(3,39,1),(9,39,2),
-- Pedido 40
(4,40,1),(8,40,1),(14,40,1),
-- Pedido 41
(2,41,1),(6,41,1),(10,41,2),
-- Pedido 42
(3,42,1),(11,42,1),(15,42,1),
-- Pedido 43
(1,43,1),(5,43,2),(7,43,1),
-- Pedido 44
(6,44,1),(9,44,1),(13,44,1),
-- Pedido 45
(4,45,1),(8,45,1),(12,45,2),
-- Pedido 46
(3,46,1),(10,46,2),(14,46,1),
-- Pedido 47
(2,47,1),(6,47,1),(11,47,1),
-- Pedido 48
(1,48,1),(7,48,1),(15,48,1),
-- Pedido 49
(3,49,1),(9,49,1),(12,49,1),
-- Pedido 50
(4,50,1),(8,50,2),(13,50,1),
-- Pedido 51
(4, 51, 1),
(10, 51, 2),
(7, 51, 1),

-- Pedido 52
(2, 52, 1),
(13, 52, 1),
(6, 52, 1),

-- Pedido 53
(9, 53, 1),
(1, 53, 1),
(14, 53, 1),

-- Pedido 54
(3, 54, 1),
(12, 54, 1),
(5, 54, 2),

-- Pedido 55
(11, 55, 2),
(4, 55, 1),
(8, 55, 1),

-- Pedido 56
(7, 56, 1),
(15, 56, 1),
(10, 56, 1),

-- Pedido 57
(6, 57, 1),
(2, 57, 1),
(9, 57, 1),

-- Pedido 58
(12, 58, 1),
(14, 58, 1),
(1, 58, 1),

-- Pedido 59
(3, 59, 1),
(7, 59, 2),
(5, 59, 1),

-- Pedido 60
(10, 60, 1),
(6, 60, 1),
(13, 60, 1),

-- Pedido 61
(1, 61, 1),
(11, 61, 2),
(4, 61, 1),

-- Pedido 62
(9, 62, 1),
(3, 62, 1),
(15, 62, 1),

-- Pedido 63
(7, 63, 2),
(2, 63, 1),
(12, 63, 1),

-- Pedido 64
(14, 64, 1),
(5, 64, 1),
(9, 64, 1),

-- Pedido 65
(13, 65, 1),
(10, 65, 1),
(1, 65, 1),

-- Pedido 66
(4, 66, 1),
(7, 66, 1),
(11, 66, 2),

-- Pedido 67
(15, 67, 1),
(6, 67, 1),
(3, 67, 1),

-- Pedido 68
(12, 68, 1),
(14, 68, 1),
(9, 68, 1),

-- Pedido 69
(5, 69, 1),
(2, 69, 1),
(13, 69, 1),

-- Pedido 70
(1, 70, 1),
(10, 70, 1),
(7, 70, 2),

-- Pedido 71
(9, 71, 1),
(4, 71, 1),
(11, 71, 2),

-- Pedido 72
(6, 72, 1),
(15, 72, 1),
(3, 72, 1),

-- Pedido 73
(12, 73, 1),
(2, 73, 1),
(5, 73, 1),

-- Pedido 74
(14, 74, 1),
(8, 74, 1),
(10, 74, 1),

-- Pedido 75
(1, 75, 1),
(13, 75, 1),
(7, 75, 2),

-- Pedido 76
(6, 76, 1),
(3, 76, 1),
(9, 76, 1),

-- Pedido 77
(4, 77, 1),
(11, 77, 2),
(14, 77, 1),

-- Pedido 78
(5, 78, 1),
(7, 78, 2),
(12, 78, 1),

-- Pedido 79
(2, 79, 1),
(10, 79, 1),
(8, 79, 1),

-- Pedido 80
(1, 80, 1),
(6, 80, 1),
(13, 80, 1),

-- Pedido 81
(3, 81, 1),
(9, 81, 1),
(15, 81, 1),

-- Pedido 82
(7, 82, 2),
(4, 82, 1),
(11, 82, 2),

-- Pedido 83
(5, 83, 1),
(14, 83, 1),
(12, 83, 1),

-- Pedido 84
(1, 84, 1),
(10, 84, 1),
(8, 84, 1),

-- Pedido 85
(9, 85, 1),
(6, 85, 1),
(3, 85, 1),

-- Pedido 86
(7, 86, 2),
(12, 86, 1),
(14, 86, 1),

-- Pedido 87
(5, 87, 1),
(11, 87, 2),
(2, 87, 1),

-- Pedido 88
(10, 88, 1),
(8, 88, 1),
(4, 88, 1),

-- Pedido 89
(3, 89, 1),
(15, 89, 1),
(7, 89, 2),

-- Pedido 90
(6, 90, 1),
(9, 90, 1),
(1, 90, 1),

-- Pedido 91
(13, 91, 1),
(11, 91, 2),
(4, 91, 1);

INSERT INTO T_CUPOM_CLIENTE (id_cliente, id_cupom) VALUES
(3, 1),
(7, 2),
(12, 3),
(5, 4),
(9, 5),
(14, 6),
(1, 7),
(10, 8),
(8, 9),
(4, 10);