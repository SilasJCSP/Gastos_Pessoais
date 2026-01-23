# Sistema de Controle de Gastos Pessoais

Sistema desenvolvido em Spring Boot para gerenciamento de gastos pessoais, permitindo o cadastro de despesas, categorização e geração de relatórios.

## Funcionalidades

- Cadastro de categorias de gastos
- Registro de lançamentos financeiros (crédito/débito)
- Consulta de lançamentos por período
- Relatórios de gastos por categoria
- Totalizadores mensais
- Interface RESTful para integração
- Exportação de dados para Excel e PDF

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (banco em memória)
- Maven
- Lombok
- MapStruct (para mapeamento de DTOs)
- Bean Validation
- Apache POI (para Excel)
- OpenPDF (para PDF)

## Como Executar

1. Certifique-se de ter o Java 17 e Maven instalados
2. Clone o repositório
3. Navegue até o diretório do projeto
4. Execute o comando: `mvn spring-boot:run`
5. Acesse a aplicação em: http://localhost:8080/api

## Acesso ao Banco de Dados H2

- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:gastosdb
- Usuário: sa
- Senha: (deixe em branco)

## Endpoints da API

### Categorias
- `GET /api/categorias` - Lista todas as categorias
- `GET /api/categorias/{id}` - Busca uma categoria por ID
- `POST /api/categorias` - Cria uma nova categoria
- `PUT /api/categorias/{id}` - Atualiza uma categoria existente
- `DELETE /api/categorias/{id}` - Remove uma categoria
- `GET /api/categorias/export/excel` - Exporta categorias para Excel
- `GET /api/categorias/export/pdf` - Exporta categorias para PDF

### Lançamentos
- `GET /api/lancamentos` - Lista todos os lançamentos (com paginação)
- `GET /api/lancamentos/{id}` - Busca um lançamento por ID
- `POST /api/lancamentos` - Cria um novo lançamento
- `PUT /api/lancamentos/{id}` - Atualiza um lançamento existente
- `DELETE /api/lancamentos/{id}` - Remove um lançamento
- `GET /api/lancamentos/mes/{mesReferencia}` - Lista lançamentos por mês (formato: MM/yyyy)
- `GET /api/lancamentos/resumo/mes/{mesReferencia}` - Retorna resumo mensal com totais por categoria

## Estrutura do Projeto

```
src/main/java/com/gastos/pessoais/
├── config/           # Configurações da aplicação
├── controller/       # Controladores REST
├── dto/              # Objetos de transferência de dados
├── exception/        # Classes de exceção
├── model/            # Entidades JPA
├── repository/       # Repositórios de dados
├── service/          # Lógica de negócio
└── PessoaisApplication.java  # Classe principal
```

## Exemplos de Uso

### Criar uma categoria
```http
POST /api/categorias
Content-Type: application/json

{
  "nome": "Alimentação",
  "descricao": "Gastos com alimentação"
}
```

### Criar um lançamento
```http
POST /api/lancamentos
Content-Type: application/json

{
  "data": "2025-10-23",
  "descricao": "Supermercado",
  "categoriaId": 1,
  "valor": 350.50,
  "tipo": "DEBITO",
  "formaPagamento": "Cartão de Crédito",
  "mesReferencia": "10/2025"
}
```

### Obter resumo mensal
```http
GET /api/lancamentos/resumo/mes/10/2025
```

## Próximos Passos

- [ ] Implementar autenticação e autorização
- [ ] Adicionar upload de comprovantes
- [x] Implementar exportação para Excel/PDF
- [ ] Criar interface web com Thymeleaf/React
- [ ] Adicionar gráficos para visualização de dados

## Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## Nota sobre exportações

O content-type para arquivos .xlsx foi ajustado para `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet`. Para arquivos grandes, considere gerar por streaming ou salvar em storage temporário para evitar OOM.
