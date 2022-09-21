# spring-final-project
spring boot fina project

#############################
------------------------------

tables
--------
*base={
id:Long,
isActive: Boolen;
isApproved: Boolen;
}

1. user={
name:String;
username:String;
password: String;
role: Role[];
email:String;
address: String;
phone:String;
}

2.Role={
name:String;
}

3.propertyType={
name:String;
type:String;
}

4.property={
usr-id:
name:String;
propertyType:PropertyType;
price:Double;
size: String;
photo: Photo[];
paymentFrequency:Enum;
completionStatus:String;
isNagotiable:Boolen;
location:String;
facing:Enum;
description:String;
publisingDate:Date;
}

5.photo={
name:String;
data:String;
}

6.comments={
user-id:
property-id:
reply:Reply[];
text:String;
date:Date;
}

7.reply={
comment-id:
text:String;
user-id:
date:Date;
}

8.chatHistory={
fromUserId:
toUserId:
message:String;
}
