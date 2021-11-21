
delete from transactions t where exists (select 1 from accounts a where a.user_id = 1 and t.account_id = a.account_id);
delete from transactions t where exists (select 1 from accounts a where a.user_id = 2 and t.account_id = a.account_id);
delete from categories;
delete from accounts where user_id = 1;
delete from users where user_id = 1;
delete from accounts where user_id = 2;
delete from users where user_id = 2;


INSERT INTO users (user_id, name, email)
  VALUES (1, 'scala_user1', 'aaa@scala.com');
INSERT INTO users (user_id, name, email)
    VALUES (2, 'scala_user2', 'aaa2@stala.com');