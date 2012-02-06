DROP TABLE images;

CREATE TABLE images
(
  user_id varchar(260) NOT NULL,
  month smallint NOT NULL CHECK (month <= 11 AND month >= 0),
  image_url varchar(500),
  image_height int,
  image_width int,
  CONSTRAINT pkey_images PRIMARY KEY (user_id, month)
);

CREATE INDEX user_index ON images (user_id);
