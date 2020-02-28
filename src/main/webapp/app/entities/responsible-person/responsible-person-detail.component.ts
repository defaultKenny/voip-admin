import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResponsiblePerson } from 'app/shared/model/responsible-person.model';

@Component({
  selector: 'jhi-responsible-person-detail',
  templateUrl: './responsible-person-detail.component.html'
})
export class ResponsiblePersonDetailComponent implements OnInit {
  responsiblePerson: IResponsiblePerson;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ responsiblePerson }) => {
      this.responsiblePerson = responsiblePerson;
    });
  }

  previousState() {
    window.history.back();
  }
}
