    DROP TABLE IF EXISTS SysUser;
    DROP TABLE IF EXISTS PartyUser;
    DROP TABLE IF EXISTS SysPartyUserLink;
    create table SysUser (
        id VARCHAR(32) NOT NULL,
        loginName VARCHAR(20) not null unique,
        loginPwd VARCHAR(64) not null,
        userName VARCHAR(20) not null,
        sex VARCHAR(4),
        birthDay DATE,
        userAvatar VARCHAR(50),
        userRemark VARCHAR(200),
        tel VARCHAR(11),
        email VARCHAR(30),
        qq VARCHAR(11),
        weiXin VARCHAR(20),
        registerTime DATETIME not null,
        registerIP VARCHAR(20) not null,
        lastLoginTime DATETIME not null,
        lastLoginIP VARCHAR(20) not null,
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    create table PartyUser (
        id VARCHAR(32) NOT NULL,
        partyId BIGINT(13) not null,
        loginName VARCHAR(20) not null unique,
        loginPwd VARCHAR(64) not null,
        userName VARCHAR(20) not null,
        sex VARCHAR(4),
        birthDay DATE,
        userAvatar VARCHAR(50),
        userRemark VARCHAR(200),
        userStatus INT(10),
        tel VARCHAR(11),
        email VARCHAR(30),
        qq VARCHAR(11),
        weiXin VARCHAR(20),
        registerTime DATETIME not null,
        registerIP VARCHAR(20) not null,
        lastLoginTime DATETIME,
        lastLoginIP VARCHAR(20),
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    create table SysPartyUserLink (
        id INT(9) NOT NULL,
        sysUserId VARCHAR(32) not null,
        partyUserId VARCHAR(32) not null,
        partyId BIGINT(13) not null,
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;

	alter table SysUser add index SysUser_loginName(loginName);
	alter table PartyUser add index PartyUser_loginName(loginName);

