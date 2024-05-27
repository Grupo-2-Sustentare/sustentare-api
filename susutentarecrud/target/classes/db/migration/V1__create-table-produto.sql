CREATE TABLE produto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    empresa VARCHAR(50),
    lote VARCHAR(50),
    nome VARCHAR(50),
    preco DECIMAL(4,2),
    qtdProduto INT
);