    DROP TABLE IF EXISTS SysUser;
    DROP TABLE IF EXISTS PartyUser;
    DROP TABLE IF EXISTS SysPartyUserLink;
    create table SysUser (
        id VARCHAR(32) NOT NULL,
        LoginName VARCHAR(20) not null unique,
        LoginPwd VARCHAR(64) not null,
        UserName VARCHAR(20) not null,
        Sex VARCHAR(4),
        BirthDay DATE,
        UserAvatar VARCHAR(50),
        UserRemark VARCHAR(200),
        Tel VARCHAR(11),
        Email VARCHAR(30),
        QQ VARCHAR(11),
        Weixin VARCHAR(20),
        RegisterTime DATE not null,
        RegisterIP VARCHAR(20) not null,
        LastLoginTime DATE not null,
        LastLoginIP VARCHAR(20) not null,
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    create table PartyUser (
        id VARCHAR(32) NOT NULL,
        LoginName VARCHAR(20) not null unique,
        PartyId BIGINT(13) not null,
        LoginPwd VARCHAR(64) not null,
        UserName VARCHAR(20) not null,
        Sex VARCHAR(4),
        BirthDay DATE,
        UserAvatar VARCHAR(50),
        UserRemark VARCHAR(200),
        UserStatus INT(10),
        Tel VARCHAR(11),
        Email VARCHAR(30),
        QQ VARCHAR(11),
        Weixin VARCHAR(20),
        RegisterTime DATE not null,
        RegisterIP VARCHAR(20) not null,
        LastLoginTime DATE,
        LastLoginIP VARCHAR(20),
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    create table SysPartyUserLink (
        id INT(9) NOT NULL,
        SysUserId VARCHAR(32) not null,
        PartyUserId VARCHAR(32) not null,
        PartyId BIGINT(13) not null,
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;

	alter table SysUser add index SysUser_LoginName(LoginName);
	alter table PartyUser add index PartyUser_LoginName(LoginName);

