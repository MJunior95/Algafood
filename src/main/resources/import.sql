insert into cozinha (id, nome) values (1,'Tailandesa');
insert into cozinha (id, nome) values (2,'Indiana');
insert into cozinha (id, nome) values (3,'Brasileira');
insert into cozinha (id, nome) values (4,'Japonesa');

insert into restaurante (nome, taxa_frete, cozinha_id)  values ('Thai Gourmet', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 8,2);

insert into estado (id, nome) values (1, 'Sao Paulo');
insert into estado (id, nome) values (2, 'Rio Janeiro');

insert into cidade (nome, estado_id) values ('Barueri', 1);
insert into cidade (nome, estado_id) values ('Jandira', 1);

insert into forma_pagamento (descricao) values ('Credito');
insert into forma_pagamento (descricao) values ('Debito');
insert into forma_pagamento (descricao) values ('Dinheiro');

insert into permissao (descricao, nome) values ('nao', 'sei');
insert into permissao (descricao, nome) values ('sei', 'nao');
