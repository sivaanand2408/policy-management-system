import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Employee } from 'src/app/model/Employee';
import { EmployeeService } from 'src/app/service/employee.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styles: [
  ]
})
export class EmployeeComponent implements OnInit {

  employees:Employee[]=[];
  constructor(private employeeService:EmployeeService,
              private router:Router) { }

  ngOnInit(): void {
    this.employeeService.getEmployees().subscribe(data=>this.employees=data);
  }

  onclick()
  {
    this.router.navigate(["/add"]);
  }
}
