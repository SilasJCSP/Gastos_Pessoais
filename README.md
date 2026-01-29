<div align="center">

# 💰 Sistema de Controle de Gastos Pessoais

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](LICENSE)
[![CI](https://img.shields.io/badge/CI-GitHub%20Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)](https://github.com/SilasJCSP/Gastos_Pessoais/actions)

### 🚧 **Projeto em Desenvolvimento Ativo** 🚧

*API RESTful moderna para gerenciamento inteligente de finanças pessoais*

[Funcionalidades](#-funcionalidades) • [Tecnologias](#-tecnologias) • [Instalação](#-como-executar) • [API](#-endpoints-da-api) • [Roadmap](#-roadmap)

</div>

---

## 📋 Sobre o Projeto

Sistema desenvolvido em **Spring Boot** para controle completo de gastos pessoais, permitindo categorização, análise e geração de relatórios financeiros detalhados. Ideal para quem busca organizar suas finanças de forma profissional.

## ✨ Funcionalidades

### ✅ Implementadas
- ✔️ Cadastro e gerenciamento de categorias de gastos
- ✔️ Registro de lançamentos financeiros (crédito/débito)
- ✔️ Consulta de lançamentos por período (mês/ano)
- ✔️ Relatórios e totalizadores mensais por categoria
- ✔️ **Autenticação básica** (login com mock JWT)
- ✔️ Exportação de dados para **Excel (.xlsx)** e **PDF**
- ✔️ Interface RESTful documentada
- ✔️ Validação de dados com Bean Validation
- ✔️ Paginação de resultados
- ✔️ Testes unitários e de integração
- ✔️ CI/CD com GitHub Actions (Maven + JaCoCo)

### 🚧 Em Desenvolvimento
- 🔨 Autenticação JWT completa com Spring Security
- 🔨 Autorização baseada em roles (USER/ADMIN)
- 🔨 Testes de cobertura completa
- 🔨 Documentação Swagger/OpenAPI

## 🛠 Tecnologias

| Categoria | Tecnologias |
|-----------|-------------|
| **Core** | Java 17, Spring Boot 3.x, Maven |
| **Persistência** | Spring Data JPA, H2 Database (in-memory) |
| **Segurança** | Spring Security *(em implementação)* |
| **Utilitários** | Lombok, MapStruct, Bean Validation |
| **Exportação** | Apache POI (Excel), OpenPDF (PDF) |
| **Testes** | JUnit 5, AssertJ, Spring Boot Test |
| **CI/CD** | GitHub Actions, JaCoCo (cobertura) |

## 🚀 Como Executar

### Pré-requisitos
- ☕ Java 17 ou superior
- 📦 Maven 3.6+

### Passos

1. **Clone o repositório**
```bash
git clone https://github.com/SilasJCSP/Gastos_Pessoais.git
cd Gastos_Pessoais/pessoais
```

2. **Execute a aplicação**
```bash
mvn spring-boot:run
```

3. **Acesse a API**
```
http://localhost:8080/api
```

### 🗄️ Acesso ao Banco H2 Console

Durante o desenvolvimento, você pode acessar o console do banco de dados:

- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:gastosdb`
- **Usuário**: `sa`
- **Senha**: *(deixe em branco)*

## 📡 Endpoints da API

### 🔐 Autenticação

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "usuario@email.com",
  "senha": "senha123"
}
```

### 📂 Categorias

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/categorias` | Lista todas as categorias |
| `GET` | `/api/categorias/{id}` | Busca categoria por ID |
| `POST` | `/api/categorias` | Cria nova categoria |
| `PUT` | `/api/categorias/{id}` | Atualiza categoria |
| `DELETE` | `/api/categorias/{id}` | Remove categoria |
| `GET` | `/api/categorias/export/excel` | Exporta para Excel |
| `GET` | `/api/categorias/export/pdf` | Exporta para PDF |

### 💸 Lançamentos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/lancamentos` | Lista todos (paginado) |
| `GET` | `/api/lancamentos/{id}` | Busca por ID |
| `POST` | `/api/lancamentos` | Cria novo lançamento |
| `PUT` | `/api/lancamentos/{id}` | Atualiza lançamento |
| `DELETE` | `/api/lancamentos/{id}` | Remove lançamento |
| `GET` | `/api/lancamentos/mes/{ano}/{mes}` | Lista por mês/ano |
| `GET` | `/api/lancamentos/resumo/{ano}/{mes}` | Resumo mensal |

### 📝 Exemplos de Uso

<details>
<summary><b>Criar Categoria</b></summary>

```http
POST /api/categorias
Content-Type: application/json

{
  "nome": "Alimentação",
  "descricao": "Gastos com supermercado e restaurantes"
}
```
</details>

<details>
<summary><b>Criar Lançamento</b></summary>

```http
POST /api/lancamentos
Content-Type: application/json

{
  "data": "2026-01-29",
  "descricao": "Supermercado - Compras do mês",
  "categoriaId": 1,
  "valor": 450.75,
  "tipo": "DEBITO",
  "formaPagamento": "Cartão de Crédito"
}
```
</details>

<details>
<summary><b>Obter Resumo Mensal</b></summary>

```http
GET /api/lancamentos/resumo/2026/01
```

**Resposta:**
```json
{
  "totalReceitas": 5000.00,
  "totalDespesas": 3200.50,
  "saldo": 1799.50,
  "gastosPorCategoria": [
    {
      "categoria": "Alimentação",
      "total": 450.75
    }
  ]
}
```
</details>

## 📁 Estrutura do Projeto

```
pessoais/
├── src/main/java/com/gastos/pessoais/
│   ├── config/           # Configurações (CORS, Web)
│   ├── controller/       # Endpoints REST
│   ├── dto/              # Data Transfer Objects
│   ├── exception/        # Tratamento de exceções
│   ├── model/            # Entidades JPA
│   ├── repository/       # Repositórios Spring Data
│   ├── service/          # Regras de negócio
│   └── PessoaisApplication.java
├── src/test/java/        # Testes unitários e integração
└── .github/workflows/    # CI/CD GitHub Actions
```

## 🗺 Roadmap

### Versão 1.0 (Em andamento)
- [x] CRUD completo de categorias e lançamentos
- [x] Exportação Excel/PDF
- [x] Testes básicos
- [x] CI/CD configurado
- [ ] Autenticação JWT completa
- [ ] Documentação Swagger
- [ ] Cobertura de testes > 80%

### Versão 2.0 (Planejado)
- [ ] Upload de comprovantes/notas fiscais
- [ ] Dashboard web (React ou Thymeleaf)
- [ ] Gráficos e visualizações interativas
- [ ] Metas de gastos mensais
- [ ] Notificações de limites
- [ ] Suporte a múltiplas contas bancárias
- [ ] Importação de OFX/CSV

### Versão 3.0 (Futuro)
- [ ] Aplicativo mobile (React Native)
- [ ] Machine Learning para previsões
- [ ] Integração com Open Banking
- [ ] Relatórios avançados com BI

## 🧪 Testes

Execute os testes com:

```bash
mvn test
```

Gerar relatório de cobertura (JaCoCo):

```bash
mvn verify
# Relatório em: target/site/jacoco/index.html
```

## 🤝 Contribuindo

Contribuições são bem-vindas! Este projeto está em desenvolvimento ativo.

1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👤 Autor

**Silas JC**  
[![GitHub](https://img.shields.io/badge/GitHub-SilasJCSP-181717?style=flat&logo=github)](https://github.com/SilasJCSP)

---

<div align="center">

### ⭐ Se este projeto foi útil, considere dar uma estrela!

**Status do Projeto:** 🚧 Em Desenvolvimento Ativo  
**Última Atualização:** Janeiro 2026

</div>