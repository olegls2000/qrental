INSERT INTO constant (constant, value, description, negative)
VALUES ('fee weekly interest', 10, 'fee weekly interest', true)
ON CONFLICT (constant) DO NOTHING;
