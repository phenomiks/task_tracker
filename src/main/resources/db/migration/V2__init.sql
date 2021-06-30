BEGIN;

INSERT INTO users (firstname, lastname, phone) VALUES
('Vladimir', 'Petrov', '22-15'),
('Ivan', 'Sidorov', '24-80'),
('Alexey', 'Ivanov', '15-76'),
('Alexander', 'Avdeev', '22-15');

INSERT INTO projects (title, description) VALUES
('Task Tracker', 'Task description'),
('Web-site', 'Web-site description'),
('Mobile app', 'Mobile app description');

INSERT INTO tasks (title, description) VALUES
('Create task tracker', 'Task description'),
('Create web-site', 'Web-site description'),
('Create mobile app', 'Mobile app description');

COMMIT;