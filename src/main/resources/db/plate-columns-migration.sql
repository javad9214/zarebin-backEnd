-- PlateNumber embeddable → column migration for car_inspections (PostgreSQL).
--
-- REQUIRED before starting any backend build that contains the PlateNumber
-- embeddable: with ddl-auto=update Hibernate tries to add these columns as
-- NOT NULL with no default, which PostgreSQL rejects on a non-empty table
-- ("column ... contains null values") and the app fails to boot.
--
-- Run order matters:
--   1. STOP the old backend (once columns are NOT NULL, the old jar's INSERT
--      no longer works; and the NOT NULL step can fail if the old jar writes
--      rows in between backfill and constraint).
--   2. Run this whole script (it is a single transaction).
--   3. Start the new backend jar. ddl-auto=update sees the columns already
--      exist and issues no DDL.
--
-- The backfill parses every historical plate_number format: the app's
-- " ایران 12 345 ب 67", the legacy picker "12 ب 345 - ایران 67", bare
-- "12 345 ب 67", and Persian/Arabic digits. Digit groups map in order to
-- firstTwo/threeDigits/iranCode; the single non-digit character is the
-- letter. Unparseable rows are left with empty parts — the check query at
-- the end lists them; fix by hand before applying NOT NULL.

BEGIN;

ALTER TABLE car_inspections ADD COLUMN IF NOT EXISTS plate_first_two VARCHAR(2);
ALTER TABLE car_inspections ADD COLUMN IF NOT EXISTS plate_letter    VARCHAR(1);
ALTER TABLE car_inspections ADD COLUMN IF NOT EXISTS plate_three     VARCHAR(3);
ALTER TABLE car_inspections ADD COLUMN IF NOT EXISTS plate_iran_code VARCHAR(2);

-- The old plate_number column stays for historical rows, but the entity no
-- longer maps it, so new INSERTs supply no value. Its NOT NULL (from the
-- days of the flat required String plateNumber) must go, otherwise every
-- insert from the new jar fails with SQLState 23502 on plate_number.
ALTER TABLE car_inspections ALTER COLUMN plate_number DROP NOT NULL;

UPDATE car_inspections c SET
  plate_first_two = left(COALESCE(p.d[1], ''), 2),
  plate_three     = left(COALESCE(p.d[2], ''), 3),
  plate_iran_code = left(COALESCE(p.d[3], ''), 2),
  plate_letter    = left(COALESCE(p.l, ''), 1)
FROM car_inspections c2,
     LATERAL (SELECT translate(translate(c2.plate_number, '۰۱۲۳۴۵۶۷۸۹', '0123456789'),
                               '٠١٢٣٤٥٦٧٨٩', '0123456789') AS cleaned) x,
     LATERAL (SELECT regexp_split_to_array(
                       btrim(regexp_replace(x.cleaned, '[^0-9]+', ' ', 'g')), ' ') AS d,
                    substring(regexp_replace(x.cleaned, '[0-9\s\-]+', '', 'g')
                              FROM 1 FOR 1) AS l) p
WHERE c.id = c2.id
  AND c2.plate_number IS NOT NULL
  AND c.plate_first_two IS NULL;

-- Guard: abort (whole transaction rolls back) if any row's parts did not
-- parse, instead of silently applying NOT NULL over empty parts.
-- To review the offending rows first, run the commented SELECT alone.
DO $$
DECLARE bad INTEGER;
BEGIN
  SELECT count(*) INTO bad FROM car_inspections
   WHERE plate_first_two = '' OR plate_three = ''
      OR plate_letter = ''  OR plate_iran_code = '';
  IF bad > 0 THEN
    RAISE EXCEPTION '% row(s) have an unparseable plate_number — review with the check query, fix by hand, then rerun', bad;
  END IF;
END $$;

-- SELECT id, plate_number FROM car_inspections
--  WHERE plate_first_two = '' OR plate_three = ''
--     OR plate_letter = ''  OR plate_iran_code = '';

ALTER TABLE car_inspections ALTER COLUMN plate_first_two SET NOT NULL;
ALTER TABLE car_inspections ALTER COLUMN plate_letter    SET NOT NULL;
ALTER TABLE car_inspections ALTER COLUMN plate_three     SET NOT NULL;
ALTER TABLE car_inspections ALTER COLUMN plate_iran_code SET NOT NULL;

COMMIT;

-- Post-deploy verification:
--   SELECT id, plate_first_two, plate_letter, plate_three, plate_iran_code
--   FROM car_inspections ORDER BY id DESC LIMIT 10;
--
-- The old plate_number column is intentionally kept (Hibernate never drops
-- it). Once the new backend is verified, it can be dropped:
--   ALTER TABLE car_inspections DROP COLUMN plate_number;
