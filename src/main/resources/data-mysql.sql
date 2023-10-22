Use movieticketbooking;
INSERT INTO user VALUES (1, "https://www.pinterest.com/pin/3166662230759215/", "pitithuong@gmail.com", "thuongmoon", "admin", "thuongmoon");

INSERT INTO genre VALUES(1, "action");
INSERT INTO genre VALUES(2, "horror");
INSERT INTO genre VALUES(3, "comedy");
INSERT INTO genre VALUES(4, "anime");

INSERT INTO movie VALUES(
107, 
1, 
"Kudo Chika, JK.Kei, Romeo James", 
"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", 
"Leon Kenedy",
"https://i.pinimg.com/originals/4d/06/10/4d0610892fe45b35dd06a147bac81fd6.png",
"Call of duty", 
"trailer.mp4",
"https://i.pinimg.com/originals/b4/e0/8a/b4e08a22504a44f42f3fa441e94dc613.jpg"
);

INSERT INTO movie VALUES(
105, 
2, 
"Peter Sattler, David Gordon", 
"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", 
"Jason Blum",
"https://images.thedirect.com/media/article_full/exorcistsb.jpg?imgeng=cmpr_75/",
"The Exorcist: Believer", 
"trailer.mp4",
"https://content.numetro.co.za/image_resized_files/6087-1-1-3-1694000596.jpg"
);

INSERT INTO movie_genre values(1, 1);
INSERT INTO movie_genre values(2, 1);
INSERT INTO movie_genre values(1, 2);

INSERT INTO auditorium values(1, "The sun");

INSERT INTO screening values(1, 1, 1, "2023-07-03 15:00:45", "3D");
INSERT INTO screening values(1, 2, 2, "2023-09-03 20:00:45", "3D")