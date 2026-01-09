use anikamibd;

select * from mangas;
select * from capitulos;

 select * from generos;
INSERT INTO generos (nombre, descripcion) VALUES
('Acción', 'Combates, peleas y ritmo intenso'),
('Comedia', 'Humor y situaciones divertidas'),
('Romance', 'Relaciones amorosas'),
('Fantasía', 'Elementos mágicos o irreales'),
('Sobrenatural', 'Fantasmas, demonios, poderes'),
('Deportes', 'Competencias deportivas'),
('Drama', 'Conflictos emocionales'),
('Terror', 'Miedo y suspenso'),
('Aventura', 'Viajes y exploración');

select *from autores;
INSERT INTO autores (nombre, descripcion, foto) VALUES
('Yuki Suenaga', 'Mangaka japonés enfocado en comedia romántica y cultura otaku', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766986727/images_loxqkb.jpg'),
('Yūki Tabata', 'Mangaka japonés reconocido por historias de acción y fantasía', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766986779/17573_myt2i3.jpg'),
('Muneyuki Kaneshiro', 'Autor japonés especializado en thrillers psicológicos y drama', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766986829/im-the-real-muneyuki-kaneshiro-author-of-blue-lock-ama-v0-zlo597kydrx91_rcd3e1.webp'),
('Tatsuki Fujimoto', 'Autor japonés conocido por su estilo oscuro, violento y emocional', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766986890/actor-tatsuki-fujimoto-255737_large_qfj7bl.jpg'),
('Yoshitoki Ōima', 'Mangaka japonesa destacada por obras de drama humano y sensibilidad emocional', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766986945/Yoshitoki__3Fima_03_jm13sv.webp'),
('Yukinobu Tatsu', 'Autor japonés con un estilo dinámico que mezcla acción, comedia y lo sobrenatural', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766987005/images_1_t3qeik.jpg'),
('Hiro Mashima', 'Mangaka japonés famoso por mundos de fantasía y personajes carismáticos', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766987051/Hiro_Profile_tg3vpt.webp'),
('Kumo Kagyu', 'Autor japonés centrado en fantasía oscura y narrativas crudas', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766987101/Kumo_Kagyu_GS_Author_g09q3p.webp'),
('Tomoki Izumi', 'Mangaka japonés enfocado en comedia y elementos sobrenaturales', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766987195/Izumi_tomoki_ifsz9y.webp'),
('Homura Kawamoto', 'Autor japonés especializado en juegos mentales y apuestas extremas', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766987154/Homura_q7rpc7.webp'),
('ONE', 'Autor japonés autodidacta con un estilo sencillo y enfoque satírico', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766987237/66748_ooecvx.jpg'),
('Yuto Suzuki', 'Mangaka japonés orientado a la acción con toques de comedia', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766987280/Yuto_Suzuki_o-300x300_j5wknr.png'),
('Shinya Umemura', 'Autor japonés especializado en historias mitológicas y épicas', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766987392/shuumatsu-no-valkyrie-by-takumi-fukui-shinya-umemura-and-v0-ytgb9jiqs8eb1_dpkang.webp'),
('Tatsuya Endo', 'Mangaka japonés que combina espionaje, comedia y drama familiar', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766987438/images_2_ezwlie.jpg'),
('Haro Aso', 'Autor japonés con interés en historias de supervivencia y tensión psicológica', 'https://res.cloudinary.com/dcolydznr/image/upload/v1766987485/Haro_Aso_27s_cameo_in_Season_1_Episode_6_as_a_Beach_member_ranked_071_vionu6.webp');

INSERT INTO mangas (titulo, descripcion, estado, portada_url) VALUES
('2.5-jigen no Ririsa', 'Comedia romántica sobre cosplay y manga', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767052132/DIMESN02-min-1-480x684_rj2ese.jpg'),
('Black Clover', 'Un joven sin magia busca ser el Rey Mago', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767052185/Chapter_72-702x1024-1-480x700_nb8sow.jpg'),
('Blue Lock', 'Proyecto extremo para crear al mejor delantero', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767052221/BL14-min_prh4fu.jpg'),
('Chainsaw Man', 'Cazadores de demonios y violencia extrema', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767052251/cm01-min_dgmmez.jpg'),
('Charlotte', 'Jóvenes con habilidades sobrenaturales', 'Finalizado', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767052282/CHARL01-min-480x683_oet8xi.jpg'),
('Dandadan', 'Aliens, espíritus y comedia absurda', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767052346/615781d50dec6-min_i3usjw.jpg'),
('Fairy Tail: 100 Years Quest', 'La misión más peligrosa de Fairy Tail', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767052374/Fairy-Tail-05-min-480x718_vqbtg9.jpg'),
('Goblin Slayer', 'Cazador obsesionado con exterminar goblins', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767052407/gsyo01-min_epnmfj.jpg'),
('Haite Kudasai, Takamine-san', 'Comedia romántica ecchi con elementos sobrenaturales', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767052576/mXL-min-480x683_uckrmy.jpg'),
('Kakegurui', 'Apuestas extremas en una academia', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767052617/332456_s0-min-480x688_oluejo.jpg'),
('Keiken-chi chochiku de nonbiri', 'Aventurero expulsado de su grupo S-Rank comienza de nuevo', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767052959/6042d44612203_zu4rua.webp'),
('Killing Bites', 'Batallas brutales entre híbridos humanos y animales', 'Finalizado', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053142/1-min-1-480x650-1_itniyc.jpg'),
('Mamahaha no Tsurego ga Motokano datta', 'Comedia romántica entre exnovios que ahora son hermanastros', 'Finalizado', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053184/mamahahav1_hgwdhv.jpg'),
('Megami no Café Terrace', 'Romance y comedia en un café heredado', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053230/602c52900e281-min_hsvhfx.jpg'),
('Mieruko-chan', 'Chica que puede ver espíritus y los ignora', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053274/mieruko05_gpqrx0.jpg'),
('Nozomanu Fushi no Boukensha', 'Aventurero renace como no-muerto y busca evolucionar', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053333/60d8a90c7208f-min_ymxuqb.jpg'),
('One Punch Man', 'Héroe invencible aburrido por su poder', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053333/60d8a90c7208f-min_ymxuqb.jpg'),
('Otonari no Tenshi-sama', 'Romance dulce entre vecinos', 'Finalizado', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053408/63d1b481bed5e-min_k17ubu.jpg'),
('Sakamoto Days', 'Asesino legendario retirado y padre de familia', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053437/606d97f6c7e83-min_tbmwvf.jpg'),
('SPY×FAMILY', 'Familia falsa para misión secreta', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053502/sxf-min_oig2rs.jpg'),
('Tawawa on Monday', 'Historias cortas románticas y slice of life', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053539/tawa01-min_pz2xx4.jpg'),
('Tougen Anki', 'Batallas entre descendientes de Oni y Momotaro', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053571/5fe2b578b3968-min_kuuusp.jpg'),
('Yancha Gal no Anjo-san', 'Romance entre una gal extrovertida y un chico tímido', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053607/YNG-min-480x675_crrin7.jpg'),
('Zom 100: Zombie ni Naru made ni Shitai 100 no Koto', 'Sobrevivir al apocalipsis zombie con lista de deseos', 'En emisión', 'https://res.cloudinary.com/dcolydznr/image/upload/v1767053633/63c2e9e03ff77-min_rzwb90.jpg');

INSERT INTO manga_autor (manga_id, autor_id) VALUES
(1, 1),   -- 2.5-jigen no Ririsa → Yuki Suenaga
(2, 2),   -- Black Clover → Yūki Tabata
(3, 3),   -- Blue Lock → Muneyuki Kaneshiro
(4, 4),   -- Chainsaw Man → Tatsuki Fujimoto
(5, 5),   -- Charlotte → Yoshitoki Ōima
(6, 6),   -- Dandadan → Yukinobu Tatsu
(7, 7),   -- Fairy Tail: 100 Years Quest → Hiro Mashima
(8, 8),   -- Goblin Slayer → Kumo Kagyu
(9, 9),   -- Haite Kudasai, Takamine-san → Tomoki Izumi
(10,10),  -- Kakegurui → Homura Kawamoto
(11,8),   -- Keiken-chi chochiku de nonbiri → Kumo Kagyu
(12,15),  -- Killing Bites → Haro Aso
(13,1),   -- Mamahaha no Tsurego ga Motokano datta → Yuki Suenaga
(14,7),   -- Megami no Café Terrace → Hiro Mashima
(15,9),   -- Mieruko-chan → Tomoki Izumi
(16,8),   -- Nozomanu Fushi no Boukensha → Kumo Kagyu
(17,11),  -- One Punch Man → ONE
(18,5),   -- Otonari no Tenshi-sama → Yoshitoki Ōima
(19,12),  -- Sakamoto Days → Yuto Suzuki
(20,13),  -- Shuumatsu no Valkyrie → Shinya Umemura
(21,14),  -- SPY×FAMILY → Tatsuya Endo
(22,1),   -- Tawawa on Monday → Yuki Suenaga
(23,15),  -- Tougen Anki → Haro Aso
(24,9),   -- Yancha Gal no Anjo-san → Tomoki Izumi
(25,15);  -- Zom 100 → Haro Aso


SELECT * FROM manga_genero;
INSERT INTO manga_genero (manga_id, genero_id) VALUES
(1, 2), (1, 3),
(2, 1), (2, 4), (2, 9),
(3, 6), (3, 7),
(4, 1), (4, 5), (4, 8),
(5, 5), (5, 7), (5, 3),
(6, 1), (6, 2), (6, 5),
(7, 1), (7, 4), (7, 9),
(8, 1), (8, 4), (8, 8),
(9, 2), (9, 3), (9, 5),
(10, 7), (10, 8),
(11, 4), (11, 9), (11, 1),
(12, 1), (12, 8),
(13, 2), (13, 3), (13, 7),
(14, 2), (14, 3),
(15, 2), (15, 5), (15, 8),
(16, 4), (16, 9), (16, 1),
(17, 1), (17, 2),
(18, 3), (18, 7),
(19, 1), (19, 2),
(20, 1), (20, 4), (20, 7),
(21, 2), (21, 1), (21, 3),
(22, 2), (22, 3),
(23, 1), (23, 4), (23, 8),
(24, 2), (24, 3),
(25, 1), (25, 2), (25, 8);
