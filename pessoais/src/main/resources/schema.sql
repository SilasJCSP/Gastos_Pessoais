-- Criação da tabela categoria_gasto
CREATE TABLE IF NOT EXISTS categoria_gasto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255)
);

-- Criação da tabela lancamento
CREATE TABLE IF NOT EXISTS lancamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor DECIMAL(19, 2) NOT NULL,
    data DATE NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    categoria_id BIGINT,
    FOREIGN KEY (categoria_id) REFERENCES categoria_gasto(id)
);

-- Índices para melhorar performance
CREATE INDEX idx_lancamento_data ON lancamento(data);
CREATE INDEX idx_lancamento_categoria ON lancamento(categoria_id);
