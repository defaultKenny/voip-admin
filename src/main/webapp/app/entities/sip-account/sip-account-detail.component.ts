import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISipAccount } from 'app/shared/model/sip-account.model';

@Component({
  selector: 'jhi-sip-account-detail',
  templateUrl: './sip-account-detail.component.html'
})
export class SipAccountDetailComponent implements OnInit {
  sipAccount: ISipAccount;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sipAccount }) => {
      this.sipAccount = sipAccount;
    });
  }

  previousState() {
    window.history.back();
  }
}
