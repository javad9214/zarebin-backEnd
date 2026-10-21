-- Split the single body-panel "chassis" flag into front/rear chassis and add
-- one "pillars" (ستون‌ها) flag (PostgreSQL).

-- REQUIRED before deploying any backend build that contains the new entity
-- fields: the deploy environment does not apply ddl-auto=update DDL, so
-- without these columns every insert/update of an inspection fails with
-- SQLState 42703 (column front_chassis does not exist).

-- Run order (no downtime concern — the old jar never writes the new columns):
--   1. psql -h localhost -U <user> -d zarebin -f front-rear-chassis-pillars-migration.sql
--   2. deploy the new jar
--   3. deploy the updated python PDF service (schema must accept the new
--      fields or PDF generation silently skips them)

ALTER TABLE car_inspections ADD COLUMN IF NOT EXISTS front_chassis boolean;
ALTER TABLE car_inspections ADD COLUMN IF NOT EXISTS rear_chassis  boolean;
ALTER TABLE car_inspections ADD COLUMN IF NOT EXISTS pillars       boolean;

-- Conservative backfill: an inspection whose old single chassis flag was
-- damaged gets BOTH the front and rear chassis flagged, so no damage is lost.
UPDATE car_inspections
   SET front_chassis = TRUE,
       rear_chassis  = TRUE
 WHERE chassis IS TRUE;

-- The legacy chassis column is intentionally kept (not dropped) so a rollback
-- to the previous jar keeps working. Cleanup once the new jar is confirmed
-- stable — uncomment and rerun just these statements:
-- ALTER TABLE car_inspections DROP COLUMN IF EXISTS chassis;

-- If an earlier draft of this migration (four per-corner pillar columns) was
-- already applied, those columns are unused now and can be dropped too:
-- ALTER TABLE car_inspections DROP COLUMN IF EXISTS front_right_pillar;
-- ALTER TABLE car_inspections DROP COLUMN IF EXISTS front_left_pillar;
-- ALTER TABLE car_inspections DROP COLUMN IF EXISTS rear_right_pillar;
-- ALTER TABLE car_inspections DROP COLUMN IF EXISTS rear_left_pillar;

-- Sanity check afterwards:
--    SELECT column_name FROM information_schema.columns
--    WHERE table_name = 'car_inspections'
--      AND column_name IN ('front_chassis', 'rear_chassis', 'pillars');
