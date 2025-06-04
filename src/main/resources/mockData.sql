INSERT INTO users (username, email, password, role, created_at, updated_at) VALUES
('john_doe', 'john.doe@example.com', '$2a$10$z4J.h4gCvSUkvbYyhqA9n.yGPs/tvq9YEAdD7vCqKjeg8A74.ddBm', 'ROLE_USER', NOW(), NOW()),
('jane_smith', 'jane.smith@example.com', '$2a$10$z4J.h4gCvSUkvbYyhqA9n.yGPs/tvq9YEAdD7vCqKjeg8A74.ddBm', 'ROLE_USER', NOW(), NOW()),
('admin_user', 'admin@example.com', '$2a$10$z4J.h4gCvSUkvbYyhqA9n.yGPs/tvq9YEAdD7vCqKjeg8A74.ddBm', 'ROLE_ADMIN', NOW(), NOW()),
('alice_dev', 'alice.dev@example.com', '$2a$10$z4J.h4gCvSUkvbYyhqA9n.yGPs/tvq9YEAdD7vCqKjeg8A74.ddBm', 'ROLE_USER', NOW(), NOW()),
('bob_learner', 'bob.learner@example.com', '$2a$10$z4J.h4gCvSUkvbYyhqA9n.yGPs/tvq9YEAdD7vCqKjeg8A74.ddBm', 'ROLE_USER', NOW(), NOW()),
('charlie_creator', 'charlie.creator@example.com', '$2a$10$z4J.h4gCvSUkvbYyhqA9n.yGPs/tvq9YEAdD7vCqKjeg8A74.ddBm', 'ROLE_USER', NOW(), NOW()),
('diana_teach', 'diana.teach@example.com', '$2a$10$z4J.h4gCvSUkvbYyhqA9n.yGPs/tvq9YEAdD7vCqKjeg8A74.ddBm', 'ROLE_USER', NOW(), NOW()),
('edward_study', 'edward.study@example.com', '$2a$10$z4J.h4gCvSUkvbYyhqA9n.yGPs/tvq9YEAdD7vCqKjeg8A74.ddBm', 'ROLE_USER', NOW(), NOW()),
('fiona_expert', 'fiona.expert@example.com', '$2a$10$z4J.h4gCvSUkvbYyhqA9n.yGPs/tvq9YEAdD7vCqKjeg8A74.ddBm', 'ROLE_USER', NOW(), NOW()),
('george_newbie', 'george.newbie@example.com', '$2a$10$z4J.h4gCvSUkvbYyhqA9n.yGPs/tvq9YEAdD7vCqKjeg8A74.ddBm', 'ROLE_USER', NOW(), NOW());

INSERT INTO educational_materials (title, topic, reading_time_minutes, author_id, created_at, updated_at) VALUES
('Introduction to Java Programming', 'Java Basics', 30, 1, NOW(), NOW());

INSERT INTO text_blocks (title, content, block_order, educational_material_id) VALUES
('What is Java?', 'Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.', 0, 1),
('Setting up Your Environment', 'To start programming in Java, you need to install the Java Development Kit (JDK). You can download it from the official Oracle website or use OpenJDK.', 1, 1),
('Your First Java Program', 'Let''s write a simple "Hello, World!" program. \n```java\npublic class HelloWorld {\n    public static void main(String[] args) {\n        System.out.println("Hello, World!");\n    }\n}\n```', 2, 1);

INSERT INTO tests (title, educational_material_id) VALUES
('Java Basics Quiz', 1);

INSERT INTO questions (text, question_order, test_id) VALUES
('What does JDK stand for?', 0, 1),
('Which keyword is used to define a class in Java?', 1, 1);

INSERT INTO answer_options (text, is_correct, option_order, question_id) VALUES
('Java Development Kit', TRUE, 0, 1),
('Java Deployment Kit', FALSE, 1, 1),
('Java Design Kit', FALSE, 2, 1),
('Java Debug Kit', FALSE, 3, 1);

INSERT INTO answer_options (text, is_correct, option_order, question_id) VALUES
('class', TRUE, 0, 2),
('void', FALSE, 1, 2),
('static', FALSE, 2, 2),
('public', FALSE, 3, 2);


INSERT INTO educational_materials (title, topic, reading_time_minutes, author_id, created_at, updated_at) VALUES
('Web Development Fundamentals', 'HTML & CSS', 25, 2, NOW(), NOW());

INSERT INTO text_blocks (title, content, block_order, educational_material_id) VALUES
('Understanding HTML', 'HTML (HyperText Markup Language) is the standard markup language for documents designed to be displayed in a web browser. It defines the meaning and structure of web content.', 0, 2),
('Styling with CSS', 'CSS (Cascading Style Sheets) is a style sheet language used for describing the presentation of a document written in a markup language like HTML. CSS describes how elements should be rendered on screen, on paper, in speech, or on other media.', 1, 2);

INSERT INTO educational_materials (title, topic, reading_time_minutes, author_id, created_at, updated_at) VALUES
('Advanced SQL Techniques', 'Databases', 45, 3, NOW(), NOW());

INSERT INTO text_blocks (title, content, block_order, educational_material_id) VALUES
('Window Functions', 'Window functions perform calculations across a set of table rows that are somehow related to the current row. This is comparable to the type of calculation that can be done with an aggregate function. But unlike regular aggregate functions, use of a window function does not cause rows to become grouped into a single output row.', 0, 3),
('Common Table Expressions (CTEs)', 'A CTE allows you to define a temporary, named result set that’s available temporarily in the execution scope of a statement such as SELECT, INSERT, UPDATE, DELETE, or MERGE.', 1, 3),
('Indexing Strategies', 'Proper indexing is crucial for database performance. Learn about different types of indexes like B-tree, Hash, GiST, and GIN, and when to use them.', 2, 3);

INSERT INTO tests (title, educational_material_id) VALUES
('SQL Advanced Quiz', 3);

INSERT INTO questions (text, question_order, test_id) VALUES
('Which type of function calculates across a set of table rows related to the current row without grouping them?', 0, 2),
('What does CTE stand for?', 1, 2);

INSERT INTO answer_options (text, is_correct, option_order, question_id) VALUES
('Aggregate Function', FALSE, 0, 3),
('Window Function', TRUE, 1, 3),
('Scalar Function', FALSE, 2, 3);

INSERT INTO answer_options (text, is_correct, option_order, question_id) VALUES
('Common Table Expression', TRUE, 0, 4),
('Complex Table Entity', FALSE, 1, 4),
('Cascading Table Element', FALSE, 2, 4);


INSERT INTO educational_materials (title, topic, reading_time_minutes, author_id, created_at, updated_at) VALUES
('Introduction to Python', 'Python Basics', 20, 4, NOW(), NOW());

INSERT INTO text_blocks (title, content, block_order, educational_material_id) VALUES
('Why Python?', 'Python is known for its readability and versatility. It''s great for web development, data science, machine learning, and more.', 0, 4),
('Variables and Data Types', 'Learn about integers, floats, strings, booleans, lists, tuples, and dictionaries in Python.', 1, 4);

INSERT INTO educational_materials (title, topic, reading_time_minutes, author_id, created_at, updated_at) VALUES
('Mastering React Components', 'ReactJS', 35, 6, NOW(), NOW());

INSERT INTO text_blocks (title, content, block_order, educational_material_id) VALUES
('Functional Components vs Class Components', 'Understand the differences and when to use each type of component in modern React.', 0, 5),
('State and Props', 'Learn how data flows in React applications using state and props.', 1, 5),
('Lifecycle Methods (Class) / Hooks (Functional)', 'Explore how to manage component lifecycle events and side effects.', 2, 5);

INSERT INTO tests (title, educational_material_id) VALUES
('React Components Quiz', 5);

INSERT INTO questions (text, question_order, test_id) VALUES
('Which hook is used to manage state in a functional component?', 0, 3),
('What is passed down from a parent component to a child component?', 1, 3);

INSERT INTO answer_options (text, is_correct, option_order, question_id) VALUES
('useEffect', FALSE, 0, 5),
('useState', TRUE, 1, 5),
('useContext', FALSE, 2, 5);

INSERT INTO answer_options (text, is_correct, option_order, question_id) VALUES
('State', FALSE, 0, 6),
('Props', TRUE, 1, 6),
('Context', FALSE, 2, 6);

INSERT INTO educational_materials (title, topic, reading_time_minutes, author_id, created_at, updated_at) VALUES
('Understanding Docker for Developers', 'DevOps', 28, 7, NOW(), NOW());

INSERT INTO text_blocks (title, content, block_order, educational_material_id) VALUES
('What are Containers?', 'Containers are a standardized unit of software that packages up code and all its dependencies so the application runs quickly and reliably from one computing environment to another.', 0, 6),
('Dockerfile Basics', 'A Dockerfile is a text document that contains all the commands a user could call on the command line to assemble an image.', 1, 6);

INSERT INTO educational_materials (title, topic, reading_time_minutes, author_id, created_at, updated_at) VALUES
('The Art of Storytelling in Presentations', 'Soft Skills', 15, 9, NOW(), NOW());

INSERT INTO text_blocks (title, content, block_order, educational_material_id) VALUES
('Engaging Your Audience', 'A good story captures attention and makes complex information relatable.', 0, 7),
('Structuring Your Narrative', 'Learn about the classic three-act structure: Setup, Confrontation, and Resolution.', 1, 7);

INSERT INTO tests (title, educational_material_id) VALUES
('Storytelling Quiz', 7);

INSERT INTO questions (text, question_order, test_id) VALUES
('What is the first act in a classic three-act story structure?', 0, 4);

INSERT INTO answer_options (text, is_correct, option_order, question_id) VALUES
('Resolution', FALSE, 0, 7),
('Confrontation', FALSE, 1, 7),
('Setup', TRUE, 2, 7);

INSERT INTO refresh_tokens (user_id, token, expiry_date, revoked) VALUES
(1, 'mock-refresh-token-john', NOW() + INTERVAL '7 day', FALSE),
(2, 'mock-refresh-token-jane', NOW() + INTERVAL '7 day', FALSE),
(3, 'mock-refresh-token-admin', NOW() + INTERVAL '7 day', FALSE);