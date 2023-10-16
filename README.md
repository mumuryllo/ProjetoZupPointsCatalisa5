<h1 align="center">
🥳📊<br>ZupHall - Sistema de recompensa para funcionários
</h1>

## Sumário
1. [O que é o ZupHall?](#-o-que-é-o-zuphall)
2. [Tecnologias Utilizadas](#-tecnologias-utilizadas)
3. [Funcionalidades do ZupHall ](#-funcionalidades-do-zuphall)
4. [Tutorial de uso da ZupHall ](#-tutorial-de-uso-do-zuphall)
5. [Acessando a documentação da API](#-acessando-a-documentação-da-api)
6. [Acessando o banco de dados (H2) ](#-acessando-o-banco-de-dados-h2)
7. [Alterando o banco de dados para o Postgres](#-alterando-o-banco-de-dados-para-o-postgres)
8. [Melhorias para futuras atualizações](#-melhorias-para-futuras-atualizações)
9. [Autores](#-autor)

## 📚 O que é o Zuphall?

## 🖥️ Tecnologias Utilizadas

* `Java 11` - Linguagem de programação
* `Spring Boot (2.7.15)` - Framework para criação de aplicativos Java
* `Spring Boot Data JPA` - Facilita o acesso a bancos de dados relacionais.
* `Spring Boot Validation` - Biblioteca que ajuda na validação de entrada de dados em aplicativos Spring Boot.
* `Spring Boot Web` - Facilita o desenvolvimento de aplicativos da web usando o Spring Boot.
* `H2 Database (Runtime)` - Um banco de dados SQL leve e embutido que é executado em tempo de execução.
* `Lombok` - Uma biblioteca Java que ajuda a reduzir a verbosidade do código
* `Springdoc OpenAPI UI (1.7.0)` -  Uma ferramenta que gera automaticamente a documentação da API com base nas anotações do Spring.

---

## 🧩 Funcionalidades do ZupHall

📔 `Cadastro de colaboradores`:


---

## 📃 Tutorial de uso do ZupHall


### 📚 Acessando a documentação da API

A documentação completa da API está disponível através do Swagger UI. Você pode acessá-la através da seguinte URL: `http://localhost:8080/swagger-ui/index.html`

O Swagger UI fornece uma interface interativa que permite explorar e testar os endpoints da API, bem como visualizar detalhes sobre os parâmetros, respostas e exemplos de uso.

---

## 🏦 Acessando o banco de dados (H2)

1. Certifique-se de que sua aplicação Spring Boot esteja em execução. Se você não tiver iniciado a aplicação, faça isso executando o aplicativo Spring Boot.

2. Abra um navegador da web de sua escolha.

3. Acesse a URL do console do H2 no seguinte endereço:

`http://localhost:8080/h2-console`

4. Preencha os campos da página de login com as seguintes informações:

```
Driver Class: org.h2.Driver

JDBC URL: jdbc:h2:mem:stockz 

User Name: sa

Password: Deixe este campo em branco.
```

5. Clique no botão "Connect" para fazer login no console do H2.

**OBS.: Lembre-se de que o console do H2 só estará disponível enquanto sua aplicação Spring Boot estiver em execução.**

---

### 🏦 Alterando o banco de dados para o Postgres

1. Acesse o application.properties que está dentro da pasta /main/resources

2. Comente as configurações do H2 utilizando `#` na frente das linhas abaixo do escrito `### #### Configuração H2 ####`

3. Descomente as configurações do Postgres deletando os `#` na frente das linhas abaixo do escrito `### #### Configuração POSTGRES ####`

---

## 🚧 Melhorias para futuras atualizações

- Realizar cobertura de 100% dos testes em todas as classes

---

## 👨‍💻 Autor

Nome: João Cruz<br>Linkedin: https://www.linkedin.com/in/joaosilvacruz/
Nome: Muryllo
Nome: Márlen


---








