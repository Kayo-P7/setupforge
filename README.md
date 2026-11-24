# SetupForge

Aplicação acadêmica para visualizar produtos de hardware, consultar componentes e montar uma configuração personalizada de computador.

## Tecnologias

| Módulo | Tecnologias |
|---|---|
| Frontend | React Native, Expo, Expo Router e TypeScript |
| Backend | Java, Spring Boot, Spring Data JPA e API REST |
| Persistência | PostgreSQL e Flyway |
| Infraestrutura | Render e GitHub |

## Arquitetura

O frontend e o backend são módulos independentes dentro deste repositório. O aplicativo mobile faz requisições HTTP aos endpoints REST do backend hospedado no Render. O backend utiliza PostgreSQL para persistir categorias, produtos e dados de montagem.

Para executar o frontend, não é necessário configurar um backend ou banco de dados local: o aplicativo já aponta para a API hospedada. A execução local do backend é opcional.

## Links

- **Repositório:** [github.com/Kayo-P7/setupforge](https://github.com/Kayo-P7/setupforge)
- **Backend:** [setupforge-backend.onrender.com](https://setupforge-backend.onrender.com)
- **Health check:** [Estado da API](https://setupforge-backend.onrender.com/actuator/health)
- **Swagger UI:** [Documentação interativa da API](https://setupforge-backend.onrender.com/swagger-ui/index.html)
- **OpenAPI JSON:** [Especificação OpenAPI](https://setupforge-backend.onrender.com/v3/api-docs)

A URL base do backend identifica o serviço, mas não serve uma página inicial e pode responder HTTP 404. Use o health check ou os endpoints/documentação acima para verificar a API.

### Endpoints principais

| Método | Caminho | Uso |
|---|---|---|
| `GET` | `/actuator/health` | Estado de disponibilidade do serviço |
| `GET` | `/categorias` | Categorias disponíveis no catálogo |
| `GET` | `/produto` | Produtos do catálogo |

## Funcionalidades

- Página inicial com banner, categorias e entrada para a montagem de computador.
- Busca de produtos por nome, marca, categoria ou descrição, usando os dados do catálogo da API.
- Categorias e catálogo carregados da API.
- Cards e detalhes de produtos com informações e imagens retornadas pela API.
- Consulta de CEP pela API ViaCEP, com exibição do endereço quando encontrado.
- Confirmação visual de compra demonstrativa. Não há pagamento, criação de pedido real ou integração com transportadora.
- Montagem personalizada de computadores com seleção, substituição e remoção de componentes.
- Resumo dos componentes escolhidos, categorias pendentes e cálculo automático do valor total.

A montagem não faz validação automática de compatibilidade entre componentes. Ela usa as categorias e os produtos do catálogo.

## Executar o frontend com Expo

### Pré-requisitos

- Node.js e npm.
- Expo Go instalado no dispositivo físico para testar pelo QR Code.

### Instalação e inicialização

```bash
git clone https://github.com/Kayo-P7/setupforge.git
cd setupforge/setupforge-frontend
npm install
npx expo start --tunnel
```

Quando o Expo exibir o QR Code, escaneie-o com o Expo Go. Em iPhone, também é possível usar a câmera para abrir o projeto no Expo Go. O computador e o dispositivo precisam ter acesso à internet.

### Navegador

```bash
npx expo start --web
```

**CORS para Web:** o código local do backend permite `http://localhost:8081` e `http://localhost:8082`, além de origens explícitas configuradas em `CORS_ALLOWED_ORIGINS`. Na última verificação pública, o Render ainda permitia `localhost:8082` e rejeitava `localhost:8081`; a alteração local foi enviada ao GitHub, mas o novo comportamento ainda não está ativo no serviço publicado. A execução mobile via Expo Go consome a API diretamente e não depende do CORS do navegador.

## Backend (opcional, execução local)

O backend já está hospedado no Render e é consumido diretamente pelo frontend. Para executá-lo localmente, são necessários Java 17, Maven Wrapper e uma instância PostgreSQL local:

1. Inicie o PostgreSQL e crie um banco local para o projeto.
2. Na pasta `setupforge-backend`, configure as variáveis de ambiente `DB_URL`, `DB_USERNAME` e `DB_PASSWORD`. Não use credenciais de produção nem as inclua no repositório.
3. Para acesso Web local, configure `CORS_ALLOWED_ORIGINS` como uma lista separada por vírgulas, por exemplo `http://localhost:8081,http://localhost:8082`. Origens adicionais devem ser URLs completas e explícitas, sem `*`.
4. Inicie a aplicação:

```bash
cd setupforge-backend
export DB_URL="jdbc:postgresql://localhost:5432/setupforge"
export DB_USERNAME="<usuario-local>"
export DB_PASSWORD="<senha-local>"
export CORS_ALLOWED_ORIGINS="http://localhost:8081,http://localhost:8082"
./mvnw spring-boot:run
```

O Flyway está habilitado e executa as migrations ao iniciar; o Hibernate valida o schema existente. A configuração de produção e o schema real do PostgreSQL no Render ainda precisam de conferência direta antes de uma publicação do backend.

## Testes e verificações

Resultados confirmados durante a revisão do projeto:

- `npm run lint` — aprovado.
- `npx tsc --noEmit` — aprovado.
- `npx expo export --platform all` — aprovado para Android, iOS e Web.
- `./mvnw clean verify` — `BUILD SUCCESS`; 10 testes passaram. Os testes usam H2/MockMvc e **não** conectam ao PostgreSQL de produção.
- Os endpoints públicos `/actuator/health`, `/categorias` e `/produto` responderam HTTP 200; a API retornou 14 categorias e 37 produtos.
- As 37 imagens referenciadas pelos produtos retornados pela API foram acessadas sem erro.
- A rotação horizontal foi testada em um dispositivo físico.

## Limitações conhecidas

- O schema real e o histórico Flyway do PostgreSQL de produção ainda não foram auditados diretamente. Os testes com H2 e PostgreSQL temporário não comprovam a compatibilidade com a base do Render.
- O CORS local aceita as duas portas Expo e origens adicionais explícitas. A publicação e o teste de preflight no Render ainda são necessários para confirmar a mudança no serviço hospedado.
- O relatório Lighthouse existente refere-se à versão Web local. Ele não representa uma medição da versão publicada nem do desempenho mobile nativo.
- A compra é apenas demonstrativa e não efetua pagamentos ou cria pedidos reais.
- A rotação horizontal foi testada em um dispositivo físico; não há afirmação de testes em outros dispositivos.

Os relatórios de auditoria e entrega estão em [`docs/relatorios/`](docs/relatorios/).
