CREATE TABLE usuario(
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nome VARCHAR(60),
                        senha VARCHAR(45),
                        acesso INT
);

CREATE TABLE unidade_medida(
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               categoria VARCHAR(75) NOT NULL,
                               conversao_padrao DECIMAL(12,2)
);

CREATE TABLE categoria_item(
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               nome VARCHAR(45)
);

CREATE TABLE item(
                     id INT PRIMARY KEY AUTO_INCREMENT,
                     nome VARCHAR(65),
                     perecivel TINYINT,
                     dia_vencimento INT,
                     fk_categoria_item INT,
                     fk_unidade_medida INT,
                     FOREIGN KEY (fk_categoria_item) REFERENCES categoria_item (id),
                     FOREIGN KEY (fk_unidade_medida) REFERENCES unidade_medida (id)
);




CREATE TABLE item_audit(
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           descricao VARCHAR(45),
                           data_hora DATETIME,
                           fk_item INT,
                           fk_usuario INT,
                           FOREIGN KEY (fk_item) REFERENCES item (id),
                           FOREIGN KEY (fk_usuario) REFERENCES usuario (id)
);


select * from item;

CREATE TABLE categoria_audit(
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                descricao VARCHAR(45),
                                data_hora DATETIME,
                                fk_categoria_item INT,
                                fk_usuario INT,
                                FOREIGN KEY (fk_categoria_item) REFERENCES categoria_item (id),
                                FOREIGN KEY (fk_usuario) REFERENCES usuario (id)
);
CREATE TABLE unidade_medida_audit(
                                     id INT PRIMARY KEY AUTO_INCREMENT,
                                     descricao VARCHAR(45),
                                     data_hora DATETIME,
                                     fk_unidade_medida INT,
                                     fk_usuario INT,
                                     FOREIGN KEY (fk_unidade_medida) REFERENCES unidade_medida (id),
                                     FOREIGN KEY (fk_usuario) REFERENCES usuario (id)
);

select * from item;
select * from usuario;
select * from categoria_item;
select * from unidade_medida;