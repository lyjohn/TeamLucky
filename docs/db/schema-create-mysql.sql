    DROP TABLE IF EXISTS SysUser;
    DROP TABLE IF EXISTS PartyUser;
    DROP TABLE IF EXISTS SysPartyUserLink;
    DROP TABLE IF EXISTS Party;
    DROP TABLE IF EXISTS Group;
    DROP TABLE IF EXISTS GroupUser;
    DROP TABLE IF EXISTS Message;
    DROP TABLE IF EXISTS Notice;
    DROP TABLE IF EXISTS Forum;
    DROP TABLE IF EXISTS ForumComment;
    DROP TABLE IF EXISTS Document;
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
    create table Party (
        id INT(9) NOT NULL,
        partyName VARCHAR(20) not null,
        partyCode VARCHAR(20) not null,
        partyCover VARCHAR(50),
        partyRemark VARCHAR(500) not null,
        partyStatus INT(10) not null,
        isPublic TINYINT(3) not null,
        isGroup TINYINT(3) not null,
        isCustomBuild TINYINT(3),
        memberNumMin INT(10),
        memberNumMax INT(10),
        buildEndTime DATETIME,
        createBy VARCHAR(32) not null,
        createTime DATETIME not null,
        memberCount INT(10) not null,
        hotCount INT(10) not null,
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    create table Group (
        id INT(9) NOT NULL,
        partyId BIGINT(13) not null,
        groupName VARCHAR(20) not null,
        groupCover VARCHAR(50),
        groupRemark VARCHAR(500) not null,
        groupStatus INT(10) not null,
        isCustomJoin TINYINT(3) not null,
        isSourcePublic TINYINT(3) not null,
        createBy VARCHAR(32) not null,
        createTime DATETIME not null,
        memberCount INT(10) not null,
        hotCount INT(10) not null,
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    create table GroupUser (
        id INT(9) NOT NULL,
        partyUserId VARCHAR(32) not null,
        memberStatus INT(10) not null,
        groupId BIGINT(13) not null,
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    create table Message (
        id INT(9) NOT NULL,
        messageTitle VARCHAR(500) not null,
        messageContent VARCHAR(5000) not null,
        messageUrl VARCHAR(100),
        isRead TINYINT(3) not null,
        messageFrom VARCHAR(20) not null,
        messageTo VARCHAR(32) not null,
        messageTime DATETIME not null,
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    create table Notice (
        id INT(9) NOT NULL,
        noticeTitle VARCHAR(32) not null,
        noticeContent VARCHAR(5000) not null,
        isPublic TINYINT(3) not null,
        readCount INT(10) not null,
        groupId BIGINT(13),
        partyId BIGINT(13),
        createBy VARCHAR(32) not null,
        createTime DATETIME not null,
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    create table Forum (
        id INT(9) NOT NULL,
        forumTitle VARCHAR(32) not null,
        forumContent VARCHAR(5000) not null,
        isPublic TINYINT(3) not null,
        readCount INT(10) not null,
        commentCount INT(10) not null,
        groupId BIGINT(13),
        partyId BIGINT(13),
        createBy VARCHAR(32) not null,
        createTime DATETIME not null,
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    create table ForumComment (
        id INT(9) NOT NULL,
        forumId BIGINT(13) not null,
        commentContent VARCHAR(500) not null,
        parentId BIGINT(13),
        createBy VARCHAR(32) not null,
        createTime DATETIME not null,
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    create table Document (
        id INT(9) NOT NULL,
        docFullName VARCHAR(50) not null,
        docName VARCHAR(50) not null,
        docExtName VARCHAR(10) not null,
        docSize BIGINT(13) not null,
        docPath VARCHAR(100) not null,
        isPublic TINYINT(3) not null,
        downloadCount INT(10) not null,
        groupId BIGINT(13),
        partyId BIGINT(13) not null,
        createBy VARCHAR(32) not null,
        createTime DATETIME not null,
        PRIMARY KEY (id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;

	alter table SysUser add index SysUser_loginName(loginName);
	alter table SysUser add index SysUser_email(email);
	alter table PartyUser add index PartyUser_partyId(partyId);
	alter table PartyUser add index PartyUser_loginName(loginName);
	alter table PartyUser add index PartyUser_email(email);
	alter table SysPartyUserLink add index SysPartyUserLink_sysUserId(sysUserId);
	alter table SysPartyUserLink add index SysPartyUserLink_partyUserId(partyUserId);
	alter table SysPartyUserLink add index SysPartyUserLink_partyId(partyId);
	alter table Party add index Party_createBy(createBy);
	alter table Group add index Group_partyId(partyId);
	alter table Group add index Group_createBy(createBy);
	alter table GroupUser add index GroupUser_partyUserId(partyUserId);
	alter table GroupUser add index GroupUser_groupId(groupId);
	alter table Message add index Message_messageTo(messageTo);
	alter table Notice add index Notice_groupId(groupId);
	alter table Notice add index Notice_partyId(partyId);
	alter table Notice add index Notice_createBy(createBy);
	alter table Forum add index Forum_groupId(groupId);
	alter table Forum add index Forum_partyId(partyId);
	alter table Forum add index Forum_createBy(createBy);
	alter table ForumComment add index ForumComment_forumId(forumId);
	alter table ForumComment add index ForumComment_parentId(parentId);
	alter table ForumComment add index ForumComment_createBy(createBy);
	alter table Document add index Document_groupId(groupId);
	alter table Document add index Document_partyId(partyId);
	alter table Document add index Document_createBy(createBy);

