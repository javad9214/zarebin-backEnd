-- chassisNumber optional migration for car_inspections (PostgreSQL).
--
-- REQUIRED before starting any backend build where the chassisNumber entity
-- field is nullable: with ddl-auto=update Hibernate NEVER relaxes an existing
-- NOT NULL constraint, so without this script every INSERT that omits the
-- chassis number fails with SQLState 23502 on chassis_number.
--
-- Run order (no downtime concern — the old jar never writes a null chassis):
--   1. Run this script with psql (single transaction).
--   2. Start the new backend jar. ddl-auto=update sees no DDL to apply.
--
-- Existing rows keep their values; only the constraint is dropped.

BEGIN;

ALTER TABLE car_inspections ALTER COLUMN chassis_number DROP NOT NULL;

COMMIT;

-- Post-deploy verification:
--   SELECT is_nullable FROM information_schema.columns
--    WHERE table_name = 'car_inspections' AND column_name = 'chassis_number';
--   -- expect YES
