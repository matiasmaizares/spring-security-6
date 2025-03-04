-----------------data------------------
insert into customers (email, pwd) VALUES
                                            ('test@correo.com', '12345'),
                                            ('test2@correo.com', '12345'),
                                            ('test3@correo.com', '12345'),
                                            ('test4@correo.com', '12345')
;

insert into roles(role_name, description,id_customer) values
                                                          ('ROLE_ADMIN','cant view account endpoint',1),
                                                          ('ROLE_ADMIN','cant view card endpoint',2),
                                                          ('ROLE_USER','cant view loans endpoint',3),
                                                          ('ROLE_USER','cant view balance endpoint',4)
;
