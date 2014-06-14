-- USERS
INSERT INTO users (email, password, display_name) VALUES ('condorito@pepo.cl', 'condorito', 'Condorito');
INSERT INTO users (email, password, display_name) VALUES ('plomazo@pepo.cl', 'plomazo', 'Pepe Cortisona');

-- PICS
INSERT INTO pics (uid, filename, comment) VALUES ((SELECT uid FROM users WHERE email = 'condorito@pepo.cl'), 'condorito.png', 'This is me!');
