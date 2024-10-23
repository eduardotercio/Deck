# Desafio de Aplicativo - Deck de Cartas

Este aplicativo foi desenvolvido por mim como parte de um desafio. O objetivo é gerenciar decks de cartas, permitindo ao usuário realizar diversas interações como adicionar decks/pilhas de cartas, embaralhar e mover cartas entre as pilhas "mão" e o "lixo", com animações para as ações realizadas.

## Funcionalidades

- **Tela Inicial**: 
  - Ao abrir o app, o usuário verá uma tela vazia com um botão no rodapé para adicionar um novo deck.
  - A cada deck adicionado, uma carta virada para baixo, representando o deck, será salva localmente e exibida em uma grade de 4 colunas.
  - O usuário pode:
    - Pressionar longamente em um deck para abrir um diálogo perguntando se deseja deletá-lo.
    - Clicar no deck para ser redirecionado à tela de gerenciamento do deck.

- **Tela de Deck**:
  - Na parte superior, o deck será exibido com dois botões:
    - **Embaralhar**: Embaralha as cartas do deck.
    - **Mostrar Carta**: Revela uma carta do deck e a envia para a "mão".
  - No meio da tela, à direita, haverá uma pilha de cartas viradas para baixo representando o "lixo", com dois botões:
    - **Embaralhar Lixo**: Embaralha as cartas que estão no lixo.
    - **Mostrar Carta**: Revela uma carta do "lixo" e a envia para a "mão".
  - Na parte inferior, haverá a área da "mão", onde as cartas enviadas pelo deck ou pelo "lixo" aparecerão.
    - Três botões estarão disponíveis acima da "mão":
      - **Devolver ao Deck**: Retorna a carta selecionada à pilha de cartas do deck.
      - **Embaralhar na Mão**: Embaralha as cartas que estão na mão.
      - **Enviar ao Lixo**: Envia uma carta da "mão" para o "lixo".

## Tecnologias Utilizadas

- **Arquitetura MVI**: Foi utilizada a arquitetura Model-View-Intent para garantir uma separação clara de responsabilidades e fácil manutenção.
- **Jetpack Compose**: Interface de usuário moderna e reativa, com componentes declarativos.
- **Corrotinas e Flow**: Para operações assíncronas e reativas, garantindo uma experiência fluida.
- **Multimódulo**: O projeto foi dividido em módulos, promovendo escalabilidade e reusabilidade de código.
- **Testes Unitários**: Implementação de testes unitários para garantir a qualidade do código e evitar regressões.
- **Injeção de Dependências com Koin**: Para gerenciar dependências de forma eficiente e modular.
- **Ktor**: Para realizar a integração com a API [DeckOfCards](https://deckofcardsapi.com/), permitindo funcionalidades como embaralhar e distribuir cartas.
- **Figma**: Um protótipo do layout foi desenvolvido no Figma para guiar a implementação da interface.

## Como Rodar o Projeto

1. Clone o repositório.
2. Abra o projeto no Android Studio.
3. Construa e execute o app em debug no seu dispositivo ou emulador.
4. É possível criar um apk em release.

## Imagens

![deck_image_1](https://github.com/user-attachments/assets/58f04ae0-1247-4277-b65b-80d975edfd8c)

![deck_image_2](https://github.com/user-attachments/assets/b5344bde-27e0-4107-b117-545e659af9bb)

![deck_image_3](https://github.com/user-attachments/assets/30dcb340-f9b4-4a7f-8a2c-d9070eb6368e)
