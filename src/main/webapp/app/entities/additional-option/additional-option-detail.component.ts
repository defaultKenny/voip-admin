import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdditionalOption } from 'app/shared/model/additional-option.model';

@Component({
  selector: 'jhi-additional-option-detail',
  templateUrl: './additional-option-detail.component.html'
})
export class AdditionalOptionDetailComponent implements OnInit {
  additionalOption: IAdditionalOption;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ additionalOption }) => {
      this.additionalOption = additionalOption;
    });
  }

  previousState() {
    window.history.back();
  }
}
