# Zapery – Defesa de Código (Detalhada)

## Sumário
- Visão Geral da Arquitetura
- Fluxo do App e Navegação
- Design System (Tema, Cores, Tipografia, Formas)
- Componentes Reutilizáveis (Buttons e Fields)
- Telas (UI) – Explicação Detalhada
- ViewModel e Lógica de Negócio
- Camada de Dados (Room) – Database, Entities, DAOs, Repositórios
- Modelos de Domínio
- Decisões de UX/UI e Racional
- Como Rodar e Como Exportar este Documento para PDF

---

## Visão Geral da Arquitetura

- Camadas:
  - UI (Jetpack Compose + Material 3): telas, navegação, theming e componentes reutilizáveis.
  - ViewModel (AndroidX Lifecycle): estado da UI e regras de negócio.
  - Data (Room): persistência local com Database, Entities, DAOs e Repositórios.
- Benefícios:
  - Separação de responsabilidades, testabilidade e manutenibilidade.

Arquivos principais:
- Ponto de entrada: `app/src/main/java/com/example/zapery/MainActivity.kt`
- Tema/Design: `ui/theme/*`
- Componentes: `ui/components/Components.kt`
- Telas: `ui/*.kt`
- ViewModel: `viewmodel/*`
- Data/Room: `data/local/*` (database, entities, daos) e `data/repositorio/*` (repositórios)

---

## Fluxo do App e Navegação

Arquivo: `MainActivity.kt`
- Usa `setContent` para iniciar o Compose e aplica o tema `ZaperyThemeM3`.
- Cria `NavController` e instancia `AppViewModel` via `AppViewModelFactory` injetando repositórios do Room.
- Define o `NavHost` com rotas: `login`, `cadastro`, `mercados`, `produtos/{mercadoId}`, `carrinho`, `compras_rapidas`, `confirmacao`, `pedido_finalizado`, `admin`, `admin/{marketId}`, `produto_form/{marketId}`, `produto_form/{marketId}/{productId}`.
- Envolve a árvore com `Surface(color = MaterialTheme.colorScheme.background)`.

Importante:
- Navegação declarativa com Navigation Compose.
- Rotas com parâmetros (ex.: `mercadoId`, `productId`).

---

## Design System (Tema, Cores, Tipografia, Formas)

Arquivos: `ui/theme/ZaperyThemeM3.kt`, `ui/theme/Typography.kt`, `ui/theme/Shape.kt`

- `ZaperyThemeM3.kt` (Material 3):
  - Light/Dark `ColorScheme` definidos manualmente (dynamicColor desativado para consistência).
  - Primária (azul vibrante): `#2563EB`. Secundária (azul suave): `#60A5FA`.
  - Background claro: branco puro. Dark: near-black com alto contraste.
  - `MaterialTheme(colorScheme, Typography3, Shapes3)` aplicado globalmente.

- `Typography.kt`:
  - `Typography3` do Material 3 como base.
  - Títulos chave centralizados nas telas para legibilidade.

- `Shape.kt`:
  - `Shapes3` com cantos arredondados modernos (6–28dp) para cartões e botões.

Padrão visual adotado:
- `OutlinedCard` com preenchimento leve `#F8FAFC` e elevação 0dp para seções/blocos.
- Ícones consistentes em botões e top bars.

---

## Componentes Reutilizáveis

Arquivo: `ui/components/Components.kt`

- `PrimaryButton` e `SecondaryButton` (Material 3):
  - Parâmetros: `text`, `onClick`, `modifier`, `enabled`, `compact`, `leadingIcon`, `iconContentDescription`.
  - Encapsulam padrão visual de ação primária/tonal.

- `EmailField` e `PasswordField`:
  - `OutlinedTextField` com ícones (e alternância de visibilidade no password).
  - Teclado e IME configurados (Email/Password, Next/Done).

Benefícios: consistência visual, menos duplicação, fácil evolução.

---

## Telas (UI) – Explicação Detalhada

Todas as telas usam `Scaffold` com `CenterAlignedTopAppBar`. Snackbars onde necessário. Navegação via `NavController`.

1) `TelaLogin.kt`
- Campos: `EmailField`, `PasswordField`.
- Ações: `PrimaryButton` Entrar (ícone) e `SecondaryButton` Criar Conta (ícone).
- Lógica: `viewModel.autenticar(email, senha)`; em sucesso, navega para `mercados`.

2) `TelaCadastro.kt`
- Formulário dentro de `OutlinedCard` com preenchimento leve `#F8FAFC`.
- Ação: `PrimaryButton` Cadastrar (ícone) chamando `viewModel.cadastrar`.

3) `TelaMercados.kt`
- Seção de busca e chips de ordenação dentro de `OutlinedCard` leve.
- Lista de mercados em `OutlinedCard` leve, com status "Aberto agora".
- Admin: FAB com ícone para cadastrar mercado, botões para gerenciar/cadastrar produto.
- Navega para `produtos/{mercadoId}`.

4) `TelaProdutos.kt`
- Título central "Lista de Produtos".
- Lista de produtos em `OutlinedCard` leve.
- Ícone estrela para compra rápida.
- Botão adicionar ao carrinho com ícone; botão "Ir para Carrinho" com ícone.

5) `TelaCarrinho.kt`
- Lista de itens em `OutlinedCard` leve com controles de quantidade.
- Empty state centralizado e maior.
- "Total" centralizado e maior.
- Botão "Finalizar Compra" com ícone.

6) `TelaComprasRapidas.kt`
- Lista de selecionados em `OutlinedCard` leve, com checkbox.
- "Enviar ao Carrinho" e "Finalizar Agora" com ícones.

7) `TelaConfirmacao.kt`
- "Resumo da Compra" centralizado.
- Resumo em `OutlinedCard` leve; "Total" centralizado.
- Botão "Finalizar Compra".

8) `TelaPedidoFinalizado.kt`
- Mensagem de sucesso centralizada com tipografia destacada.
- Botão "Voltar às compras".

9) `TelaAdmin.kt`
- Formação de mercados/produtos para administradores.
- Seções e listas em `OutlinedCard` leve; títulos centrais.
- Ações: editar/excluir/cadastrar.

10) `TelaProdutoForm.kt`
- Formulário para criar/editar produto com `OutlinedTextField` (nome/preço).
- Ações: salvar/cadastrar e excluir (quando edição).
- Opcional: envolver conteúdo com `OutlinedCard` leve (mesmo padrão visual das outras telas).

---

## ViewModel e Lógica de Negócio

Arquivos: `viewmodel/AppViewModel.kt`, `viewmodel/AppViewModelFactory.kt`

- `AppViewModel`:
  - Estado observável (Compose): `usuarios`, `mercados`, `produtos`, `carrinho`, `selecaoRapida`.
  - Usuário atual e flag de admin.
  - `init` carrega/seed inicial.
  - Métodos principais:
    - Autenticação/cadastro.
    - Carrinho: adicionar/remover, calcular total, finalizar compra.
    - Compras rápidas: alternar seleção, verificar, enviar ao carrinho.
    - Admin Mercado: inserir/atualizar/excluir, recarregar listas.
    - Admin Produto: inserir/atualizar/excluir, recarregar listas.
  - Acesso a dados via repositórios (coroutines + `viewModelScope`).

- `AppViewModelFactory`:
  - Injeta `UsuarioRepositorio`, `MercadoRepositorio`, `ProdutoRepositorio`, `CarrinhoRepositorio`, `CompraRapidaRepositorio`.

---

## Camada de Dados (Room)

- `DatabaseProvider.kt`:
  - Singleton do Room DB com `fallbackToDestructiveMigration()`.

- `ZaperyDatabase.kt`:
  - `@Database` define entities e DAOs expostos.

- Entities (em `data/local/entidade`):
  - `UsuarioEntidade`: tabela `users` com índice único para e-mail, flag `isAdmin`.
  - `MercadoEntidade`: tabela `markets` (id, nome, endereço).
  - `ProdutoEntidade`: tabela `products` (id, nome, preço, `mercadoId`, `imageUrl`).
  - `ItemCarrinhoEntidade`: tabela `cart_items` com FKs para usuário e produto, índice para `userId/productId`.
  - `CompraRapidaItemEntidade`: tabela `quick_buy_items` com índice único `userId+productId`.

- DAOs (em `data/local/acesso_dados`):
  - `UsuarioDao`: inserir, atualizar, buscar por email/id, listar todos, definir admin.
  - `MercadoDao`: inserir, atualizar, listar, buscar por id, excluir.
  - `ProdutoDao`: inserir, atualizar, listar, por mercado, buscar por id, excluir.
  - `ItemCarrinhoDao`: upsert/atualizar/excluir/limpar; carrinho convidado e por usuário.
  - `CompraRapidaDao`: inserir/remover/listar por usuário; verificar existência.

- Repositórios (em `data/repositorio`):
  - Camada que abstrai o DAO para o ViewModel (ex.: `UsuarioRepositorio.register`, `ProdutoRepositorio.getByMarket`, etc.).

Observação: o código usa nomes de pacotes em PT-BR (`entidade`, `acesso_dados`, `repositorio`), mas mantém padrões do Room/Compose.

---

## Modelos de Domínio (UI/Negócio)

- `model/Usuario.kt`, `model/Mercado.kt`, `model/Produto.kt`:
  - Modelos usados pela UI/negócio (separados das `Entity` de Room).

- Conversões acontecem no ViewModel quando necessário (ex.: transformar entidades em modelos para exibição).

---

## Decisões de UX/UI e Racional

- Cores dinâmicas desativadas: consistência total independente do Android 12+.
- Azul como cor de ação (identidade forte e acessível).
- Cards com borda e preenchimento leve `#F8FAFC`: mais leve que cinza, moderno e sutil.
- Ícones padronizados em botões e top bars melhoram reconhecimento de ações.
- Títulos/empty states e totais centralizados e maiores para foco e legibilidade.

---

## Como Rodar

1. Android Studio Flamingo+ / Gradle atualizado.
2. Sincronizar Gradle e rodar `:app` em um dispositivo/emulador.
3. Build via terminal: `./gradlew assembleDebug` (Linux/Mac) ou `gradlew.bat assembleDebug` (Windows).

---

## Como Exportar este Documento para PDF

Opção 1 – VS Code/IDE:
- Abra `docs/defesa-codigo-zapery.md`, use a pré-visualização de Markdown e exporte/imprima como PDF.

Opção 2 – Pandoc (linha de comando):
```
pandoc docs/defesa-codigo-zapery.md -o docs/defesa-codigo-zapery.pdf
```

Opção 3 – Extensões de Markdown (ex.: Markdown PDF):
- Instale a extensão, abra o arquivo e selecione “Export PDF”.

---

## Apêndice (Roteiro de Defesa – 5 a 7 minutos)

1. Introdução (30s): objetivo do app e resumo da arquitetura.
2. Arquitetura (1min): camadas, responsabilidades e benefícios.
3. UI/UX (1–2min): tema azul, OutlinedCards, ícones, centralização e consistência.
4. Navegação (30s): rotas principais e parâmetros.
5. ViewModel (1min): estado, operações (auth, carrinho, admin, compras rápidas).
6. Dados (1–2min): Room (entities, daos, repositórios), fluxo CRUD.
7. Encerramento (30s): decisões de qualidade, possíveis evoluções (temas dinâmicos opcionais, testes, acessibilidade avançada).
