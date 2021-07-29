import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Employee } from 'src/app/model/Employee';
import { EmployeeService } from 'src/app/service/employee.service';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styles: [
  ]
})
export class AddEmployeeComponent implements OnInit {

  constructor(private employeeService:EmployeeService,private router:Router) { }

  ngOnInit(): void {
  }

  onSubmit(employee:Employee)
  {
    this.employeeService.addEmployee(employee).subscribe(data=>{
      this.router.navigate(["/employees"]);
    })
  }

  onCancel()
  {
    this.router.navigate(["/employees"]);
  }

}
