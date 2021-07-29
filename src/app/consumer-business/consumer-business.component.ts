import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { ConsumerBusinessDetails } from '../model/ConsumerBusinessDetails';
import { Service } from '../policy-service/service';

@Component({
  selector: 'app-consumer-business',
  templateUrl: './consumer-business.component.html',
  styleUrls: ['./consumer-business.component.css']
})
export class ConsumerBusinessComponent implements OnInit {

  message: any;
  consumerBusinessDetails: ConsumerBusinessDetails;

  constructor(private service:Service, private route: ActivatedRoute) { }

  ngOnInit() {
    let consumerId = this.route.snapshot.params.consumerId;
    this.service.viewConsumerBusiness(consumerId).subscribe(data =>{
      this.consumerBusinessDetails = data
      console.log(this.consumerBusinessDetails)},
      error => {
        this.message = "Sorry, Consumer not found"
        console.log(this.message)
    })
  }

}
