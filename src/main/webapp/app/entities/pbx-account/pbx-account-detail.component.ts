import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPbxAccount } from 'app/shared/model/pbx-account.model';

@Component({
  selector: 'jhi-pbx-account-detail',
  templateUrl: './pbx-account-detail.component.html'
})
export class PbxAccountDetailComponent implements OnInit {
  pbxAccount: IPbxAccount;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pbxAccount }) => {
      this.pbxAccount = pbxAccount;
    });
  }

  previousState() {
    window.history.back();
  }
}
