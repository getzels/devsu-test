ALTER TABLE transaction
    ALTER COLUMN type
        TYPE smallint
        USING type::smallint;