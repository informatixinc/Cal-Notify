import {Address} from '../common/address';

export class NotificationObject {
	addresses: Address[] = [new Address()];
	sms: boolean;
	email: boolean;
	sns: boolean;
}