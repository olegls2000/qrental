UPDATE transaction_type
SET code = 'WKLRENT'
WHERE name = 'weekly rent';

UPDATE transaction_type
SET code = 'NLBLFINE'
WHERE name = 'no label fine';

UPDATE transaction_type
SET code = 'SLFRESPPRQST'
WHERE name = 'self responsibility payment request';

UPDATE transaction_type
SET code = 'SLFRESPPPMNT'
WHERE name = 'self responsibility payment';

UPDATE transaction_type
SET code = 'ABSNADJ'
WHERE name = 'absence adjustment';

UPDATE transaction_type
SET code = 'INROADINSRC'
WHERE name = 'inner road insurance';

UPDATE transaction_type
SET code = 'DMGWRTOFF'
WHERE name = 'damage write off';

UPDATE transaction_type
SET code = 'FEEDBT'
WHERE name = 'fee debt';

UPDATE transaction_type
SET code = 'BNS'
WHERE name = 'bonus';

UPDATE transaction_type
SET code = 'DMGP'
WHERE name = 'damage payment';
