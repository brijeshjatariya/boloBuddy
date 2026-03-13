DROP TABLE IF EXISTS conversation_logs;
DROP TABLE IF EXISTS sessions;
DROP TABLE IF EXISTS child_vocabulary;
DROP TABLE IF EXISTS vocabulary;
DROP TABLE IF EXISTS children;

CREATE TABLE children (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL CHECK (age BETWEEN 3 AND 10),
    avatar_color VARCHAR(20) DEFAULT 'blue',
    level INTEGER DEFAULT 1,
    total_stars INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE vocabulary (
    id BIGSERIAL PRIMARY KEY,
    english_word VARCHAR(100) NOT NULL,
    hindi_word VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    difficulty_level INTEGER DEFAULT 1 CHECK (difficulty_level BETWEEN 1 AND 5),
    emoji VARCHAR(10),
    example_sentence VARCHAR(200)
);

CREATE TABLE child_vocabulary (
    id BIGSERIAL PRIMARY KEY,
    child_id BIGINT REFERENCES children(id),
    vocabulary_id BIGINT REFERENCES vocabulary(id),
    attempts INTEGER DEFAULT 0,
    correct_count INTEGER DEFAULT 0,
    accuracy_percent INTEGER DEFAULT 0,
    is_mastered BOOLEAN DEFAULT FALSE,
    last_practiced TIMESTAMP DEFAULT NOW()
);

CREATE TABLE sessions (
    id BIGSERIAL PRIMARY KEY,
    child_id BIGINT REFERENCES children(id),
    started_at TIMESTAMP DEFAULT NOW(),
    ended_at TIMESTAMP,
    duration_minutes INTEGER,
    words_practiced INTEGER DEFAULT 0,
    stars_earned INTEGER DEFAULT 0,
    topic VARCHAR(100)
);

CREATE TABLE conversation_logs (
    id BIGSERIAL PRIMARY KEY,
    session_id BIGINT REFERENCES sessions(id),
    child_id BIGINT REFERENCES children(id),
    child_message TEXT,
    ai_response TEXT,
    topic VARCHAR(100),
    created_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO vocabulary (english_word, hindi_word, category, difficulty_level, emoji, example_sentence) VALUES
('Apple', 'सेब', 'Fruits', 1, '🍎', 'This is an apple.'),
('Mango', 'आम', 'Fruits', 1, '🥭', 'I like mango.'),
('Banana', 'केला', 'Fruits', 1, '🍌', 'The banana is yellow.'),
('Cat', 'बिल्ली', 'Animals', 1, '🐱', 'The cat says meow.'),
('Dog', 'कुत्ता', 'Animals', 1, '🐶', 'The dog is happy.'),
('Cow', 'गाय', 'Animals', 1, '🐄', 'The cow gives milk.'),
('Red', 'लाल', 'Colors', 1, '🔴', 'The apple is red.'),
('Blue', 'नीला', 'Colors', 1, '🔵', 'The sky is blue.'),
('Yellow', 'पीला', 'Colors', 1, '💛', 'The sun is yellow.'),
('One', 'एक', 'Numbers', 1, '1️⃣', 'I have one apple.'),
('Two', 'दो', 'Numbers', 1, '2️⃣', 'I have two cats.'),
('Three', 'तीन', 'Numbers', 1, '3️⃣', 'Three birds are flying.'),
('Mother', 'माँ', 'Family', 1, '👩', 'My mother is kind.'),
('Father', 'पिताजी', 'Family', 1, '👨', 'My father works hard.'),
('School', 'स्कूल', 'Places', 2, '🏫', 'I go to school.'),
('Home', 'घर', 'Places', 1, '🏠', 'My home is big.'),
('Water', 'पानी', 'Food', 1, '💧', 'I drink water.'),
('Milk', 'दूध', 'Food', 1, '🥛', 'Milk is white.'),
('Sun', 'सूरज', 'Nature', 1, '☀️', 'The sun is bright.'),
('Moon', 'चाँद', 'Nature', 1, '🌙', 'The moon is round.'),
('Book', 'किताब', 'Objects', 1, '📚', 'I read a book.'),
('Ball', 'गेंद', 'Objects', 1, '⚽', 'I play with a ball.'),
('Happy', 'खुश', 'Feelings', 2, '😊', 'I am happy today.'),
('Sad', 'दुखी', 'Feelings', 2, '😢', 'The dog is sad.'),
('Big', 'बड़ा', 'Adjectives', 2, '🐘', 'The elephant is big.'),
('Small', 'छोटा', 'Adjectives', 2, '🐭', 'The mouse is small.'),
('Run', 'दौड़ना', 'Actions', 2, '🏃', 'I can run fast.'),
('Jump', 'कूदना', 'Actions', 2, '🦘', 'I love to jump.'),
('Eat', 'खाना', 'Actions', 1, '🍽️', 'I eat food.'),
('Sleep', 'सोना', 'Actions', 1, '😴', 'I sleep at night.');
