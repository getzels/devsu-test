INSERT INTO person (name, gender, age, identification, address, phone) VALUES
                                                                           ('John Doe', 'MALE', 30, 'ID12345', '123 Main St', '555-1234'),
                                                                           ('Jane Smith', 'FEMALE', 28, 'ID67890', '456 Elm St', '555-5678'),
                                                                           ('Alex Johnson', 'NON_BINARY', 35, 'ID54321', '789 Oak St', '555-9012');

INSERT INTO client (id, client_id, password, active) VALUES
                                                         (1, 'C123', 'pass123', TRUE),
                                                         (2, 'C456', 'pass456', TRUE),
                                                         (3, 'C789', 'pass789', FALSE);

INSERT INTO account (account_number, account_type, initial_balance, status, client_id) VALUES
                                                                                           ('ACC1234', 'CHECKING', 1000.00, 'Active', 1),
                                                                                           ('ACC2345', 'SAVINGS', 5000.00, 'Active', 2),
                                                                                           ('ACC3456', 'CHECKING', 1500.00, 'Inactive', 3);

INSERT INTO transaction (date, account_id, type, amount, balance_after_transaction) VALUES
                                                                                        ('2023-11-10 10:00:00', 1, 'CREDIT', 200.00, 1200.00),
                                                                                        ('2023-11-10 11:00:00', 2, 'DEBIT', 500.00, 4500.00),
                                                                                        ('2023-11-10 12:00:00', 3, 'CREDIT', 300.00, 1800.00);
