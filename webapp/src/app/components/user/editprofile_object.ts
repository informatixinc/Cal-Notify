import {Address} from '../common/address';
export class EditprofileObject {
	firstName: string;
	lastName: string;
	phoneNumber: string;
	email: string;
	password: string;
	oldPassword: string;
	confPassword: string;
	addresses: Address[] = [new Address()];
}