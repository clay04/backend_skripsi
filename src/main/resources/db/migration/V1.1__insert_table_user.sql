-- Insert role into t_roles table
INSERT INTO t_roles (
    role_id,
    name,
    description,
    created_by,
    created_at,
    updated_by,
    updated_at,
    is_deleted
) VALUES (
             '00',
             'Super Admin',
             'Roles Untuk Super Admin',
             'no-username',
             NOW(),
             'no-username',
             NOW(),
             FALSE
         ),
      (
          '01',
          'Admin',
          'Roles Untuk Admin',
          'no-username',
          NOW(),
          'no-username',
          NOW(),
          FALSE
      ),
         (
             '02',
             'Kemahasiswaan',
             'Roles Kemahasiswaan',
             'no-username',
             NOW(),
             'no-username',
             NOW(),
             FALSE
         ),
         (
             '03',
             'Student',
             'Roles Untuk Student',
             'no-username',
             NOW(),
             'no-username',
             NOW(),
             FALSE
         )
  ON CONFLICT (role_id) DO NOTHING;

INSERT INTO t_user (
    uuid,
    first_name,
    last_name,
    user_name,
    email,
    role_id,
    created_at,
    created_by,
    updated_at,
    updated_by,
    password,
    status,
    is_deleted
) VALUES (
             '00afc87f-7415-4970-b1b3-9955cead7520',
             'super',
             'admin',
             'superadmin',
             'superadmin@test.dev',
             '00',
             NOW(),
             'no-username',
             NOW(),
             'no-username',
             crypt('12345', gen_salt('bf')), -- Hash the password with bcrypt
             TRUE,
             FALSE
         ) ON CONFLICT (uuid) DO NOTHING;
