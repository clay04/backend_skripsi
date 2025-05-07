DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM information_schema.columns
            WHERE table_name = 't_document'
              AND column_name = 'is_verified'
        ) THEN
            ALTER TABLE t_document
                ADD COLUMN is_verified BOOLEAN DEFAULT FALSE;
        END IF;
    END $$;
