CREATE SEQUENCE ufonetwork.message_id_seq START WITH 1;

CREATE TABLE ufonetwork.planet ( 
	name                 text  NOT NULL,
	age                  integer  ,
	CONSTRAINT pk_planet_name PRIMARY KEY ( name )
 );

CREATE TABLE ufonetwork.race ( 
	name                 text  NOT NULL,
	CONSTRAINT pk_race_name PRIMARY KEY ( name )
 );

CREATE TABLE ufonetwork.person ( 
	login                text  NOT NULL,
	pass                 text  NOT NULL,
	date_of_registration date DEFAULT CURRENT_DATE NOT NULL,
	sex                  text  ,
	age                  integer  ,
	race                 text  NOT NULL,
	planet               text  ,
	group_of_person      text  ,
	media                bytea  ,
	CONSTRAINT pk_person_login PRIMARY KEY ( login )
 );

CREATE INDEX idx_person_planet ON ufonetwork.person ( planet );

CREATE INDEX idx_person_race ON ufonetwork.person ( race );

COMMENT ON COLUMN ufonetwork.person.login IS 'unique login of each person';

COMMENT ON COLUMN ufonetwork.person.pass IS 'encoded password';

COMMENT ON COLUMN ufonetwork.person.sex IS 'any symbols combination ( we guess there are more then only 2 sexes in galaxy )';

CREATE TABLE ufonetwork."group" ( 
	name                 text  NOT NULL,
	owner_of_group       text  NOT NULL,
	media                bytea  ,
	CONSTRAINT pk_group_name PRIMARY KEY ( name )
 );

CREATE INDEX idx_group_owner_of_group ON ufonetwork."group" ( owner_of_group );

COMMENT ON TABLE ufonetwork."group" IS 'list of groups';

CREATE TABLE ufonetwork.message ( 
	id                   serial  NOT NULL,
	writer               text  NOT NULL,
	to_group             text  NOT NULL,
	date_of_departure    date DEFAULT CURRENT_DATE NOT NULL,
	text                 text  NOT NULL,
	media                bytea  ,
	CONSTRAINT pk_message_id PRIMARY KEY ( id )
 );

CREATE INDEX idx_message_to_group ON ufonetwork.message ( to_group );

CREATE INDEX idx_message_writer ON ufonetwork.message ( writer );

CREATE TABLE ufonetwork.person_group ( 
	peson                text  NOT NULL,
	name_of_group        text  NOT NULL
 );

CREATE INDEX idx_person_group_name_of_group ON ufonetwork.person_group ( name_of_group );

CREATE INDEX idx_person_group_peson ON ufonetwork.person_group ( peson );

COMMENT ON TABLE ufonetwork.person_group IS 'connecting table of for persons and groups';

ALTER TABLE ufonetwork."group" ADD CONSTRAINT fk_group_person FOREIGN KEY ( owner_of_group ) REFERENCES ufonetwork.person( login ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ufonetwork.message ADD CONSTRAINT fk_message_group FOREIGN KEY ( to_group ) REFERENCES ufonetwork."group"( name ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ufonetwork.message ADD CONSTRAINT fk_message_person FOREIGN KEY ( writer ) REFERENCES ufonetwork.person( login ) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE ufonetwork.person ADD CONSTRAINT planet FOREIGN KEY ( planet ) REFERENCES ufonetwork.planet( name ) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE ufonetwork.person ADD CONSTRAINT fk_person_race FOREIGN KEY ( race ) REFERENCES ufonetwork.race( name ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ufonetwork.person_group ADD CONSTRAINT fk_person_group FOREIGN KEY ( name_of_group ) REFERENCES ufonetwork."group"( name ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ufonetwork.person_group ADD CONSTRAINT fk_person_group_person FOREIGN KEY ( peson ) REFERENCES ufonetwork.person( login ) ON DELETE CASCADE ON UPDATE CASCADE;

