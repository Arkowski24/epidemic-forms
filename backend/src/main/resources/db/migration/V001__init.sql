create table choice_field
(
	id bigint not null
		constraint choice_field_pkey
			primary key,
	description varchar(255),
	field_number integer not null,
	field_type integer,
	inline boolean not null,
	multi_choice boolean not null,
	required boolean not null,
	title varchar(255),
	unit varchar(255)
);

alter table choice_field owner to postgres;

create table choice_field_choices
(
	choice_field_id bigint not null
		constraint fkawcp4wgkardw3h4uaf7fymivk
			references choice_field,
	choices varchar(255)
);

alter table choice_field_choices owner to postgres;

create table choice_field_state
(
	id bigint not null
		constraint choice_field_state_pkey
			primary key,
	field_id bigint
		constraint fkb7anmsm708dnkwrvut1ein1hs
			references choice_field
);

alter table choice_field_state owner to postgres;

create table choice_field_state_value
(
	choice_field_state_id bigint not null
		constraint fk36u0ctkwmy9aem29838qk2h0m
			references choice_field_state,
	value boolean
);

alter table choice_field_state_value owner to postgres;

create table derived_field
(
	id bigint not null
		constraint derived_field_pkey
			primary key,
	derived_type integer,
	field_number integer not null,
	field_type integer,
	inline boolean not null
);

alter table derived_field owner to postgres;

create table derived_field_descriptions
(
	derived_field_id bigint not null
		constraint fkd5ivdrtl6gbnow2h0b1fg7y7k
			references derived_field,
	descriptions varchar(255)
);

alter table derived_field_descriptions owner to postgres;

create table derived_field_required
(
	derived_field_id bigint not null
		constraint fk1v2e9fwevnn1g2oy0bnghqf4h
			references derived_field,
	required boolean
);

alter table derived_field_required owner to postgres;

create table derived_field_titles
(
	derived_field_id bigint not null
		constraint fktf5mp42cr8y1juhphdgu6vm83
			references derived_field,
	titles varchar(255)
);

alter table derived_field_titles owner to postgres;

create table derived_field_units
(
	derived_field_id bigint not null
		constraint fk1fsrmot292pn9csp9slyieqr7
			references derived_field,
	units varchar(255)
);

alter table derived_field_units owner to postgres;

create table derived_field_state
(
	id bigint not null
		constraint derived_field_state_pkey
			primary key,
	field_id bigint
		constraint fkful38fycspxwl99fdpnrw5xlg
			references derived_field
);

alter table derived_field_state owner to postgres;

create table derived_field_state_value
(
	derived_field_state_id bigint not null
		constraint fka70i2ix8fkv86yq3a2in0v1d7
			references derived_field_state,
	value varchar(255)
);

alter table derived_field_state_value owner to postgres;

create table employee
(
	id bigint not null
		constraint employee_pkey
			primary key,
	full_name varchar(255),
	password_hash varchar(255),
	role integer,
	username varchar(255)
		constraint ukim8flsuftl52etbhgnr62d6wh
			unique
);

alter table employee owner to postgres;

create table patient
(
	id bigint not null
		constraint patient_pkey
			primary key,
	logged_in boolean not null
);

alter table patient owner to postgres;

create table signature
(
	id bigint not null
		constraint signature_pkey
			primary key,
	created_at timestamp,
	value bytea
);

alter table signature owner to postgres;

create table signature_field
(
	id bigint not null
		constraint signature_field_pkey
			primary key,
	description varchar(255),
	title varchar(255)
);

alter table signature_field owner to postgres;

create table schema
(
	id bigint not null
		constraint schema_pkey
			primary key,
	multi_page boolean not null,
	name varchar(255),
	employee_signature_id bigint
		constraint fkg8p8kswkqgvd5saplodswsdoy
			references signature_field,
	patient_signature_id bigint
		constraint fkfy1bdjil6ptyji4xscspagwfx
			references signature_field
);

alter table schema owner to postgres;

create table form
(
	id bigint not null
		constraint form_pkey
			primary key,
	created_at timestamp,
	form_name varchar(255),
	status integer,
	created_by_id bigint
		constraint fkl92vehxph39ujx7ejqlegv8cb
			references employee,
	device_id bigint
		constraint fksq13m8afxxgqp34smi7qwugh1
			references employee,
	employee_signature_id bigint
		constraint fkt07fv3cs1qoqwgda3sgedggjj
			references signature,
	patient_id bigint
		constraint fkd5ux9s0tvuh6r5lx0u7pj0l6b
			references patient,
	patient_signature_id bigint
		constraint fkc9r4xd2xo8h3q8x2k4jlt5im
			references signature,
	schema_id bigint
		constraint fkmo40f7gndk3hotnb3i6ma8vt9
			references schema,
	signed_by_id bigint
		constraint fkd0mdopd1paekanrmdl85ht7pr
			references employee
);

alter table form owner to postgres;

create table form_choice
(
	form_id bigint not null
		constraint fkenp48kagjxlf8rhqhg492l8fy
			references form,
	choice_id bigint not null
		constraint uk_n87en2y2h9d3gqfl7tixd62s6
			unique
		constraint fkrbdwp0yevu72me5tn5scptl4l
			references choice_field_state
);

alter table form_choice owner to postgres;

create table form_derived
(
	form_id bigint not null
		constraint fka7ryinl1vrubwhosyvyvsg1ii
			references form,
	derived_id bigint not null
		constraint uk_dvi30adsk8mlaon7iglbkw1a6
			unique
		constraint fk6xqqa4qrgymkt2q710a3999bl
			references derived_field_state
);

alter table form_derived owner to postgres;

create table schema_choice
(
	schema_id bigint not null
		constraint fkb60r884msepddgfln0kblwfem
			references schema,
	choice_id bigint not null
		constraint uk_e8u5l0dtey5yicxbnh6v82ah4
			unique
		constraint fkn6s07q95nr5o9kasiopajrd7l
			references choice_field
);

alter table schema_choice owner to postgres;

create table schema_derived
(
	schema_id bigint not null
		constraint fk9aspgx1chxtmbgvami4mf09ud
			references schema,
	derived_id bigint not null
		constraint uk_9giq462dq8u0cvqtrht4bd0uq
			unique
		constraint fkep76o194pjnlafsts2ylskagh
			references derived_field
);

alter table schema_derived owner to postgres;

create table simple_field
(
	id bigint not null
		constraint simple_field_pkey
			primary key,
	description varchar(255),
	field_number integer not null,
	field_type integer,
	inline boolean not null,
	title varchar(255)
);

alter table simple_field owner to postgres;

create table schema_simple
(
	schema_id bigint not null
		constraint fkg7etrf2gblg35ru6865xk3m4p
			references schema,
	simple_id bigint not null
		constraint uk_n280jd02cegcvb4y59vwoc08q
			unique
		constraint fkwcksuawqiia8rv3syfn3serc
			references simple_field
);

alter table schema_simple owner to postgres;

create table slider_field
(
	id bigint not null
		constraint slider_field_pkey
			primary key,
	default_value double precision not null,
	description varchar(255),
	field_number integer not null,
	field_type integer,
	inline boolean not null,
	max_value double precision not null,
	min_value double precision not null,
	required boolean not null,
	step double precision not null,
	title varchar(255),
	unit varchar(255)
);

alter table slider_field owner to postgres;

create table schema_slider
(
	schema_id bigint not null
		constraint fkkulw4kdmir2xj59q9sl2ojwfm
			references schema,
	slider_id bigint not null
		constraint uk_kxfq2j3t7b4juuocjnwvd35hi
			unique
		constraint fkqn8f8aa311sqyl20rvgcid99g
			references slider_field
);

alter table schema_slider owner to postgres;

create table slider_field_state
(
	id bigint not null
		constraint slider_field_state_pkey
			primary key,
	value double precision not null,
	field_id bigint
		constraint fkkwea72iw3f6c4rfc8rr6ru226
			references slider_field
);

alter table slider_field_state owner to postgres;

create table form_slider
(
	form_id bigint not null
		constraint fk5nearoi1it44lqna37d6wa0am
			references form,
	slider_id bigint not null
		constraint uk_ka3fn3jaulduecrpyq24jfauv
			unique
		constraint fktft8r5od1sc3p0ay6siifm4nh
			references slider_field_state
);

alter table form_slider owner to postgres;

create table text_field
(
	id bigint not null
		constraint text_field_pkey
			primary key,
	description varchar(255),
	field_number integer not null,
	field_type integer,
	inline boolean not null,
	multi_line boolean not null,
	required boolean not null,
	title varchar(255),
	unit varchar(255)
);

alter table text_field owner to postgres;

create table schema_text
(
	schema_id bigint not null
		constraint fk4ok6ekuo113jdflw6ox3hxyvv
			references schema,
	text_id bigint not null
		constraint uk_7t5jdhit9v1j2qtq3uwp7kbyc
			unique
		constraint fkkqao3036d7c8o0nysj7tbku5i
			references text_field
);

alter table schema_text owner to postgres;

create table text_field_state
(
	id bigint not null
		constraint text_field_state_pkey
			primary key,
	value varchar(255),
	field_id bigint
		constraint fktgtjikivfwy39eph5nawd9xpl
			references text_field
);

alter table text_field_state owner to postgres;

create table form_text
(
	form_id bigint not null
		constraint fk2rqdi1unp9uiv9b09u0yv3wc6
			references form,
	text_id bigint not null
		constraint uk_odrtfkfo1nfq4efkuosxtra4n
			unique
		constraint fk17hjgcsrmdvs0lj95nxhc6wwp
			references text_field_state
);

alter table form_text owner to postgres;

create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;

