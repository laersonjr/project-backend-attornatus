/* Inserir dados na tabela pessoa */
insert into pessoa(nome, data_de_nascimento) values('Laerson Castro', '1989-01-20');
insert into pessoa(nome, data_de_nascimento) values('Lucas Silva', '1990-02-21');
insert into pessoa(nome, data_de_nascimento) values('Pedro Dantas', '1991-03-22');
insert into pessoa(nome, data_de_nascimento) values('Alice Castro', '1992-04-23');
insert into pessoa(nome, data_de_nascimento) values('Helena Araujo', '1993-05-24');
insert into pessoa(nome, data_de_nascimento) values('Natalia Lima', '1994-06-25');
insert into pessoa(nome, data_de_nascimento) values('Carla Soares', '1995-07-26');
insert into pessoa(nome, data_de_nascimento) values('Luana Silva', '1996-08-27');
insert into pessoa(nome, data_de_nascimento) values('Bruno Dantas', '1997-09-28');
insert into pessoa(nome, data_de_nascimento) values('Oliver Araujo', '1998-10-29');

/* Inserir dados na tabela endereco */
insert into endereco(cep, logradouro, numero, cidade, principal, pessoa_id) values ('86220000', 'Rua Barão de Vitória', '100', 'Diadema', true, 1);
insert into endereco(cep, logradouro, numero, cidade, pessoa_id) values ('79002290', 'Rua da Imprensa', '110', 'Campo Grande', 1);
insert into endereco(cep, logradouro, numero, cidade, principal, pessoa_id) values ('04545005', 'Rua das Fiandeiras', '100', 'São Paulo', true, 2);
insert into endereco(cep, logradouro, numero, cidade, pessoa_id) values ('13216000', 'Avenida São João', '500', 'Jundi', 2);
insert into endereco(cep, logradouro, numero, cidade, principal, pessoa_id) values ('30120060', 'Rua dos Carijós', '42', 'Belo Horizonte', true, 3);
insert into endereco(cep, logradouro, numero, cidade, principal, pessoa_id) values ('16015244', 'Rua Cristiano Olsen', '14', 'Aracatuba', true, 4);
insert into endereco(cep, logradouro, numero, cidade, principal, pessoa_id) values ('29946490', 'Avenida Esbertalina Barbosa Damiani', '4545', 'São Mateus', true, 5);
insert into endereco(cep, logradouro, numero, cidade, principal, pessoa_id) values ('66055260', 'Avenida Governador José Malcher', '1549', 'Belém', true, 6);
insert into endereco(cep, logradouro, numero, cidade, principal, pessoa_id) values ('13216000', 'Avenida São João', '1675', 'Jundiaí', true, 7);
insert into endereco(cep, logradouro, numero, cidade, pessoa_id) values ('20040002', 'Avenida Rio Branco', '100', 'Rio de Janeiro', 7);
insert into endereco(cep, logradouro, numero, cidade, principal, pessoa_id) values ('04144070', 'Rua Pereira Estéfano', '48', 'São Paulo', true, 8);
insert into endereco(cep, logradouro, numero, cidade, principal, pessoa_id) values ('68700216', 'Travessa Antônio Ferreira', '945', 'Capanema', true, 9);
insert into endereco(cep, logradouro, numero, cidade, principal, pessoa_id) values ('76900032', 'Travessa da CDL', '12', 'Ji-Paraná', true, 10);
insert into endereco(cep, logradouro, numero, cidade, pessoa_id) values ('29946490', 'Avenida Esbertalina Barbosa Damiani', '79', 'São Mateus', 9);
insert into endereco(cep, logradouro, numero, cidade, pessoa_id) values ('96204040', 'Avenida Almirante Maximiano Fonseca', '49', 'Rio Grande', 8);
insert into endereco(cep, logradouro, numero, cidade, pessoa_id) values ('71020631', 'QE 11 Área Especial C', '100', 'Brasília', 10);

update endereco set principal = false where principal is null;

