# Account-table migration

The application no longer maps or uses a `users` table. It now uses `students`,
`admins`, and `teachers`.

Before deleting the old table, make a database backup. Once the updated backend
has started successfully and the initial administrator has been created, remove
the obsolete table manually:

```sql
DROP TABLE users;
```

Hibernate will create the `admins` and `teachers` tables and add `password` to
`students` because `spring.jpa.hibernate.ddl-auto=update` is enabled. Existing
student records are retained; only newly registered students have portal
passwords unless you set passwords for existing records.
