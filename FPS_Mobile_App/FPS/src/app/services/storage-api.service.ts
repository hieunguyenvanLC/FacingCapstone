import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage';

@Injectable({
  providedIn: 'root'
})
export class StorageApiService {

  constructor(
    private storage: Storage,
  ) { }

  public set(key, value) {
    return this.storage.set(key, value);
  }
  public async get(key) {
    return await this.storage.get(key);
  }
  public async remove(key) {
    return await this.storage.remove(key);
  }
  public clear() {
    this.storage.clear().then(() => {
      console.log('all keys cleared');
    });
  }
}
