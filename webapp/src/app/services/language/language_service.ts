import {Injectable} from '@angular/core';
import {ENGLISH} from './english_keys'
import {SPANISH} from './spanish_keys'

@Injectable()
export class LanguageService {
	public getTranslation(key: string) {
		if (this.getSelectedLanguage() === 'es') {
			return SPANISH.filter(value => value.key === key)[0].value;
		}else{
			return ENGLISH.filter(value => value.key === key)[0].value;
		}
	}

	public getSelectedLanguage(){
		return localStorage.getItem("language");
	}

	public setSelectedLanguage(language: string){
		localStorage.setItem("language", language);
	}
}
