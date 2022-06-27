CREATE TABLE users_roles
(
    user_id VARCHAR(36),
    role_id VARCHAR(36),

    CONSTRAINT user_roles_user_id_fk_key FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_roles_role_id_fk_key FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT user_roles_constraint_p_key PRIMARY KEY (user_id, role_id)
);
