# DAO JDBC Demo

Projeto desenvolvido em **Java puro com JDBC**, com o objetivo de **consolidar os conceitos do padrão DAO (Data Access Object)**, separação de responsabilidades e acesso direto a banco de dados relacional.

Este projeto faz parte do meu processo de aprendizado em backend e persistência de dados, focando em entender **o que acontece por baixo dos frameworks**, antes de abstrações como JPA ou Hibernate.

---

## 🎯 Objetivo do projeto

- Entender o funcionamento do **JDBC**
- Aplicar o padrão **DAO**
- Separar regras de negócio do acesso a dados
- Trabalhar diretamente com **SQL**
- Consolidar conceitos de:
  - Conexão com banco
  - PreparedStatement
  - ResultSet
  - CRUD completo
  - Tratamento de exceções
  - Camada de persistência desacoplada

---

## 🧱 Arquitetura utilizada

O projeto segue uma estrutura simples, porém bem definida:

- **Model**
  - Representa as entidades do domínio
- **DAO**
  - Interfaces que definem as operações de acesso a dados
- **DAO Implementations**
  - Implementações concretas usando JDBC
- **DB**
  - Classe utilitária para gerenciamento de conexões
- **Application**
  - Classe principal para testes e execução

Essa separação facilita manutenção, testes e evolução do código.

---

## 🗄️ Banco de dados

- Banco de dados relacional com MySQL
- Acesso feito diretamente via JDBC
- SQL escrito manualmente (sem ORM)

Exemplo de operações implementadas:
- `insert`
- `update`
- `delete`
- `findById`
- `findAll`

---

## 🔌 Conexão com o banco

As configurações de conexão ficam centralizadas em um arquivo de propriedades, permitindo fácil alteração de ambiente.

Exemplo:

```properties
dburl=jdbc:mysql://localhost:3306/your_database
user=your_user
password=your_password
