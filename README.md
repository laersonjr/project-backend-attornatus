<h1 align="center"> Avaliação Desenvolvedor Back-end Attornatus 🚀 </h1>

<h1 align="center">🎯 Sobre o projeto</h1>
O projeto é capaz de gerenciar pessoas realizando algumas validações. Uma pessoa deve conter os campos nome, data de nascimento, endereço (Logradouro, cep, numero e cidade). Se faz necessário que todas as respostas da api retornem no formato JSON e utilize o banco de dados H2.

<h1 align="center">📦 Desenvolvimento</h1>
Realizado com Java versão 17 junto com o framkework SpringBoot versão 3.0.1, gestor de dependência Maven. No banco de dados, conforme solicitado, foi utilizado o H2 e para persistir as informações no banco de dados foi utilizado do Spring Data JPA, que é um framkework que faz parte do conjunto de projetos do Spring Data, tendo como finalidade tornar a integração de aplicações do Spring com o JPA. Para evitar a repetição de código "clichê" foi utilizado o Lombok. Uso do ModelMapper para criar os DTO's de entreda e saída de dados que foram utilizados. Por fim, para realizar os testes, tive utilização do Junit.

<h1 align="center">🔨 Funcionalidades do projeto </h1>

- `Funcionalidade 1` `cadastrarPessoa`: Realizada o cadastro de uma nova pessoa no banco, recebendo nome e data de nascimento. Se faz necessário o preechimento correto dos campos. Após o cadastro, é informado o location do novo recurso criado através de URI.
Endpoint: /api/pessoas, método: POST.

- `Funcionalidade 2` `listarPessoas`: Lista todas as pessoas cadastradas, tendo a opção de passar por parâmetro nome da pessoa(pode ser apenas um trecho), page e size para definir a paginação, se necessário.
Endpoint: /api/pessoas, método: GET.
 
- `Funcionalidade 3` `buscarPessoaId`: Localiza a pessoa passando como parâmetro na URL o id. Endpoint: /api/pessoas/{idPessoa}, método: GET.

- `Funcionalidade 4` `atualizarPessoa`: Atualiza o cadastro de uma pessoa no banco, recebendo nome e data de nascimento, além disso se faz necessário informar o id da pessoa na URL.
EndPoint: /api/pessoas/{idPessoa}, método: PUT.

- `Funcionalidade 5` `excluirPessoa`: Exclui do banco de dados um recurso recebido na url. EndPoint: /api/pessoas/{idPessoa}, método: DELETE.

- `Funcionalidade 6` `cadastrarEnderecoPessoa`: Realizada o cadastro de um novo endereço no banco, recebendo alguns parametros como logradouro, cep, numero e cidade. Se faz necessário o preechimento correto de alguns campos. Após o cadastro, é informado o location do novo recurso criado através de URI.
Endpoint: /api/pessoas/{idPessoa}/endereco, método: POST.

- `Funcionalidade 7` `listarEnderecoDePessoa`: Lista todos os endereços cadastrados para pessoa. EndPoint: /api/pessoas/{idPessoa}/endereco, método: GET.

- `Funcionalidade 8` `atualizarEnderecoPrincipal`: Atualiza um endereço endereço recebido na URL, através do ID, como principal. EndPoint: /api/pessoas/{idPessoa}/endereco/{idEndereco}, método: PUT.
