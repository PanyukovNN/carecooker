ALTER TABLE recipe ALTER COLUMN serving TYPE integer USING (serving::integer);
