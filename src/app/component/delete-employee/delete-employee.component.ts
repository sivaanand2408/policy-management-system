import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from 'src/app/model/Employee';
import { EmployeeService } from 'src/app/service/employee.service';

@Component({
  selector: 'app-delete-employee',
  templateUrl: './delete-employee.component.html',
  styles: [
  ]
})
export class DeleteEmployeeComponent implements OnInit {
  employee!:Employee;
  constructor(private employeeService:EmployeeService,private route:ActivatedRoute,private router:Router) { }

  ngOnInit(): void {
    let id=this.route.snapshot.params.id;
    this.employeeService.getEmployeeById(id).subscribe((data)=>{
      this.employee=data;
    })
  }

  onDelete(){
    this.employeeService.deleteEmployee(this.employee.id).subscribe(data=>{
      this.router.navigate(["\employees"]);
    })
  }

  onCancel()
  {
    this.router.navigate(["/employees"]);
  }

}
