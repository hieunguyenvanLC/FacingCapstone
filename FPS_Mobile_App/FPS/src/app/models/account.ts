export class Account {
    phoneNumber: string;
    password: string;
    fullName: string;

    constructor(phoneNumber: string, password: string, fullName: string){
      this.phoneNumber = phoneNumber;
      this.password = password;
      this.fullName = fullName;
    }

}
