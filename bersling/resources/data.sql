
drop table  if exists  documents;
create table documents(
    id bigint primary key,
    metadata json
);

drop table if exists  metaData;
drop table if exists  documentsNoJSON;
create table documentsNoJSON(
    id bigint primary key,
    metadataId bigint
);
create table metaData(
    id bigint primary key,
    difficulty varchar(255),
    constraint metaDataToDocumentsNoJSON foreign key (id) references documentsNoJSON(id)
);
