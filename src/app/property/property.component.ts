import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Property } from '../model/Property';
import { Service } from '../policy-service/service';

@Component({
  selector: 'app-property',
  templateUrl: './property.component.html',
  styleUrls: ['./property.component.css']
})
export class PropertyComponent implements OnInit {

  message:any;
  property: Property;

  constructor(private service:Service, private route: ActivatedRoute) { }

  ngOnInit() {
    let consumerId = this.route.snapshot.params.consumerId;
    let propertyId = this.route.snapshot.params.consumerId;
    this.service.viewBusinessProperty(consumerId, propertyId).subscribe(data =>{
      this.property = data
      console.log(this.property)},     //},
      error => {
        this.message = "Sorry, Property not found"
        console.log(this.message)
    })
  }



}
