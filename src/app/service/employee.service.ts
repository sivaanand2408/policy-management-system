import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Employee } from '../model/Employee';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http:HttpClient) { }

  getEmployees():Observable<Employee[]>
  {
    return this.http.get<Employee[]>("http://915626-env.eba-22rarxay.us-east-2.elasticbeanstalk.com/employees");
  }

  addEmployee(employee:Employee):Observable<Employee>
  {
    return this.http.post<Employee>("http://915626-env.eba-22rarxay.us-east-2.elasticbeanstalk.com/employees",employee);
  }

  getEmployeeById(id:number):Observable<Employee>
  {
    return this.http.get<Employee>("http://915626-env.eba-22rarxay.us-east-2.elasticbeanstalk.com/employees/"+id);
  }

  updateEmployee(id:number,employee:Employee):Observable<Employee>
  {
    return this.http.put<Employee>("http://915626-env.eba-22rarxay.us-east-2.elasticbeanstalk.com/employees/"+id,employee);
  }

  deleteEmployee(id:number):Observable<Employee>
  {
    return this.http.delete<Employee>("http://915626-env.eba-22rarxay.us-east-2.elasticbeanstalk.com/employees/"+id);
  }
}
