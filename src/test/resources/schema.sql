CREATE TABLE delivery (
    id_ter integer,
    id_wares integer,
    id_quarter double precision,
    num_deliv integer,
    sum_deliv integer
);

CREATE TABLE employee (
    id_employee integer NOT NULL,
    id_subdiv integer NOT NULL,
    full_name character(50) NOT NULL,
    pr_sex smallint NOT NULL,
    emp_type smallint DEFAULT 0 NOT NULL,
    date_born date NOT NULL
);

CREATE TABLE realizations (
    id_wares integer,
    id_employee integer NOT NULL,
    date_realiz date NOT NULL,
    num_realiz real DEFAULT 1::real NOT NULL,
    sum_realiz real NOT NULL,
    pc_discount smallint DEFAULT 0 NOT NULL,
    price real NOT NULL
);

CREATE TABLE store (
    id_store integer NOT NULL,
    id_ter integer NOT NULL,
    naim_store character(30) NOT NULL
);

CREATE TABLE subdivision (
    id_subdiv integer NOT NULL,
    id_store integer NOT NULL,
    id_subdiv_parent integer NOT NULL,
    naim_subdiv character(50) NOT NULL,
    id_type_subdiv integer NOT NULL,
    id_type_wares integer,
    rub_subdiv character(20) NOT NULL
);

CREATE TABLE support (
    typ character(1) DEFAULT 'R'::bpchar NOT NULL,
    id integer NOT NULL,
    id_parent integer NOT NULL,
    naim character varying(50) NOT NULL,
    pnom smallint NOT NULL,
    id_gen character varying(20) NOT NULL,
    to_level character(20) DEFAULT ''::bpchar NOT NULL
);

CREATE TABLE teritory (
    id_ter integer NOT NULL,
    id_ter_parent integer DEFAULT 0 NOT NULL,
    naim_ter character(40) NOT NULL,
    pr_ter smallint NOT NULL,
    rub_terit character(12) NOT NULL
);

CREATE TABLE type_subdiv (
    id_type_subdiv integer NOT NULL,
    naim_type_subdiv character(30) NOT NULL
);

CREATE TABLE type_wares (
    id_type_wares integer NOT NULL,
    id_type_wares_parent integer NOT NULL,
    naim_type_wares character(40) NOT NULL,
    rub_type_wares character(12) NOT NULL
);

CREATE TABLE vendor (
    id_vendor integer NOT NULL,
    id_ter integer NOT NULL,
    naim_vendor character(30) NOT NULL
);

CREATE TABLE wares (
    id_wares integer NOT NULL,
    id_vendor integer NOT NULL,
    id_type_wares integer,
    naim_wares character(30) NOT NULL,
    price_wares real NOT NULL
);

CREATE TABLE realizations
(
  id_wares integer,
  id_employee integer NOT NULL,
  date_realiz date NOT NULL,
  num_realiz real NOT NULL,
  sum_realiz real NOT NULL,
  pc_discount smallint NOT NULL,
  price real NOT NULL
);