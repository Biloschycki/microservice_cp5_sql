-- Migration: criacao do banco e das tabelas da API Jogador (SQL Server / T-SQL)
-- Data: 2026-08-17
--
-- Tabelas correspondentes as entidades JPA:
--   - Jogador     -> jogadores
--   - Restaurante -> pedidos
--
-- Observacao: nenhuma das entidades utiliza @GeneratedValue, portanto o
-- id de cada registro deve ser informado pelo cliente da API
-- (nao ha IDENTITY nem sequence).

IF DB_ID('api') IS NULL
    CREATE DATABASE api;
GO

USE api;
GO

IF OBJECT_ID('dbo.jogadores', 'U') IS NULL
CREATE TABLE dbo.jogadores (
    id              BIGINT       NOT NULL,
    nome_jogador    VARCHAR(70)  NOT NULL,
    time_jogador    VARCHAR(70)  NOT NULL,
    numero_jogador  INT          NOT NULL,
    posicao_jogador VARCHAR(255) NOT NULL,
    CONSTRAINT pk_jogadores PRIMARY KEY (id)
);
GO

IF OBJECT_ID('dbo.pedidos', 'U') IS NULL
CREATE TABLE dbo.pedidos (
    id               BIGINT      NOT NULL,
    nome_prato       VARCHAR(70) NOT NULL,
    numero_mesa      INT         NOT NULL,
    quantidade_prato INT         NOT NULL,
    valor_pedido     FLOAT(53)   NOT NULL,
    CONSTRAINT pk_pedidos PRIMARY KEY (id)
);
GO
