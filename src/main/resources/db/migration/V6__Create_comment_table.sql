create table comment
(
	id int auto_increment,
	parent_id bigint not null,
	type int not null,
	commentator int not null,
	gmt_modified bigint,
	gmt_create bigint,
	like_count bigint,
	content longvarchar not null,
	constraint comment_pk
		primary key (id)
);


