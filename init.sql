CREATE EXTENSION IF NOT EXISTS dblink;

DO
$do$
DECLARE
   _db text;
BEGIN
   FOREACH _db IN ARRAY STRING_TO_ARRAY(current_setting('myvars.dbs'), ',')
   LOOP
      EXECUTE format('CREATE DATABASE %I', _db);
   END LOOP;
END
$do$;
