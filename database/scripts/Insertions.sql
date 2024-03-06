-- Beans table
INSERT INTO Beans (name, time_to_kill, quantity) VALUES
('Castor Beans', 3, 100),
('Lima Beans', 2, 50),
('Jequirity Beans', 1, 75),
('Velvet Beans', 1, 30),
('Lupin Beans', 1, 20),
('Raw Kidney Beans', 2, 15),
('Ignatius Beans', 1, 10),
('Lucky Beans', 1, 25);

-- Prices table
INSERT INTO Prices (price, start_date, end_date, bean_id) VALUES
(120.00, '2023-11-10', NULL, 1),
(70.00, '2023-11-25', NULL, 4),
(100.00, '2023-12-05', NULL, 2),
(50.00, '2023-12-15', NULL, 6),
(80.00, '2023-12-20', NULL, 3),
(30.00, '2024-01-01', NULL, 5),
(70.00, '2024-02-01', NULL, 7),
(60.00, '2024-03-15', NULL, 8);

-- Agents table
INSERT INTO Agents (alias, firstname, lastname) VALUES
('Sipho', 'John', 'Doe'),
('Emily', 'Thabo', 'Mthembu'),
('Alexander', 'Michael', 'Johnson'),
('Olivia', 'Sarah', 'Brown'),
('William', 'Lungelo', 'Mabuza'),
('Sophia', 'Nomthandazo', 'Nkosi'),
('Henry', 'Musa', 'Mkhize'),
('Charlotte', 'Zanele', 'Molefe');

-- Addresses table
INSERT INTO Addresses (street_address, suburb, city) VALUES
('123 Main St', 'Downtown', 'Metropolis'),
('456 Elm St', 'Uptown', 'Gotham'),
('789 Oak St', 'Midtown', 'Central City'),
('101 Pine St', 'Westside', 'Starling City'),
('321 Acacia Ave', 'Soweto', 'Johannesburg'),
('456 Oak Ave', 'Randpark', 'Randburg'),
('789 Maple St', 'Hillside', 'Randburg');

-- Customers table
INSERT INTO Customers (alias) VALUES
('Sbu'),
('Thandi'),
('Bongi'),
('Themba'),
('Nandi'),
('Sipho'),
('Lerato'),
('Tumi');

-- AgentPhoneNumbers table
INSERT INTO AgentPhoneNumbers (agent_id, phone_number) VALUES
(1, '0823456789'),
(2, '0734567890'),
(3, '0812345678'),
(4, '0765432198'),
(5, '0824567890'),
(6, '0712345678'),
(7, '0761234567'),
(8, '0839876543');

-- Statuses table
INSERT INTO Statuses (status) VALUES
('Received'),
('Assigned to agent'),
('In Transit'),
('Delivered');

-- Orders table
INSERT INTO Orders (customer_id, date_time, address_id, status_id, agent_id) VALUES
(1, '2023-09-16 08:30:47', 1, 4, 4),
(2, '2023-10-25 11:45:23', 2, 4, 5),
(3, '2023-11-21 14:15:59', 3, 4, 3),
(4, '2023-12-29 09:20:12', 4, 4, 2),
(5, '2024-01-05 10:30:15', 5, 4, 1),
(6, '2024-03-12 13:45:30', 6, 2, 6),
(7, '2024-03-14 16:00:45', 7, 1, NULL);

-- OrderLines table
INSERT INTO OrderLines (order_id, bean_id, quantity) VALUES
(1, 1, 5),
(1, 2, 10),
(2, 3, 3),
(2, 4, 2),
(3, 5, 2),
(3, 6, 3),
(4, 7, 1),
(5, 1, 3),
(5, 3, 2),
(6, 2, 5),
(6, 4, 3),
(7, 5, 1),
(7, 6, 2);

-- CustomerPhoneNumbers table
INSERT INTO CustomerPhoneNumbers (customer_id, phone_number) VALUES
(1, '0823456789'),
(2, '0734567890'),
(3, '0812345678'),
(4, '0765432198'),
(5, '0824567890'),
(6, '0712345678'),
(7, '0761234567'),
(8, '0839876543');