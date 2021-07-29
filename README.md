# scala-challenge
Exercício para avaliação Scala



**Pré-requisitos do projeto**

                 
        -Docker e Docker-compose  (última versão)
        -Git (última versão)
___________________________________________________________________________________________________________________________________________________________________


**_Rodando a aplicação_**


Configurações do Projeto (comandos linux/use o proporcional ao seu S.O.)
- posicionar-se no diretório de preferência
- Executar comando: $git clone https://github.com/elishuc/scala-challenge.git
- acessar o diretório scala-challenge
- Executar comando: $docker-compose build && docker-compose up
- Após a instalação acessar o Swagger da aplicação rodando local url: http://localhost:8083/swagger-ui.html
- O Swagger pode ser utilizado para os testes conforme as operações
- Existem dois usuários criados com os ids 1 e 2
___________________________________________________________________________________________________________________________________________________________________

**Banco postgress**
- jdbc:postgresql://localhost:5432/scala_db?createDatabaseIfNotExist=true
- user:postgres
- password:postgres
_________________________________________________________________________________________________________________________________________
