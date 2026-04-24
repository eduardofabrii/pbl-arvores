# PBL 02 - Sistema de Ranking de Jogadores

Este repositório contém a implementação de um sistema de ranking de jogadores utilizando uma Árvore Binária de Busca (ABB) não balanceada. O projeto foi desenvolvido como requisito da disciplina de Resolução de Problemas Estruturados em Computação da PUCPR.

## 👥 Participantes

* João Vitor Correa
* Eduardo Fabri
* João Pedro Cardoso

##  Sobre o Projeto

O sistema gerencia o ranking de jogadores utilizando uma Árvore Binária de Busca construída do zero, sem o uso de estruturas de dados prontas da linguagem Java. Cada jogador possui um *nickname* e um *ranking*, sendo o *ranking* utilizado como a chave de ordenação da árvore.

### Funcionalidades

O programa opera via terminal e possui as seguintes funcionalidades principais:
* **Carga de Dados:** Lê os jogadores a partir do arquivo `sup/players.csv`.
* **Inserção:** Adiciona novos jogadores informando nome e ranking.
* **Busca:** Localiza um jogador pelo seu *nickname*.
* **Remoção:** Remove jogadores pelo *nickname*, reajustando as posições da árvore e o ranking dos demais jogadores.
* **Visualização:** Exibe os jogadores em ordem no terminal e conta com uma Interface Gráfica (Swing) para visualizar a hierarquia dos nós da árvore.

## Como Executar

1. Certifique-se de ter o Java (JDK) instalado em sua máquina.
2. Clone este repositório.
3. Compile todos os arquivos `.java` localizados na pasta `src/`.
4. Execute o arquivo principal:
   ```bash
   java Main
