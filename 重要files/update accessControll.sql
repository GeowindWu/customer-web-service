UPDATE access_control SET user_pub_key='MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCswRlimulVi5IXlQ2DhLgMaIM2ShARoB11XIs8JE5joF4SN+q+acGU8q4n3tDUYQ3RSlZ9xZhcRvukMPydjNk5YYhuUFa34c1VUPGqUsmEh/Hjr/jT894papn3eOaNTmkAp88UIU4uQ6uElz3TBW8WPU1uNE6RCgQH0bbY39WzMQIDAQAB'
,user_pri_key='MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKzBGWKa6VWLkheVDYOEuAxogzZKEBGgHXVcizwkTmOgXhI36r5pwZTyrife0NRhDdFKVn3FmFxG+6Qw/J2M2TlhiG5QVrfhzVVQ8apSyYSH8eOv+NPz3ilqmfd45o1OaQCnzxQhTi5Dq4SXPdMFbxY9TW40TpEKBAfRttjf1bMxAgMBAAECgYAhK9LcBDLZojsjHBPENS/B8i9/tSjwuXXumx6BJdeHebuWc/Y3Kcdp+k9GiJpArYIhtcx1ygM/dMHM/TsEZY8wC+pqNEX3CZ050oWgzggUWpfHBTC0HcZu7Jvz4EYbq0wfDvOR+CHwwqLf8u+2KtWtCQMyPVY2xgU5bepktQI5oQJBAPFm4CRrZDubbfm91C5mr0NNa364tExD45MpoMWGVqyl1yu2H0TN0XmNz9F5T+uWFgOpOD2CLSNeGYMcqfahKicCQQC3M37mbFE3cNEMrQPMpdYHClDelLmkyLkBCxm/YPJpnn8G3dxfwhgEd9CljP4/htEOLvefEaL13XBHCcYdTkbnAkBWlFAVn9KuMjvKU5QTJE79s3m3VGWN6NdpQ4fe8CSL/Vrj2Yjzc2IK15rOhVTtqMGyhHGgVdz8j1ZVGOW4h+sHAkBUsocZyrwlsI/Fl3upMoZnzNokfYfyaiY2GEa4Fv8b234I06udzeNCtY4N68hj4FVohEhRD1tS5iSRgzBHvjfDAkARhVrCnyWT1HKQHBjUfBFSeH7AT+7a3dlM2kkIX3aLlEzh3f1KXxcth2xc6MvZJS2brAEGWoQALdyMNBMau0bw'
,user_key1='gkqhkiG9w0BAQEFAASCAl8wggJbAgE'
,user_key2='BgQCswRlimulVi5IXlQ2DhLgMaIM2'
 WHERE tid=20;
  
COMMIT;


-- 数据表修改
CREATE TABLE access_control (
  tid bigint(20) NOT NULL AUTO_INCREMENT,
  access_ip varchar(60) NOT NULL COMMENT '访问IP',
  access_uuid varchar(120) NOT NULL COMMENT '访问用户，即校验码',
  user_key varchar(1024) NOT NULL COMMENT '用户根密钥',
  user_randomkey varchar(100) NOT NULL COMMENT '随机数，user_key+随机数算出访问uuid',
  parameter_key varchar(1024) NOT NULL COMMENT '512字节的密钥，用于输入参数的3des加密',
  user_pub_key blob COMMENT '自签名：用户公钥',
  user_pri_key blob COMMENT '自签名：用户私钥',
  user_key1 blob COMMENT '自签名：对称密钥1',
  user_key2 blob COMMENT '自签名：对称密钥2',
  user_code varchar(30) NOT NULL COMMENT '用户代码，接口访问的时候用，用于确定用那个密钥。',
  effective_trage int(11) NOT NULL DEFAULT '0' COMMENT '有效标志:1：有效，非1：无效',
  uuid_version varchar(100) NOT NULL COMMENT '密钥版本',
  begin_time datetime NOT NULL COMMENT '有效期开始时间',
  end_time datetime NOT NULL COMMENT '有效期结束时间',
  create_oper varchar(100) DEFAULT NULL COMMENT '创建时间',
  create_time datetime DEFAULT NULL COMMENT '创建人',
  verify_oper varchar(100) DEFAULT NULL COMMENT '审核人',
  verify_time datetime DEFAULT NULL COMMENT '审核时间',
  remark varchar(300) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (tid)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8
