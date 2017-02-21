export class NotificationSetting{
	nickName = "";
	textMessage: boolean;
	email: boolean;
	pushNotification: boolean;

	constructor(nickname: string, textMessage: boolean, email: boolean, pushNotification: boolean){
		this.nickName = nickname;
		this.textMessage = textMessage;
		this.email = email;
		this.pushNotification = pushNotification;
	}
}