INSERT INTO utenti (nome, cognome, email, password, ruolo) VALUES
('Linda', 'Bianchi', 'admin@linda.it', 'admin123', 'ADMIN'),
('Marco', 'Neri', 'marco@linda.it', 'marco123', 'FATTORINO'),
('Giulia', 'Rossi', 'giulia@linda.it', 'giulia123', 'UTENTE');

INSERT INTO fattorini (nome, cognome, telefono, zona, attivo, utente_id) VALUES
('Marco', 'Neri', '3331234567', 'Centro', true, 2);