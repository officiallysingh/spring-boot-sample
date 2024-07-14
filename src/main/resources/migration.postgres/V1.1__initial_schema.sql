CREATE TABLE IF NOT EXISTS revisions
(
    id integer NOT NULL,
    "timestamp" bigint NOT NULL,
    actor character varying(255) NOT NULL,
    datetime timestamp(6) with time zone NOT NULL,
    CONSTRAINT revisions_pkey PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_rev_datetime
    ON revisions (datetime ASC NULLS LAST);

CREATE TABLE IF NOT EXISTS employees
(
    id uuid NOT NULL,
    version bigint NOT NULL,
    code character varying(10) COLLATE pg_catalog."default" NOT NULL,
    dob date NOT NULL,
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pkey_employees_id PRIMARY KEY (id),
    CONSTRAINT unq_employees_code UNIQUE (code)
)

CREATE INDEX IF NOT EXISTS idx_employees_code
    ON employees (code ASC NULLS LAST);

CREATE TABLE IF NOT EXISTS employees_aud
(
    id uuid NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    code character varying(20),
    dob date,
    name character varying(50),
    CONSTRAINT pkey_employees_aud PRIMARY KEY (rev, id),
    CONSTRAINT fkemployees_aud_rev FOREIGN KEY (rev)
    REFERENCES revisions (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
)