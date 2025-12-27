-- Inserir categorias iniciais
INSERT INTO categoria_gasto (nome, descricao) VALUES
('Moradia', 'Despesas com moradia, incluindo aluguel e condomínio'),
('Transporte', 'Gastos com transporte, incluindo combustível e manutenção'),
('Alimentação', 'Gastos com alimentação em geral'),
('Saúde', 'Gastos com saúde e bem-estar'),
('Lazer', 'Atividades de lazer e entretenimento'),
('Educação', 'Cursos e materiais educacionais'),
('Outros', 'Outros gastos diversos');

-- Inserir alguns lançamentos de exemplo
INSERT INTO lancamento (descricao, valor, data, tipo, categoria_id) VALUES
('Mercado do mês', 850.50, '2025-10-05', 'DESPESA', 3),
('Combustível', 250.00, '2025-10-10', 'DESPESA', 2),
('Salário', 5000.00, '2025-10-01', 'RECEITA', NULL),
('Academia', 120.00, '2025-10-15', 'DESPESA', 4),
('Cinema', 45.00, '2025-10-20', 'DESPESA', 5);
