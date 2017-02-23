import {Address} from '../common/address';

export class SignupObjectError {
	firstName: string = "";
	lastName: string = "";
	phoneNumber: string = "";
	email: string = "";
	password: string = "";
	confPassword: string = "";
	addresses: Address[] = [new Address()];
}