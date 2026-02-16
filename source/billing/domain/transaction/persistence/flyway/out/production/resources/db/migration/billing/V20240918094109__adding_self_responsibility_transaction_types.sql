DELETE
from transaction
where transaction_type_id in (select id
                              from transaction_type
                              where
                                  name in ('self responsibility', 'self responsibility overpayment', 'damage payment'));
DELETE
from transaction_type
where name in ('self responsibility', 'self responsibility overpayment', 'damage payment');

INSERT INTO transaction_type(name, description, transaction_kind_id)
VALUES ('self responsibility payment request',
        'For the transactions that are considered as a request for the Self Responsibility compensation. "Real" compensation Transaction can be created during Insurance Balance Calculation',
        (select id from transaction_kind where code = 'SR'))
ON CONFLICT DO NOTHING;

INSERT INTO transaction_type(name, description, transaction_kind_id)
VALUES ('self responsibility payment',
        'For the transactions dedicated to compensate Self Responsibility',
        (select id from transaction_kind where code = 'FA'))
ON CONFLICT DO NOTHING;

INSERT INTO transaction_type(name, description, transaction_kind_id)
VALUES ('damage payment',
        'For the transactions dedicated to compensate Damage',
        (select id from transaction_kind where code = 'FA'))
ON CONFLICT DO NOTHING;


