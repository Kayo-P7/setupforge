# Auditoria de entrega acadêmica — SetupForge

**Data:** 24/11/2026
**Escopo:** frontend Expo/React Native, backend Spring Boot, persistência PostgreSQL, imagens e auditoria Lighthouse da versão web.

## Estado encontrado antes das alterações

Já existiam a Home com busca, banner e categorias; catálogo e detalhes de produto; consulta ViaCEP e confirmação demonstrativa; montagem de PC com oito categorias, Context API e total automático. O frontend já chamava os endpoints HTTP do backend. O backend usava entidades JPA e repositórios para categorias, produtos e montagens.

As lacunas verificadas foram:

- Hibernate estava configurado com `ddl-auto: update`, o Flyway estava desabilitado e não havia migrations versionadas.
- DTOs de escrita tinham validação incompleta e o handler global tratava somente algumas exceções de domínio.
- A configuração Expo restringia a orientação a portrait.
- As imagens eram exibidas com `Image` do React Native, sem tratamento uniforme de falha ou prioridade/cache explícitos.
- Não havia metadados web de título/descrição nem relatório Lighthouse.
- O CORS do backend permitia apenas `http://localhost:8082`; a Expo web servida no ambiente de análise usou `http://localhost:8081`.

## Alterações realizadas

### Persistência e backend

- A persistência continua em PostgreSQL: driver PostgreSQL, entidades JPA, repositórios Spring Data e serviços que consultam/salvam os dados. Nenhuma lista mock foi adicionada à aplicação.
- A configuração continua exigindo `DB_URL`, `DB_USERNAME` e `DB_PASSWORD`, sem valores padrão de credenciais.
- Foi adicionada a migration `V1__create_initial_schema.sql` para as tabelas de categorias, produtos, setups e produtos do setup. Ela usa criação condicional e não apaga dados.
- Flyway foi habilitado com baseline para bases preexistentes e o Hibernate passou a validar o schema, em vez de alterá-lo automaticamente. O log SQL e o `open-in-view` foram desabilitados.
- Foi alinhado o incremento da sequência de categoria ao allocation size do Hibernate.
- DTOs de categoria, produto e setup agora validam campos obrigatórios, limites e valores positivos. Erros de validação, corpo inválido, recurso ausente e conflito de integridade retornam respostas HTTP consistentes; exceções inesperadas são registradas e respondem 500 sem expor detalhes internos.
- O cadastro de produto retorna HTTP 201.
- O CORS mantém `http://localhost:8081` e `http://localhost:8082` permitidas, aceita origens web explícitas adicionais por `CORS_ALLOWED_ORIGINS` e rejeita wildcard/caminhos.
- O perfil de teste usa H2 isolado. Isso não altera a configuração de produção, que continua usando PostgreSQL.

**Verificação do PostgreSQL configurado:** as variáveis de banco estão configuradas localmente, mas a consulta read-only à base remota não conseguiu conectar a partir deste ambiente. Portanto, não foi possível confirmar a conectividade atual nem inspecionar os dados dessa instância. Não apliquei migration à base remota. Em compensação, apliquei a migration duas vezes a uma instância PostgreSQL 16 temporária e iniciei o JAR empacotado contra outra base PostgreSQL temporária: Flyway aplicou V1 e o Hibernate validou o schema com sucesso. A aplicação de produção executará a migration ao iniciar após a publicação do código.

**Verificação posterior do serviço publicado:** em 24/11/2026, `/actuator/health` respondeu HTTP 200 com `UP`, `/categorias` respondeu com 14 registros e `/produto` com 37. O endpoint de health não expõe o componente de banco, e a conexão SQL direta continuou inacessível a partir deste ambiente; assim, os dados foram confirmados pela API e pelo caminho JPA no código, mas não por consulta SQL direta.

### Frontend, carregamento e rotação

- A Home e os fluxos existentes foram preservados. A Home agora tem metadados web de título e descrição; indicadores de progresso têm nomes acessíveis.
- A busca da Home encaminha para uma tela de resultados que consulta `buscarProdutos()` e filtra nome, marca, categoria e descrição, normalizando maiúsculas, acentos e espaços. O retorno continua vindo integralmente do catálogo da API.
- `buscarProdutos()` valida que a resposta seja uma lista, mantém cache em memória por 60 segundos e compartilha uma requisição em andamento para evitar chamadas duplicadas entre telas.
- A listagem segue usando `FlatList`, que virtualiza os cards. Imagens usam `expo-image`, cache em disco, prioridade baixa nos cards e alta nos detalhes, indicador enquanto carregam e fallback em falha. O carregamento das imagens acompanha as linhas renderizadas pela lista.
- A resposta atual de produtos não oferece paginação; não foi criada paginação simulada.
- A grade dos produtos ajusta o número de colunas à largura disponível. A orientação Expo passou a `default`, permitindo portrait e landscape. As telas mantêm rolagem para acomodar conteúdo em telas menores.
- O usuário informou que já testou a rotação para landscape em um dispositivo físico e que funcionou. Esta revisão não repetiu esse teste e não afirma teste em outros dispositivos.

### Otimização de imagens

Foram analisados os 38 PNGs servidos por `src/main/resources/static/images`. Mantive nomes, diretórios, formato PNG, transparência e URLs consumidas pelo backend; redimensionei imagens grandes para até 1200 px e recompactei sem conversão para formatos incompatíveis com os caminhos existentes.

| Medida | Antes | Depois |
|---|---:|---:|
| Tamanho total | 24.624.022 bytes (24,62 MB) | 15.377.441 bytes (15,38 MB) |
| Redução |  | 9.246.581 bytes (37,6%) |
| Arquivos menores |  | 34 de 38 |

Os quatro arquivos em que a nova codificação não reduziu o tamanho permaneceram inalterados. Nenhum arquivo de imagem foi removido ou substituído por imagem genérica.

## Análise de performance web

- **Ferramenta:** Google Lighthouse CLI 13.6.0 com Google Chrome Headless 155.0.8059.84.
- **Data da coleta:** 24/11/2026 13:56 UTC.
- **Aplicação:** frontend Expo SDK 58.0.26, React Native 0.88.1; servidor local Expo iniciado com `--web --no-dev --minify` em `http://localhost:8081/`.
- **Modo:** auditoria Lighthouse com perfil mobile e throttling simulado. Esta medição é da versão web local; não representa desempenho nativo nem uma implantação web de produção.

| Categoria | Pontuação |
|---|---:|
| Performance | 83 |
| Acessibilidade | 100 |
| Boas práticas | 96 |
| SEO | 100 |

Métricas coletadas: FCP 1,1 s; LCP 1,1 s; Speed Index 2,7 s; TBT 650 ms; CLS 0; Time to Interactive 8,4 s.

Problemas e recomendações observados:

- O console registrou bloqueio CORS ao consultar o backend publicado desde `localhost:8081`. O código foi ajustado, mas o backend publicado não foi atualizado nesta tarefa; a pontuação de boas práticas reflete esse erro. Para web em produção, configure `CORS_ALLOWED_ORIGINS` no Render com a origem real do frontend e publique o backend atualizado.
- O servidor local respondeu ao documento inicial em cerca de 1,51 s. Meça novamente no host web de produção para avaliar a latência real.
- Lighthouse estimou cerca de 620 KiB de JavaScript e 14 KiB de CSS não utilizados na navegação inicial. Avalie divisão adicional de bundle e redução de dependências se a web for parte central da entrega.
- O servidor Metro local não expôs source maps válidos para alguns bundles e impediu BFCache por cabeçalhos `no-store`; reavalie esses itens em uma implantação web de produção.
- A nota de SEO/Acessibilidade alcançou 100 após adicionar metadados e nomes aos indicadores. Isso mede apenas as verificações Lighthouse da página inicial web.

**Nova medição publicada nesta revisão:** não realizada. Não foi localizada uma URL pública do frontend nas configurações/documentos do projeto; o `app.json` configura saída Expo Web server, mas isso não publica o site. Não inventei URL. As pontuações acima continuam sendo somente da coleta local.

## Verificação final de API, imagens e segurança (24/11/2026)

- `GET /actuator/health`, `GET /categorias` e `GET /produto` retornaram HTTP 200. Health respondeu `UP` (grupos liveness/readiness); categorias retornou 14 registros; produtos retornou 37.
- A documentação OpenAPI publicada também está disponível: `/swagger-ui/index.html`, `/swagger-ui.html` e `/v3/api-docs` retornaram HTTP 200 nesta checagem.
- Os 37 produtos têm URLs de imagem únicas: 37/37 responderam sem erro HTTP, e os 37 caminhos/basename correspondem a arquivos locais em `src/main/resources/static/images`. O PNG novo `placas-mae/image(20261121-173539).png` não é referenciado pelo retorno atual da API. Ele foi preservado.
- Foi comparada visualmente uma imagem de processador otimizada com a versão do HEAD: mesma dimensão e aparência equivalente na inspeção. Não se afirma inspeção visual individual de todos os 38 PNGs.
- Teste CORS na API publicada: origem `http://localhost:8081` retornou 403 sem `Access-Control-Allow-Origin`; `http://localhost:8082` retornou 200 e foi permitida; uma origem externa arbitrária retornou 403. Isso descreve o serviço publicado atual, que não corresponde ao CORS local revisado para aceitar 8081 e 8082. A atualização precisa ser publicada/configurada no Render antes de apresentar o frontend Web em localhost:8081.
- Verificação heurística de segredos: nenhum indicador de alta confiança encontrado nos 104 arquivos textuais rastreados/não rastreados verificados (excluído `.env`) nem nos 176 blobs examinados no histórico local alcançável. O `.env` permanece ignorado e não rastreado. A busca é por padrões conhecidos, não uma garantia contra todo segredo.
- O repositório não é shallow; a verificação local examinou os refs locais `master` e `origin/master`. Não foi feita reescrita do histórico.

## Validação executada

- Frontend: `npm run lint` — passou.
- Frontend: `npx tsc --noEmit` — passou.
- Frontend: `npx expo export --platform all` — passou, gerando bundles Android, iOS e Web em `setupforge-frontend/dist` (ignorado pelo Git).
- Backend: `./mvnw verify` — passou, 1 teste, 0 falhas/erros. O teste de contexto usou o perfil isolado H2 e não acessou a base configurada.
- PostgreSQL temporário: migration aplicada duas vezes; aplicação empacotada iniciou com Flyway e Hibernate `validate` — passou.
- `git diff --check` — passou.

## Itens para a entrega final

1. Publicar o backend atualizado e configurar `CORS_ALLOWED_ORIGINS` com a origem web definitiva, caso a versão web seja apresentada.
2. Confirmar a conexão e o histórico Flyway na instância PostgreSQL real após a publicação. Essa instância não foi acessível para verificação read-only neste ambiente.
3. Publicar/configurar CORS para permitir a origem real de demonstração Web; na versão Render atual, `localhost:8081` é negado.
4. Se for disponibilizada uma URL pública do frontend, repetir Lighthouse nessa URL. Até agora há somente métricas da versão local.
5. O teste de landscape em um dispositivo físico foi relatado pelo usuário; não há evidência nesta revisão de outros aparelhos. Se a avaliação exigir performance mobile nativa, medir em dispositivo real; Lighthouse Web não comprova performance nativa.
6. Executar a auditoria SQL read-only no PostgreSQL de produção usando a URL External para cliente local; o `.env` disponível só tinha endereço interno, sem resolução DNS fora do Render.

## Revalidação para a missão final

**Data:** 24/11/2026. A busca de produtos foi concluída localmente nesta revisão. `npm run lint`, `npx tsc --noEmit` e `npx expo export --platform all` passaram; a exportação gerou bundles Android, iOS e Web, incluindo as rotas de busca e montagem. A primeira tentativa de `./mvnw clean verify` não iniciou os testes porque o sandbox não permitiu abrir o lock de `~/.m2`; a repetição com acesso ao cache local concluiu com `BUILD SUCCESS`, 10 testes, 0 falhas e 0 erros. Os testes de contexto e HTTP usam H2/MockMvc, não a base PostgreSQL de produção.

O código CORS local usa uma lista de origens explícitas, inclui `http://localhost:8081` e `http://localhost:8082`, e aceita entradas adicionais separadas por vírgulas em `CORS_ALLOWED_ORIGINS`. A suíte MockMvc cobre preflight e GET permitido/bloqueado. A lista do Render deve conter a URL pública final do frontend, se existir, separada por vírgula; não se deve usar `*`. Após o push de `ee8afb1`, os endpoints `/actuator/health`, `/categorias` e `/produto` responderam HTTP 200 (`UP`, 14 categorias e 37 produtos); Swagger UI e OpenAPI também responderam HTTP 200. O preflight público permitiu `http://localhost:8082`, rejeitou `http://localhost:8081` (403) e rejeitou `https://untrusted.example` (403). Portanto, o CORS novo ainda não está ativo no serviço Render. O push foi confirmado, mas não há acesso ao painel ou deploy hook para iniciar/inspecionar uma publicação manual; a publicação do backend não está confirmada. As imagens não foram retestadas nesta checagem pós-push; na checagem anterior registrada acima, as 37 URLs da API responderam sem erro.

O teste automatizado CORS é local e não comprova que a configuração já está ativa no Render. Não foi feita interação manual de UI nesta etapa. A rotação horizontal em um dispositivo físico permanece como teste relatado anteriormente pelo usuário. A auditoria SQL direta do banco de produção continua pendente.
