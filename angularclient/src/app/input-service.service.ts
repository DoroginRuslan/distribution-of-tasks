import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class InputService {

  private _data = new BehaviorSubject<number>(0);
  data$ = this._data.asObservable();

  setData(data: number) {
    this._data.next(data);
  }

  constructor() { }

  public changeValueOne(){
    this.setData(1);
  }
  public changeValueTwo(){
    this.setData(2);
  }

  public getData(){
    return this._data;
  }

  private _worker_data = new BehaviorSubject<number>(0);
  worker_data$ = this._worker_data.asObservable();
  public changeWorkerId(currentEmployeeId: number){
    this._worker_data.next(currentEmployeeId);
  }
}
