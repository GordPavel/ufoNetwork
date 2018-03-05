CREATE SCHEMA ufonetwork;

CREATE SEQUENCE ufonetwork.group_category_id_seq START WITH 1;

CREATE SEQUENCE ufonetwork.group_id_seq START WITH 1;

CREATE SEQUENCE ufonetwork.message_id_seq START WITH 1;

CREATE SEQUENCE ufonetwork.person_id_seq START WITH 1;

CREATE SEQUENCE ufonetwork.planet_id_seq START WITH 1;

CREATE SEQUENCE ufonetwork.race_id_seq START WITH 1;

CREATE TABLE ufonetwork.category ( 
	id                   serial  NOT NULL,
	name                 text  NOT NULL,
	CONSTRAINT pk_group_category_id PRIMARY KEY ( id )
 );

COMMENT ON TABLE ufonetwork.category IS 'list of categories of all groups. Data filled by DB admin';

COMMENT ON COLUMN ufonetwork.category.name IS 'Unique name of category (ignoring case)';

CREATE TABLE ufonetwork.planet ( 
	id                   serial  NOT NULL,
	name                 text  NOT NULL,
	CONSTRAINT planet_id_primary PRIMARY KEY ( id ),
	CONSTRAINT planet_unique_name UNIQUE ( name ) 
 );

COMMENT ON TABLE ufonetwork.planet IS 'list of planets. Users can''t manipulate with values in table, only read';

COMMENT ON COLUMN ufonetwork.planet.name IS 'Unique name of planet ( ignoring case)';

CREATE TABLE ufonetwork.race ( 
	id                   serial  NOT NULL,
	name                 text  NOT NULL,
	CONSTRAINT pk_race_id PRIMARY KEY ( id ),
	CONSTRAINT race_unique_name UNIQUE ( name ) 
 );

COMMENT ON TABLE ufonetwork.race IS 'List of different races. Each person could enter his race or choose from list';

COMMENT ON COLUMN ufonetwork.race.name IS 'Unique name of race ( ignoring case ).';

CREATE TABLE ufonetwork.person ( 
	id                   serial  NOT NULL,
	login                text  NOT NULL,
	pass                 text  NOT NULL,
	date_of_registration date DEFAULT CURRENT_DATE NOT NULL,
	sex                  text  ,
	age                  integer  ,
	media                bytea  ,
	race                 integer  NOT NULL,
	planet               integer  ,
	CONSTRAINT pk_person_id PRIMARY KEY ( id ),
	CONSTRAINT unique_person_login UNIQUE ( login ) ,
	CONSTRAINT fk_person_planet FOREIGN KEY ( planet ) REFERENCES ufonetwork.planet( id ) ON DELETE SET NULL ON UPDATE CASCADE,
	CONSTRAINT fk_person_race FOREIGN KEY ( race ) REFERENCES ufonetwork.race( id )  ON UPDATE CASCADE
 );

CREATE INDEX idx_person_planet ON ufonetwork.person ( planet );

CREATE INDEX idx_person_race ON ufonetwork.person ( race );

COMMENT ON TABLE ufonetwork.person IS 'List of all users of system';

COMMENT ON COLUMN ufonetwork.person.login IS 'unique login of each person';

COMMENT ON COLUMN ufonetwork.person.pass IS 'Encrypted in SHA-256 password';

COMMENT ON COLUMN ufonetwork.person.sex IS 'any symbols combination ( we guess there are more then only 2 sexes in galaxy )';

COMMENT ON COLUMN ufonetwork.person.media IS 'Optional png pic of user';

CREATE TABLE ufonetwork."group" ( 
	id                   serial  NOT NULL,
	name                 text  NOT NULL,
	media                bytea  ,
	owner_group          integer  NOT NULL,
	category             integer  ,
	CONSTRAINT pk_group_id PRIMARY KEY ( id ),
	CONSTRAINT unique_planet_name UNIQUE ( name ) ,
	CONSTRAINT fk_group_group_category FOREIGN KEY ( category ) REFERENCES ufonetwork.category( id ) ON DELETE SET NULL ON UPDATE CASCADE,
	CONSTRAINT fk_group_person FOREIGN KEY ( owner_group ) REFERENCES ufonetwork.person( id ) ON DELETE CASCADE ON UPDATE CASCADE
 );

CREATE INDEX idx_group_category ON ufonetwork."group" ( category );

CREATE INDEX idx_group_owner_group ON ufonetwork."group" ( owner_group );

COMMENT ON TABLE ufonetwork."group" IS 'list of groups';

COMMENT ON COLUMN ufonetwork."group".owner_group IS 'person who made this group';

CREATE TABLE ufonetwork.message ( 
	id                   serial  NOT NULL,
	date_of_submition    date DEFAULT CURRENT_DATE NOT NULL,
	text                 text  NOT NULL,
	media                bytea  ,
	writer               integer  ,
	to_group             integer  NOT NULL,
	CONSTRAINT pk_message_id PRIMARY KEY ( id ),
	CONSTRAINT fk_message_group FOREIGN KEY ( to_group ) REFERENCES ufonetwork."group"( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_message_person FOREIGN KEY ( writer ) REFERENCES ufonetwork.person( id )  ON UPDATE CASCADE
 );

CREATE INDEX idx_message_to_group ON ufonetwork.message ( to_group );

CREATE INDEX idx_message_writer ON ufonetwork.message ( writer );

CREATE TABLE ufonetwork.person_group ( 
	person               integer  NOT NULL,
	"group"              integer  NOT NULL,
	CONSTRAINT _0 PRIMARY KEY ( person, "group" ),
	CONSTRAINT fk_person_group_group FOREIGN KEY ( "group" ) REFERENCES ufonetwork."group"( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_person_group_person FOREIGN KEY ( person ) REFERENCES ufonetwork.person( id )  ON UPDATE CASCADE
 );

CREATE INDEX idx_person_group_group ON ufonetwork.person_group ( "group" );

CREATE INDEX idx_person_group_person ON ufonetwork.person_group ( person );

CREATE OR REPLACE FUNCTION ufonetwork.add_user(text, text, text, integer, integer, integer)
 RETURNS void
 LANGUAGE sql
AS $function$ insert into person( login , pass , sex , age, race , planet ) values( $1, $2 , $3 , $4 , $5 , $6 ); $function$
