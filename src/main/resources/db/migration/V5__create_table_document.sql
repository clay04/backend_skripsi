CREATE TABLE t_document (
                            uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            file_name VARCHAR(255) NOT NULL,
                            file_type VARCHAR(100) NOT NULL,
                            path TEXT NOT NULL,
                            category VARCHAR(100) NOT NULL,
                            uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            uploaded_by VARCHAR(100) NOT NULL,
                            file_data BYTEA -- kolom untuk menyimpan isi file biner
);
