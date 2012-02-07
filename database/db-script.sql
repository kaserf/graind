DROP TABLE "favourite" CASCADE;
DROP TABLE "daily_template" CASCADE;
DROP TABLE "monthly_template" CASCADE;
DROP TABLE "yearly_template" CASCADE;
DROP TABLE "template_visibility" CASCADE;
DROP TABLE "user" CASCADE;
DROP TABLE "template_type" CASCADE;
DROP TABLE "template" CASCADE;

CREATE TABLE "user"
(
  "id" serial,
  "account" character varying(40) NOT NULL,
  "name" character varying(60),
  "default_template" integer,
  "default_type" integer DEFAULT (0) NOT NULL,
  "default_refreshrate" integer DEFAULT 1 NOT NULL,
  "last_refresh" date DEFAULT now()::date NOT NULL,
  CONSTRAINT user_pkey PRIMARY KEY ("id")
);

CREATE TABLE "favourite"
(
  "template" integer NOT NULL,
  "user" integer NOT NULL,
  CONSTRAINT favourites_pkey PRIMARY KEY ("template" , "user" )
);

CREATE TABLE "template"
(
  "id" serial,
  "creator" integer NOT NULL,
  "type" integer NOT NULL,
  "visibility" integer NOT NULL,
  CONSTRAINT templates_pkey PRIMARY KEY ("id")
);

CREATE TABLE "yearly_template"
(
  "template" integer NOT NULL,
  "month" smallint NOT NULL,
  "picture_url" character varying(255),
  CONSTRAINT yearly_templates_pkey PRIMARY KEY ("template", "month")
);

CREATE TABLE "monthly_template"
(
  "template" integer NOT NULL,
  "week" smallint NOT NULL,
  "picture_url" character varying(255),
  CONSTRAINT monthly_templates_pkey PRIMARY KEY ("template", "week")
);

CREATE TABLE "daily_template"
(
  "template" integer NOT NULL,
  "day" smallint NOT NULL,
  "picture_url" character varying(255),
  CONSTRAINT daily_templates_pkey PRIMARY KEY ("template", "day")
);

CREATE TABLE "template_visibility"
(
  "id" serial,
  "visibility" character varying(7),
  CONSTRAINT template_visibility_pkey PRIMARY KEY ("id")
);

CREATE TABLE "template_type"
(
  "id" serial,
  "type" character varying(7),
  CONSTRAINT template_type_pkey PRIMARY KEY ("id")
);

-- foreign keys

ALTER TABLE daily_template
  ADD CONSTRAINT daily_template_fk
      FOREIGN KEY ("template") REFERENCES "template" (id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE monthly_template
  ADD CONSTRAINT monthly_template_fk
      FOREIGN KEY ("template") REFERENCES "template" (id) ON UPDATE NO ACTION ON DELETE CASCADE;
      
ALTER TABLE yearly_template
  ADD CONSTRAINT yearly_template_fk
      FOREIGN KEY ("template") REFERENCES "template" (id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE favourite
  ADD CONSTRAINT favourite_template_fk
      FOREIGN KEY ("template") REFERENCES "template" (id) ON UPDATE NO ACTION ON DELETE CASCADE,
  ADD CONSTRAINT favourite_user_fk
      FOREIGN KEY ("user") REFERENCES "user" (id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE "user"
  ADD CONSTRAINT user_default_template_fk
      FOREIGN KEY (default_template) REFERENCES "template" (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
  ADD CONSTRAINT user_default_type_fk
      FOREIGN KEY (default_type) REFERENCES "template_type" (id) ON UPDATE NO ACTION ON DELETE RESTRICT;

ALTER TABLE "template"
  ADD CONSTRAINT template_user_fk
      FOREIGN KEY (creator) REFERENCES "user" (id) ON UPDATE NO ACTION ON DELETE CASCADE,
  ADD CONSTRAINT template_type_fk
      FOREIGN KEY (type) REFERENCES "template_type" (id) ON UPDATE NO ACTION ON DELETE RESTRICT,
  ADD CONSTRAINT template_visibility_fk
      FOREIGN KEY (visibility) REFERENCES "template_visibility" (id) ON UPDATE NO ACTION ON DELETE RESTRICT;



-- default values
INSERT INTO "template_visibility" (visibility) VALUES ('private');
INSERT INTO "template_visibility" (visibility) VALUES ('friends');
INSERT INTO "template_visibility" (visibility) VALUES ('public');


INSERT INTO "template_type" ("type") VALUES ('random');
INSERT INTO "template_type" ("type") VALUES ('fixed');



-- views

-- DROP VIEW latest_templates;


-- latest 100 templates
CREATE OR REPLACE VIEW latest_templates AS 
 SELECT tmp.id, u.id AS creator_id, u.name AS creator_name, t.type
   FROM template tmp, template_type t, template_visibility v, "user" u
  WHERE tmp.visibility = v.id AND v.visibility::text = 'public'::text AND tmp.creator = u.id AND tmp.type = t.id
  ORDER BY tmp.id
 LIMIT 100;


-- todays urls for each fixed template


-- daily urls for today
SELECT tmp.id, tmp.creator, tmp.type, tmp.visibility, dt.picture_url, 'daily'::text as grain
  FROM template tmp, template_type t, daily_template dt
  WHERE
   tmp.type = t.id
   AND
    t.type::text = 'fixed'::text
   AND
    dt.day = EXTRACT(DOY FROM now()) 

