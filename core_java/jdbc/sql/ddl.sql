-- Create Database
DROP DATABASE IF EXISTS stock_quote;
CREATE DATABASE stock_quote;

\connect stock_quote;

-- Create Quote table
DROP TABLE IF EXISTS quote;
CREATE TABLE quote (
    symbol              VARCHAR(10) PRIMARY KEY,
    open                DECIMAL(10, 2) NOT NULL,
    high                DECIMAL(10, 2) NOT NULL,
    low                 DECIMAL(10, 2) NOT NULL,
    price               DECIMAL(10, 2) NOT NULL,
    volume              INT NOT NULL,
    latest_trading_day  DATE NOT NULL,
    previous_close      DECIMAL(10, 2) NOT NULL,
    change              DECIMAL(10, 2) NOT NULL,
    change_percent      VARCHAR(10) NOT NULL,
    timestamp           TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL
);

-- Create Position table
DROP TABLE IF EXISTS position;
CREATE TABLE position (
    symbol                VARCHAR(10) PRIMARY KEY,
    number_of_shares      INT NOT NULL,
    value_paid            DECIMAL(10, 2) NOT NULL,
    CONSTRAINT symbol_fk	FOREIGN KEY (symbol) REFERENCES quote(symbol)
);

-- Pre populate DB with some quotes
INSERT INTO quote (symbol, open, high, low, price, volume, latest_trading_day, previous_close, change, change_percent, timestamp) VALUES
('AAPL', 225.14, 226.81, 223.32, 225.67, 33700693, '2024-10-03', 226.78, -1.11, '-0.4895%', '2024-10-03 21:00:00'),
('TSLA', 244.48, 249.79, 237.81, 240.66, 80074386, '2024-10-03', 249.02, -8.36, '-3.3572%', '2024-10-03 21:05:00'),
('MSFT', 417.63, 419.55, 414.29, 416.54, 13490771, '2024-10-03', 417.13, -0.59, '-0.1414%', '2024-10-03 21:10:00'),
('AMZN', 183.045, 183.44, 180.875, 181.96, 28854036, '2024-10-03', 184.76, -2.80, '-1.5155%', '2024-10-03 21:15:00'),
('GOOGL', 164.41, 166.64, 163.923, 165.86, 14643565, '2024-10-03', 165.86, 0.00, '0.0000%', '2024-10-03 21:19:00');